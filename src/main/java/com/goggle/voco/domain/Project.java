package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "team")
public class Project extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    private String language;

    @ManyToOne
    private Team team;

    public Project(String language, String title, Team team) {
        this.title = title;
        this.language = language;
        this.team = team;
    }

}
