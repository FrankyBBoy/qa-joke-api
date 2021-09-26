package com.frankybboy.qajoke.service;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.mapper.JokeMapper;
import com.frankybboy.qajoke.mapper.JokeMapperImpl;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokeServiceImplTest {

    final String JOKE_QUESTION = "My question?";

    @Mock
    JokeRepository jokeRepository;

    JokeMapper jokeMapper = new JokeMapperImpl();

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
        // given
        when(jokeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            underTest.getJokeById(1L);
        });

        // then
        assertNotNull(exception);
        assertEquals("404 NOT_FOUND \"Joke not found\"", exception.getMessage());
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
        // given
        Long jokeId = 1L;
        JokeDto jokeToUpdate = JokeDto.builder().question(JOKE_QUESTION).build();
        Joke savedJoke = Joke.builder().id(jokeId).question(JOKE_QUESTION).build();
        when(jokeRepository.save(any())).thenReturn(savedJoke);

        // when
        JokeDto result = underTest.saveJoke(jokeId, jokeToUpdate);

        // then
        assertNotNull(result);
        assertEquals(JOKE_QUESTION, result.getQuestion());
    }

    @Test
    void patchJoke() {
        // given
        Long jokeId = 1L;
        String jokeAnswer = "My answer...";
        JokeDto jokeToUpdate = JokeDto.builder().question(JOKE_QUESTION).build();
        Joke actualJoke = Joke.builder().answer(jokeAnswer).build();
        Joke savedJoke = Joke.builder().id(jokeId).answer(jokeAnswer).question(JOKE_QUESTION).build();
        when(jokeRepository.findById(jokeId)).thenReturn(Optional.of(actualJoke));
        when(jokeRepository.save(any())).thenReturn(savedJoke);

        // when
        JokeDto result = underTest.patchJoke(jokeId, jokeToUpdate);

        // then
        assertNotNull(result);
        assertEquals(jokeAnswer, result.getAnswer());
        assertEquals(JOKE_QUESTION, result.getQuestion());
    }

    @Test
    void patchJokeNotFound() {
        // given
        when(jokeRepository.findById(anyLong())).thenReturn(Optional.empty());
        JokeDto jokeToPatch = JokeDto.builder().build();

        // when
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            underTest.patchJoke(1L, jokeToPatch);
        });

        // then
        assertNotNull(exception);
        assertEquals("404 NOT_FOUND \"Joke not found\"", exception.getMessage());
    }

    @Test
    void deleteJokeById() {
        // given
        Long jokeId = 1L;

        // when
        underTest.deleteJokeById(jokeId);

        // then
        verify(jokeRepository).deleteById(jokeId);
    }
}