package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
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
    private int userId;

    @ManyToOne
    private Project project;

    public Block(String text, String audioPath, Project project) {
        this.text = text;
        this.audioPath = audioPath;
        this.project = project;
    }

}
