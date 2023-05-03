package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name="\"order\"")
    @NotNull
    private Long order;

    @ManyToOne
    private Project project;

    public Block(Project project, String text, String audioPath, Long voiceId, Long interval, Long order) {
        this.project = project;
        this.text = text;
        this.audioPath = audioPath;
        this.voiceId = voiceId;
        this.interval = interval;
        this.order = order;
    }

}
