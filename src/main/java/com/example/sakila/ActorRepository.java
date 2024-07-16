package com.example.sakila;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public class ActorRepository {
    public interface ActorRepositoryI extends JpaRepository<Actor, Short> {
        @Query("select a from Actor a where a.firstName = ?1")
        List<Actor> findAllByFirstNameWithQuery(String firstName);
    }
}
