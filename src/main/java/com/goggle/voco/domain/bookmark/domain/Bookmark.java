package com.goggle.voco.domain.bookmark.domain;

import com.goggle.voco.domain.project.domain.Project;
import com.goggle.voco.domain.user.domain.User;
import com.goggle.voco.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "project"})
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Project project;

    public Bookmark(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    public Long getProjectId() {
        return project.getId();
    }
}
