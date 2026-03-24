package com.w2m.starshipmanager.model.starship;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StarshipModifyRequest {

    @Max(255)
    private String name;

    @Positive
    private Double length;

    @Positive
    private Double beam;

}
