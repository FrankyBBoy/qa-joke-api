package com.frankybboy.qajoke.service;

import com.frankybboy.qajoke.dto.JokeDto;
import java.util.List;

public interface JokeService {

  List<JokeDto> getAllJokes();

  JokeDto getJokeById(Long id);

  JokeDto createJoke(JokeDto jokeDto);

  JokeDto saveJoke(Long id, JokeDto jokeDto);

  JokeDto patchJoke(Long id, JokeDto jokeDto);

  void deleteJokeById(Long id);
}
