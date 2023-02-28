package com.goggle.voco.dto;

import com.goggle.voco.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {
    private Long id;
    private String name;
    private String teamCode;

    public static TeamResponseDto from(Team team) {
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getTeamCode()
        );
    }
}
