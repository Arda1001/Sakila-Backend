package com.example.sakila.actor;

import com.example.sakila.film.Film;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private short id;

    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(mappedBy = "cast")
    @JsonBackReference
    private Set<Film> films = new HashSet<>();


    public Actor(String firstName, String lastName, Set<Film> films) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.films = films;
    }
}

