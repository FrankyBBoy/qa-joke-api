package com.frankybboy.qajoke.repository;

import com.frankybboy.qajoke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, Long> {
}
