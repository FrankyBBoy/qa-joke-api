package com.frankybboy.qajoke.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.model.Joke;
import com.frankybboy.qajoke.repository.JokeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class JokeControllerIT extends AbstractRestControllerTest {

    private static final String JOKE_QUESTION_1 = "My question 1?";
    private static final String JOKE_ANSWER_1 = "My answer 1!";
    private static final String JOKE_QUESTION_2 = "My question 2?";
    private static final String JOKE_ANSWER_2 = "My answer 2!";

    Joke simpleJoke1;
    Joke simpleJoke2;

    @Autowired
    JokeRepository jokeRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        simpleJoke1 = jokeRepository.save(
                Joke.builder().question(JOKE_QUESTION_1).answer(JOKE_ANSWER_1).build()
        );
        simpleJoke2 = jokeRepository.save(
                Joke.builder().question(JOKE_QUESTION_2).answer(JOKE_ANSWER_2).build()
        );
    }

    @Test
    void getListOfJokes() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get(JokeController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jokes", hasSize(2)));
    }

    @Test
    void getJokeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(JokeController.BASE_URL + "/" + simpleJoke1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(JOKE_QUESTION_1)))
                .andExpect(jsonPath("$.answer", equalTo(JOKE_ANSWER_1)));
    }

    @Test
    void createNewJoke() throws Exception {
        String newJokeQuestion = "New joke question?";
        String newJokeAnswer = "New joke answer!";
        JokeDto jokeToCreate = JokeDto.builder().question(newJokeQuestion).answer(newJokeAnswer).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(JokeController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokeToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question", equalTo(newJokeQuestion)))
                .andExpect(jsonPath("$.answer", equalTo(newJokeAnswer)));
    }

    @Test
    void updateJoke() throws Exception {
        String updatedJokeQuestion = "Updated joke question?";
        String updatedJokeAnswer = "Updated joke answer!";
        JokeDto jokeToUpdate = JokeDto.builder().question(updatedJokeQuestion).answer(updatedJokeAnswer).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(JokeController.BASE_URL + "/" + simpleJoke1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokeToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(updatedJokeQuestion)))
                .andExpect(jsonPath("$.answer", equalTo(updatedJokeAnswer)));
    }

    @Test
    void patchJoke() throws Exception {
        String patchedJokeQuestion = "Patched joke question?";
        JokeDto jokeToPatch = JokeDto.builder().question(patchedJokeQuestion).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(JokeController.BASE_URL + "/" + simpleJoke1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokeToPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(patchedJokeQuestion)))
                .andExpect(jsonPath("$.answer", equalTo(simpleJoke1.getAnswer())));
    }

    @Test
    void deleteJokeById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(JokeController.BASE_URL + "/" + simpleJoke1.getId()))
                .andExpect(status().isOk());
    }
}
