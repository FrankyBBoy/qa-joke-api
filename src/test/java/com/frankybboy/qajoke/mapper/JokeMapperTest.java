package com.frankybboy.qajoke.mapper;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JokeMapperTest {

    static final String JOKE_QUESTION = "My question?";
    static final String JOKE_ANSWER = "My answer!";

    JokeMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = JokeMapper.INSTANCE;
    }

    @Test
    void jokeToJokeDto() {
        // given
        Joke joke = Joke.builder().question(JOKE_QUESTION).answer(JOKE_ANSWER).build();
        JokeDto expected = JokeDto.builder().question(JOKE_QUESTION).answer(JOKE_ANSWER).build();

        // when
        JokeDto result = underTest.jokeToJokeDto(joke);

        // then
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void jokeDtoToJoke() {
        // given
        JokeDto jokeDto = JokeDto.builder().question(JOKE_QUESTION).answer(JOKE_ANSWER).build();
        Joke expected = Joke.builder().question(JOKE_QUESTION).answer(JOKE_ANSWER).build();

        // when
        Joke result = underTest.jokeDtoToJoke(jokeDto);

        // then
        assertNotNull(result);
        assertEquals(expected, result);
    }
}