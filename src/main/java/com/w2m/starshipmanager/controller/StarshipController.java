package com.w2m.starshipmanager.controller;

import com.w2m.starshipmanager.model.StarshipDto;
import com.w2m.starshipmanager.service.StarshipService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class StarshipController {

    private final StarshipService starshipService;

    @GetMapping("/starships")
    public ResponseEntity<Page<StarshipDto>> getAll(
            @PositiveOrZero @RequestParam(defaultValue = "0") final int page,
            @Min(0) @RequestParam(defaultValue = "10") final int size
    ) {
        final Page<StarshipDto> starshipDtos = this.starshipService.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok(starshipDtos);
    }

}
