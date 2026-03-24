package com.w2m.starshipmanager.model.starship;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StarshipCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double length;

    @NotNull
    @Positive
    private Double beam;

}
