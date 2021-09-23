package com.frankybboy.qajoke.service;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.mapper.JokeMapper;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokeServiceImplTest {

    final String JOKE_QUESTION = "My question?";

    @Mock
    JokeRepository jokeRepository;

    JokeMapper jokeMapper = JokeMapper.INSTANCE;

    JokeServiceImpl underTest;

    @BeforeEach
    void setup() {
        underTest = new JokeServiceImpl(jokeRepository, jokeMapper);
    }

    @Test
    void getAllJokes() {
        // given
        when(jokeRepository.findAll()).thenReturn(Arrays.asList(Joke.builder().id(1L).build(),
                                                                Joke.builder().id(2L).build()));

        // when
        List<JokeDto> result = underTest.getAllJokes();

        // then
        assertEquals(2, result.size());
    }

    @Test
    void getJokeById() {
        // given
        Long jokeId = 1L;
        when(jokeRepository.findById(jokeId)).thenReturn(
                Optional.of(Joke.builder().id(jokeId).question(JOKE_QUESTION).build()));

        // when
        JokeDto result = underTest.getJokeById(jokeId);

        // then
        assertNotNull(result);
        assertEquals(JOKE_QUESTION, result.getQuestion());
    }

    @Test
    void getJokeByIdNotFound() {
        // TODO implements
    }

    @Test
    void createJoke() {
        // given
        JokeDto jokeToSave = JokeDto.builder().question(JOKE_QUESTION).build();
        Joke savedJoke = Joke.builder().id(1L).question(JOKE_QUESTION).build();
        when(jokeRepository.save(any())).thenReturn(savedJoke);

        // when
        JokeDto result = underTest.createJoke(jokeToSave);

        // then
        assertNotNull(result);
        assertEquals(JOKE_QUESTION, result.getQuestion());
    }

    @Test
    void saveJoke() {
    }

    @Test
    void patchJoke() {
    }

    @Test
    void deleteJokeById() {
    }
}