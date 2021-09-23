package com.frankybboy.qajoke.mapper;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.model.Joke;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JokeMapper {

    JokeMapper INSTANCE = Mappers.getMapper(JokeMapper.class);

    JokeDto jokeToJokeDto(Joke joke);

    Joke jokeDtoToJoke(JokeDto jokeDto);
}
