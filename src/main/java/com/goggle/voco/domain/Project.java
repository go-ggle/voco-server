package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class Project extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    private String language;

    @ManyToOne
    private User user;

    public Project(String language, String title, User user) {
        this.title = title;
        this.language = language;
        this.user = user;
    }

}
