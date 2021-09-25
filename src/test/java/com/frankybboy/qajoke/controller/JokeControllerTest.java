package com.frankybboy.qajoke.controller;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.service.JokeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JokeControllerTest extends AbstractRestControllerTest{

    private static final Long JOKE_ID = 1L;
    private static final String JOKE_QUESTION = "My question?";
    private static final String JOKE_ANSWER = "My answer!";

    private static final String INVALID_VALUE = "TOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" +
            "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" +
            "LONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" +
            "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" +
            "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" +
            "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG";

    JokeDto simpleJoke;

    @Mock
    JokeService jokeService;

    @InjectMocks
    JokeController underTest;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        simpleJoke = JokeDto.builder().question(JOKE_QUESTION).answer(JOKE_ANSWER).build();
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    void getListOfJokes() throws Exception {
        List<JokeDto> jokeDtoList = Arrays.asList(
                JokeDto.builder().question("My question 1?").answer("My answer 1!").build(),
                JokeDto.builder().question("My question 2?").answer("My answer 2!").build()
        );
        when(jokeService.getAllJokes()).thenReturn(jokeDtoList);

        mockMvc.perform(MockMvcRequestBuilders
                .get(JokeController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jokes", hasSize(2)));
    }

    @Test
    void getJokeById() throws Exception {
        when(jokeService.getJokeById(JOKE_ID))
                .thenReturn(simpleJoke);

        mockMvc.perform(MockMvcRequestBuilders
                .get(JokeController.BASE_URL + "/" + JOKE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(JOKE_QUESTION)))
                .andExpect(jsonPath("$.answer", equalTo(JOKE_ANSWER)));
    }

    @Test
    void createNewJoke() throws Exception {
        JokeDto jokeToCreate = JokeDto.builder().build();
        when(jokeService.createJoke(any())).thenReturn(simpleJoke);

        mockMvc.perform(MockMvcRequestBuilders
                .post(JokeController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jokeToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question", equalTo(JOKE_QUESTION)))
                .andExpect(jsonPath("$.answer", equalTo(JOKE_ANSWER)));
    }

    @Test
    void givenInvalidQuestion_whenCreateNewJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().question(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(JokeController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidAnswer_whenCreateNewJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().answer(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(JokeController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateJoke() throws Exception {
        JokeDto jokeToUpdate = JokeDto.builder().build();
        when(jokeService.saveJoke(anyLong(), any())).thenReturn(simpleJoke);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokeToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(JOKE_QUESTION)))
                .andExpect(jsonPath("$.answer", equalTo(JOKE_ANSWER)));
    }

    @Test
    void givenInvalidQuestion_whenUpdateJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().question(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidAnswer_whenUpdateJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().answer(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchJoke() throws Exception {
        JokeDto jokeToPatch = JokeDto.builder().build();
        when(jokeService.patchJoke(anyLong(), any())).thenReturn(simpleJoke);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokeToPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", equalTo(JOKE_QUESTION)))
                .andExpect(jsonPath("$.answer", equalTo(JOKE_ANSWER)));
    }

    @Test
    void givenInvalidQuestion_whenPatchJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().question(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidAnswer_whenPatchJoke_thenReturnBadRequest() throws Exception {
        JokeDto invalidJoke = JokeDto.builder().answer(INVALID_VALUE).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(JokeController.BASE_URL + "/" + JOKE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidJoke)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteJokeById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(JokeController.BASE_URL + "/" + JOKE_ID))
                .andExpect(status().isOk());
    }
}