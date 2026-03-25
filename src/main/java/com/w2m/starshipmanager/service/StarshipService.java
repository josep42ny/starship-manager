package com.w2m.starshipmanager.service;

import com.w2m.starshipmanager.data.model.Starship;
import com.w2m.starshipmanager.data.repository.StarshipRepository;
import com.w2m.starshipmanager.exception.StarshipNotFoundException;
import com.w2m.starshipmanager.model.starship.StarshipCreateRequest;
import com.w2m.starshipmanager.model.starship.StarshipModifyRequest;
import com.w2m.starshipmanager.model.starship.StarshipResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class StarshipService {

    private final ObjectMapper objectMapper;
    private final StarshipRepository starshipRepository;

    public Page<StarshipResponse> getAll(final PageRequest pageRequest, final String nameSearch) {
        final Page<Starship> starshipsToMap;

        if (nameSearch == null || nameSearch.isBlank()) {
            starshipsToMap = this.starshipRepository.findAll(pageRequest);
        } else {
            starshipsToMap = this.starshipRepository.findAllByNameContainingIgnoreCase(pageRequest, nameSearch);
        }

        return starshipsToMap.map(this::mapToResponse);
    }

    @Cacheable(value = "starships", key = "#id")
    public StarshipResponse getById(final Long id) {
        final Starship starship = this.starshipRepository.findById(id)
                .orElseThrow(() -> new StarshipNotFoundException("Starship with id " + id + " not found"));

        return this.mapToResponse(starship);
    }

    @Transactional
    public StarshipResponse create(final StarshipCreateRequest request) {
        final Starship starshipToSave = this.mapToNewStarship(request);

        final Starship savedStarship = this.starshipRepository.saveAndFlush(starshipToSave);

        return this.mapToResponse(savedStarship);
    }

    @CacheEvict(value = "starships", key = "#id")
    @Transactional
    public StarshipResponse edit(final long id, final StarshipModifyRequest request) {
        final Starship starshipToEdit = this.starshipRepository.findById(id)
                .orElseThrow(() -> new StarshipNotFoundException("Starship with id " + id + " not found"));

        this.objectMapper.updateValue(starshipToEdit, request);
        final Starship editedStarship = this.starshipRepository.saveAndFlush(starshipToEdit);

        return this.mapToResponse(editedStarship);
    }

    @CacheEvict(value = "starships", key = "#id")
    public void delete(final Long id) {
        if (!this.starshipRepository.existsById(id)) {
            throw new StarshipNotFoundException("Starship with id " + id + " not found");
        }

        this.starshipRepository.deleteById(id);
    }

    private Starship mapToNewStarship(final StarshipCreateRequest request) {
        return Starship.builder()
                .name(request.getName())
                .length(request.getLength())
                .beam(request.getBeam())
                .build();
    }

    private StarshipResponse mapToResponse(final Starship entity) {
        final StarshipResponse response = this.objectMapper.convertValue(entity, StarshipResponse.class);
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
}
