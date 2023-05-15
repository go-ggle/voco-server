package com.goggle.voco.domain.train.service;

import com.goggle.voco.domain.fcm.service.FCMService;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.exception.ErrorCode;
import com.goggle.voco.exception.NotFoundException;
import com.goggle.voco.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService{
    private final UserRepository userRepository;
    private final FCMService fcmService;

    @Override
    public void finishTrain(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        user.setIsRegistered(Boolean.TRUE);
        userRepository.save(user);

        fcmService.sendToToken(user, "모델 훈련 완료", "모델 훈련 끝남ㅎ");
    }

    @Override
    public void startTrain(Long userId) throws Exception {
        Optional<User> selectedUser = userRepository.findById(userId);

        if(selectedUser.isPresent()){
            User user = selectedUser.get();
            WebClient client = WebClient.builder()
                    .baseUrl("http://58.142.29.186:52424")
                    .defaultCookie("cookieKey", "cookieValue")
                    .build();

            client.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/train")
                            .queryParam("user_id", "{userId}")
                            .build(userId.intValue()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(result -> finishTrain(user.getId()));

        } else {
            throw new Exception();
        }
    }
}
