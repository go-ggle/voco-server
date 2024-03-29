package com.goggle.voco.domain.participation.domain;

import com.goggle.voco.domain.team.domain.Team;
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
@ToString(exclude = {"user", "team"})
public class Participation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Team team;

    public Participation(User user, Team team) {
        this.user = user;
        this.team = team;
    }
}
