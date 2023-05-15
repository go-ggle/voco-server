package com.goggle.voco.domain.block.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.goggle.voco.domain.block.domain.Block;
import com.goggle.voco.domain.block.dto.AudioRequestDto;
import com.goggle.voco.domain.block.dto.BlockCreateRequestDto;
import com.goggle.voco.domain.block.dto.BlockResponseDto;
import com.goggle.voco.domain.block.dto.BlocksResponseDto;
import com.goggle.voco.domain.project.domain.Project;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.domain.block.repository.BlockRepository;
import com.goggle.voco.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

@Service
@Log4j2
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final ProjectRepository projectRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${ai.audio-bucket-name}")
    private String AUDIO_BUCKET_NAME;
    @Value("${cloud.aws.region.static}")
    private String AWS_REGION;
    @Value("${ai.address}")
    private String AI_ADDRESS;
    @Value("${ai.port}")
    private String FLASK_PORT;

    @Override
    public String createAudio(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://" + AI_ADDRESS + ":" + FLASK_PORT)
                .path("/tts")
                .queryParam("teamId", teamId)
                .queryParam("projectId", projectId)
                .queryParam("blockId", blockId)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<byte[]> audioEntity = restTemplate.postForEntity(uri, audioRequestDto, byte[].class);
        byte[] body = audioEntity.getBody();

        String audioPath = "https://" + AUDIO_BUCKET_NAME + ".s3." + AWS_REGION + ".amazonaws.com/" + teamId + "/" + projectId + "/" + blockId + ".wav";

        return audioPath;
    }

    @Override
    public void mergeBlocks(Long teamId, Long projectId){
        try {
            List<Block> blocks = blockRepository.findByProjectId(projectId);
            long length = 0;
            AudioInputStream clip = null;
            List<AudioInputStream> list = new ArrayList<AudioInputStream>();
            new File(String.valueOf(projectId)).mkdirs();
            for (Block b: blocks) {
                FileOutputStream fos = new FileOutputStream(new File(projectId + "/temp" + b.getId() + ".wav"));
                try {
                    S3Object o = amazonS3Client.getObject(AUDIO_BUCKET_NAME, teamId + "/" + projectId + "/" + b.getId() + ".wav");
                    S3ObjectInputStream s3is = o.getObjectContent();
                    byte[] read_buf = new byte[1024];
                    int read_len = 0;
                    while ((read_len = s3is.read(read_buf)) > 0) {
                        fos.write(read_buf, 0, read_len);
                    }
                    fos.close();
                }
                catch (AmazonServiceException e) {
                    System.err.println(e.getErrorMessage());
                    if(Objects.equals(e.getErrorMessage(), "NoSuchKey")){
                        continue;
                    }
                    else {
                        System.exit(1);
                    }
                }

                String[] cmd = {"sox", projectId + "/temp" + b.getId() + ".wav", projectId + "/interval" + b.getId() + ".wav", "pad", "0", Long.toString(b.getInterval())};
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectErrorStream(true);
                try {
                    Process p = pb.start();
                    InputStream is = p.getInputStream();
                    int in = -1;
                    while ((in = is.read()) != -1) {
                        System.out.print((char)in);
                    }
                    p.waitFor();
                    int exitWith = p.exitValue();
                    System.out.println("\nExited with " + exitWith);
                } catch(Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }

                clip = AudioSystem.getAudioInputStream(new File(projectId + "/interval" + b.getId() + ".wav"));
                list.add(clip);
                length += clip.getFrameLength();
            }
            if(length>0 && list.size()>0 && clip!=null) {
                AudioInputStream appendedFiles =
                        new AudioInputStream(
                                new SequenceInputStream(Collections.enumeration(list)),
                                clip.getFormat(),
                                length);

                AudioSystem.write(appendedFiles,
                        AudioFileFormat.Type.WAVE,
                        new File(projectId + "/appended.wav"));
            }
            clip.close();
            amazonS3Client.putObject(AUDIO_BUCKET_NAME, teamId + "/" + projectId + "/0.wav", new File(projectId + "/appended.wav"));
            FileUtils.deleteDirectory(new File(String.valueOf(projectId)));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void increaseOrders(Long projectId, Long order){
        List<Block> blocks = blockRepository.findByProjectId(projectId);
        blocks.stream()
                .filter(b -> b.getOrder() >= order)
                .forEach(b -> b.setOrder(b.getOrder() + 1));
    }

    @Override
    public void decreaseOrders(Long projectId, Long order){
        List<Block> blocks = blockRepository.findByProjectId(projectId);
        blocks.stream()
                .filter(b -> b.getOrder() > order)
                .forEach(b -> b.setOrder(b.getOrder() - 1));
    }

    @Override
    public BlockResponseDto createBlock(BlockCreateRequestDto blockCreateRequestDto, Long teamId, Long projectId) {
        Long voiceId = blockCreateRequestDto.getVoiceId();
        Long order = blockCreateRequestDto.getOrder();

        Project project = projectRepository.findById(projectId).orElseThrow(()-> new NotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        Block block = new Block(project, "", "", voiceId, 0L, order);
        increaseOrders(projectId, order);
        blockRepository.save(block);

        return BlockResponseDto.from(block);
    }

    @Override
    public BlocksResponseDto findBlocks(Long projectId) {
        List<Block> blocks = blockRepository.findByProjectId(projectId);
        List<BlockResponseDto> blocksResponseDtos = blocks.stream()
                .sorted(Comparator.comparing(block -> block.getOrder()))
                .map(block -> BlockResponseDto.from(block))
                .collect(Collectors.toList());

        return new BlocksResponseDto(blocksResponseDtos);
    }

    //TODO: 버켓에서 음성 삭제
    @Override
    public void deleteBlock(Long teamId, Long projectId, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));
        decreaseOrders(projectId, block.getOrder());
        blockRepository.delete(block);
        mergeBlocks(teamId, projectId);
    }

    @Override
    public BlockResponseDto updateBlock(AudioRequestDto audioRequestDto, Long teamId, Long projectId, Long blockId) {
        Block block = blockRepository.findById(blockId).orElseThrow(()->new NotFoundException(ErrorCode.BLOCK_NOT_FOUND));

        String text = audioRequestDto.getText();
        Long voiceId = audioRequestDto.getVoiceId();
        Long interval = audioRequestDto.getInterval();

        String audioPath = createAudio(audioRequestDto, teamId, projectId, block.getId());

        block.setText(text);
        block.setVoiceId(voiceId);
        block.setInterval(interval);
        block.setAudioPath(audioPath);
        block.setUpdatedAt(LocalDateTime.now());
        blockRepository.save(block);

        mergeBlocks(teamId, projectId);

        return BlockResponseDto.from(block);
    }
}