package com.frankybboy.qajoke.mapper;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.model.Joke;
import org.mapstruct.Mapper;

@Mapper
public interface JokeMapper {

    JokeDto jokeToJokeDto(Joke joke);

    Joke jokeDtoToJoke(JokeDto jokeDto);
}
