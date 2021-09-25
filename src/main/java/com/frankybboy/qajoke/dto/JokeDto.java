package com.frankybboy.qajoke.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JokeDto {

    @Size(max = 255)
    private String question;

    @Size(max = 255)
    private String answer;
}
