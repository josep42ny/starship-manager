package com.w2m.starshipmanager.controller;

import com.w2m.starshipmanager.model.StarshipDto;
import com.w2m.starshipmanager.service.StarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StarshipController {

    private final StarshipService starshipService;

    public Page<StarshipDto> getAll() {
        this.starshipService
    }

}
