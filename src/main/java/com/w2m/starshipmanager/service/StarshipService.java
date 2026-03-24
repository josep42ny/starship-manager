package com.w2m.starshipmanager.service;

import com.w2m.starshipmanager.data.model.Starship;
import com.w2m.starshipmanager.data.repository.StarshipRepository;
import com.w2m.starshipmanager.model.StarshipDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class StarshipService {

    private final ObjectMapper objectMapper;
    private final StarshipRepository starshipRepository;

    public Page<StarshipDto> getAll(final PageRequest pageRequest) {
        Page<Starship> starshipsToMap = this.starshipRepository.findAll(pageRequest);

        return starshipsToMap.map(starship ->
            this.objectMapper.convertValue(starship, StarshipDto.class)
        );
    }

}
