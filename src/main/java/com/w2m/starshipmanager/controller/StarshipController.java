package com.w2m.starshipmanager.controller;

import com.w2m.starshipmanager.model.starship.StarshipCreateRequest;
import com.w2m.starshipmanager.model.starship.StarshipResponse;
import com.w2m.starshipmanager.service.StarshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class StarshipController {

    private final StarshipService starshipService;

    @GetMapping("/starships")
    public ResponseEntity<Page<StarshipResponse>> getAll(
            @PositiveOrZero @RequestParam(defaultValue = "0") final int page,
            @Min(0) @RequestParam(defaultValue = "10") final int size
    ) {
        final Page<StarshipResponse> starshipsDto = this.starshipService.getAll(PageRequest.of(page, size));

        return ResponseEntity.ok(starshipsDto);
    }

    @PostMapping("/starships")
    public ResponseEntity<StarshipResponse> create(
            @NotNull @Valid @RequestBody final StarshipCreateRequest request
    ) {
        StarshipResponse response = this.starshipService.create(request);

        long id = response.getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }


}
