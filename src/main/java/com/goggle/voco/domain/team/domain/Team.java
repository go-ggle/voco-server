package com.goggle.voco.domain.team.domain;

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
@ToString
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    private String teamCode;
    @Builder.Default
    private boolean isPrivate = false;

    public Team(String name, String teamCode) {
        this.name = name;
        this.teamCode = teamCode;
    }

    public Team(User user, boolean isPrivate) {
        this.name = user.getNickname();
        this.isPrivate = isPrivate;
    }
}
