package com.frankybboy.qajoke.service;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.mapper.JokeMapper;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(joke -> jokeMapper.jokeToJokeDto(joke))
                .collect(Collectors.toList());
    }

    @Override
    public JokeDto getJokeById(Long id) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);

        if (jokeOptional.isEmpty()) {
            // TODO implement (should throw exception)
        }

        return jokeMapper.jokeToJokeDto(jokeOptional.get());
    }

    @Override
    public JokeDto createJoke(JokeDto jokeDto) {
        Joke joke = jokeMapper.jokeDtoToJoke(jokeDto);
        Joke savedJoke = jokeRepository.save(joke);
        return jokeMapper.jokeToJokeDto(savedJoke);
    }

    @Override
    public JokeDto saveJoke(Long id, JokeDto jokeDto) {
        return null;
    }

    @Override
    public JokeDto patchJoke(Long id, JokeDto jokeDto) {
        return null;
    }

    @Override
    public void deleteJokeById(Long id) {

    }
}
