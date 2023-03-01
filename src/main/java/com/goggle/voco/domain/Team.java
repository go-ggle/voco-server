package com.goggle.voco.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
}
