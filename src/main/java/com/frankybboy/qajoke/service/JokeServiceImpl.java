package com.frankybboy.qajoke.service;

import static org.apache.logging.log4j.util.Strings.isBlank;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.mapper.JokeMapper;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JokeServiceImpl implements JokeService {

  private final JokeRepository jokeRepository;
  private final JokeMapper jokeMapper;

  public JokeServiceImpl(JokeRepository jokeRepository, JokeMapper jokeMapper) {
    this.jokeRepository = jokeRepository;
    this.jokeMapper = jokeMapper;
  }

  @Override
  public List<JokeDto> getAllJokes() {
    return jokeRepository.findAll().stream()
        .map(jokeMapper::jokeToJokeDto)
        .collect(Collectors.toList());
  }

  @Override
  public JokeDto getJokeById(Long id) {
    Optional<Joke> jokeOptional = jokeRepository.findById(id);

    if (jokeOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Joke not found");
    }

    return jokeMapper.jokeToJokeDto(jokeOptional.get());
  }

  @Override
  public JokeDto createJoke(JokeDto jokeDto) {
    Joke jokeToSave = jokeMapper.jokeDtoToJoke(jokeDto);
    Joke savedJoke = jokeRepository.save(jokeToSave);
    return jokeMapper.jokeToJokeDto(savedJoke);
  }

  @Override
  public JokeDto saveJoke(Long id, JokeDto jokeDto) {
    Joke jokeToSave = jokeMapper.jokeDtoToJoke(jokeDto);
    jokeToSave.setId(id);
    Joke savedJoke = jokeRepository.save(jokeToSave);
    return jokeMapper.jokeToJokeDto(savedJoke);
  }

  @Override
  public JokeDto patchJoke(Long id, JokeDto jokeDto) {
    return jokeRepository.findById(id).map(joke -> {
      if (!isBlank(jokeDto.getQuestion())) {
        joke.setQuestion(jokeDto.getQuestion());
      }

      if (!isBlank(jokeDto.getAnswer())) {
        joke.setAnswer(jokeDto.getAnswer());
      }

      return jokeMapper.jokeToJokeDto(jokeRepository.save(joke));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Joke not found"));
  }

  @Override
  public void deleteJokeById(Long id) {
    jokeRepository.deleteById(id);
  }
}
