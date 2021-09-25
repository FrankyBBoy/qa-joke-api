package com.frankybboy.qajoke.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JokeListDto {
  private List<JokeDto> jokes;
}
