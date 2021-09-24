package com.frankybboy.qajoke.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JokeListDto {

    private List<JokeDto> jokes;
}
