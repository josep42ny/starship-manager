package com.w2m.starshipmanager.controller;

import com.w2m.starshipmanager.model.starship.StarshipCreateRequest;
import com.w2m.starshipmanager.model.starship.StarshipModifyRequest;
import com.w2m.starshipmanager.model.starship.StarshipResponse;
import com.w2m.starshipmanager.service.StarshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    public ResponseEntity<?> create(
            @NotNull @Valid @RequestBody final StarshipCreateRequest request
    ) {
        final StarshipResponse response = this.starshipService.create(request);

        final long id = response.getId();
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/starships/{id}")
    public ResponseEntity<?> modify(
            @PathVariable @Positive final Long id,
            @Valid @RequestBody final StarshipModifyRequest request
    ) {
        final StarshipResponse response = this.starshipService.edit(id, request);

        final String location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();

        return ResponseEntity.ok().header("Location", location).body(response);
    }


}
