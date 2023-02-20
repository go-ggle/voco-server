package com.goggle.voco.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
    private Boolean isBookmarked;

    @ManyToOne
    private User user;

    public Project(String language, String title) {
        this.title = title;
        this.language = language;
        this.isBookmarked = false;
    }

}
