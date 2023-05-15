package com.goggle.voco.domain.project.domain;

import com.goggle.voco.domain.block.domain.Block;
import com.goggle.voco.domain.bookmark.domain.Bookmark;
import com.goggle.voco.domain.team.domain.Team;
import com.goggle.voco.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "team")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    private Long language;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, orphanRemoval=true)
    private List<Block> blocks;
    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, orphanRemoval=true)
    private List<Bookmark> bookmarks;

    public Project(Long language, String title, Team team) {
        this.title = title;
        this.language = language;
        this.team = team;
    }
}
