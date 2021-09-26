package com.frankybboy.qajoke.service;

import static org.junit.jupiter.api.Assertions.*;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.mapper.JokeMapperImpl;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@DataJpaTest
class JokeServiceImplIT {

    private static final String JOKE_QUESTION_1 = "My question 1?";
    private static final String JOKE_ANSWER_1 = "My answer 1!";
    private static final String JOKE_QUESTION_2 = "My question 2?";
    private static final String JOKE_ANSWER_2 = "My answer 2!";

    Joke simpleJoke1;
    Joke simpleJoke2;

    @Autowired
    JokeRepository jokeRepository;

    JokeService jokeService;

    @BeforeEach
    void setup() {
        simpleJoke1 = Joke.builder().question(JOKE_QUESTION_1).answer(JOKE_ANSWER_1).build();
        simpleJoke2 = Joke.builder().question(JOKE_QUESTION_2).answer(JOKE_ANSWER_2).build();

        jokeService = new JokeServiceImpl(jokeRepository, new JokeMapperImpl());
    }

    @Test
    void getAllJokes() {
        // given
        jokeRepository.save(simpleJoke1);
        jokeRepository.save(simpleJoke2);

        // when
        List<JokeDto> result = jokeService.getAllJokes();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(JOKE_QUESTION_1, result.get(0).getQuestion());
        assertEquals(JOKE_ANSWER_1, result.get(0).getAnswer());
    }

    @Test
    void getJokeById() {
        // given
        Joke savedJoke = jokeRepository.save(simpleJoke1);

        // when
        JokeDto result = jokeService.getJokeById(savedJoke.getId());

        // then
        assertNotNull(result);
        assertEquals(JOKE_QUESTION_1, result.getQuestion());
        assertEquals(JOKE_ANSWER_1, result.getAnswer());
    }

    @Test
    void getJokeByIdNotFound() {
        // given
        Long nonExistentId = 54321L;

        // when
        Exception result = assertThrows(ResponseStatusException.class, () -> {
            jokeService.getJokeById(nonExistentId);
        });

        // then
        assertNotNull(result);
        assertEquals("404 NOT_FOUND \"Joke not found\"", result.getMessage());
    }

    @Test
    void createJoke() {
        // given
        int actualSize = jokeRepository.findAll().size();
        int expectedSize = actualSize + 1;

        // when
        jokeService.createJoke(JokeDto.builder().build());

        // then
        assertEquals(expectedSize, jokeRepository.findAll().size());
    }

    @Test
    void saveJoke() {
        // given
        Joke jokeToUpdate = jokeRepository.save(simpleJoke1);
        String question = "Updated question?";
        String answer = "Updated answer!";
        JokeDto jokeDto = JokeDto.builder().question(question).answer(answer).build();

        // when
        JokeDto savedJoke = jokeService.saveJoke(jokeToUpdate.getId(), jokeDto);

        // then
        assertNotNull(savedJoke);
        assertEquals(question, savedJoke.getQuestion());
        assertEquals(answer, savedJoke.getAnswer());
    }

    @Test
    void patchJoke() {
        // given
        Joke jokeToPatch = jokeRepository.save(Joke.builder().question(JOKE_QUESTION_1).answer(JOKE_ANSWER_1).build());
        String patchedQuestion = "Patched question?";
        JokeDto jokeDto = JokeDto.builder().question(patchedQuestion).build();

        // when
        JokeDto savedJoke = jokeService.patchJoke(jokeToPatch.getId(), jokeDto);

        // then
        assertNotNull(savedJoke);
        assertEquals(patchedQuestion, savedJoke.getQuestion());
        assertEquals(JOKE_ANSWER_1, savedJoke.getAnswer());
    }

    @Test
    void deleteJokeById() {
        // given
        Joke savedJoke = jokeRepository.save(simpleJoke1);
        int expectedSize = jokeRepository.findAll().size() - 1;

        // when
        jokeService.deleteJokeById(savedJoke.getId());

        // then
        assertEquals(expectedSize, jokeRepository.findAll().size());
    }
}
