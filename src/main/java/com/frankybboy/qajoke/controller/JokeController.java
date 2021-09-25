package com.frankybboy.qajoke.controller;

import com.frankybboy.qajoke.dto.JokeDto;
import com.frankybboy.qajoke.dto.JokeListDto;
import com.frankybboy.qajoke.service.JokeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(JokeController.BASE_URL)
public class JokeController {

    public static final String BASE_URL = "/jokes";

    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public JokeListDto getListOfJokes() {
        return new JokeListDto(jokeService.getAllJokes());
    }

    @GetMapping("/{id}")
    public JokeDto getJokeById(@PathVariable Long id) {
        return jokeService.getJokeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JokeDto createNewJoke(@Valid @RequestBody JokeDto jokeDto) {
        return jokeService.createJoke(jokeDto);
    }

    @PutMapping("/{id}")
    public JokeDto updateJoke(@PathVariable Long id, @Valid @RequestBody JokeDto jokeDto) {
        return jokeService.saveJoke(id, jokeDto);
    }

    @PatchMapping("/{id}")
    public JokeDto patchJoke(@PathVariable Long id, @Valid @RequestBody JokeDto jokeDto) {
        return jokeService.patchJoke(id, jokeDto);
    }


    @DeleteMapping("/{id}")
    public void deleteJokeById(@PathVariable Long id) {
        jokeService.deleteJokeById(id);
    }

}
