package com.example.sakila.actor;

import com.example.sakila.ValidationGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class ActorInput {
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String firstName;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String lastName;

}

