package com.goggle.voco.domain.team.dto;

import com.goggle.voco.domain.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {
    private Long id;
    private String name;
    private String teamCode;
    private boolean isPrivate;

    public static TeamResponseDto from(Team team) {
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getTeamCode(),
                team.isPrivate()
        );
    }
}
