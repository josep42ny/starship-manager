package com.w2m.starshipmanager.service;

import com.w2m.starshipmanager.data.model.Starship;
import com.w2m.starshipmanager.data.repository.StarshipRepository;
import com.w2m.starshipmanager.exception.StarshipNotFoundException;
import com.w2m.starshipmanager.model.starship.StarshipCreateRequest;
import com.w2m.starshipmanager.model.starship.StarshipModifyRequest;
import com.w2m.starshipmanager.model.starship.StarshipResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StarshipService {

    private final ObjectMapper objectMapper;
    private final StarshipRepository starshipRepository;

    public Page<StarshipResponse> getAll(final PageRequest pageRequest) {
        final Page<Starship> starshipsToMap = this.starshipRepository.findAll(pageRequest);

        return starshipsToMap.map(starship ->
                this.objectMapper.convertValue(starship, StarshipResponse.class)
        );
    }

    @Transactional
    public StarshipResponse create(final StarshipCreateRequest request) {
        final Starship starshipToSave = this.mapToNewStarship(request);
        final Starship response = this.starshipRepository.save(starshipToSave);
        return this.objectMapper.convertValue(response, StarshipResponse.class);
    }

    @Transactional
    public StarshipResponse edit(final long id, final StarshipModifyRequest request) {
        final Starship starshipToEdit = this.starshipRepository.findById(id)
                .orElseThrow(() -> new StarshipNotFoundException("Starship with id " + id + " not found"));

        this.objectMapper.updateValue(starshipToEdit, request);
        this.starshipRepository.save(starshipToEdit);

        return this.objectMapper.convertValue(starshipToEdit, StarshipResponse.class);
    }

    private Starship mapToNewStarship(final StarshipCreateRequest request) {
        return Starship.builder()
                .name(request.getName())
                .length(request.getLength())
                .beam(request.getBeam())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
