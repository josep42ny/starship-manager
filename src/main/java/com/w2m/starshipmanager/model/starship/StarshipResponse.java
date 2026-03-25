package com.w2m.starshipmanager.model.starship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class StarshipResponse {

    private Long id;

    private String name;

    private Double length;

    private Double beam;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
