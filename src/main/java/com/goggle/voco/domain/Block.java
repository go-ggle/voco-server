package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "project")
public class Block extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;
    private String audioPath;
    private Long voiceId;
    @Column(name="\"interval\"")
    private Long interval;

    @ManyToOne
    private Project project;

    public Block(Project project, String text, String audioPath, Long voiceId, Long interval) {
        this.project = project;
        this.text = text;
        this.audioPath = audioPath;
        this.voiceId = voiceId;
        this.interval = interval;
    }

}
