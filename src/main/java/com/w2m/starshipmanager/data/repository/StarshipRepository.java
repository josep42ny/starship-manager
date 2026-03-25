package com.w2m.starshipmanager.data.repository;

import com.w2m.starshipmanager.data.model.Starship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarshipRepository extends JpaRepository<Starship, Long> {

    Page<Starship> findAllByNameContainingIgnoreCase(Pageable pageable, String search);

}
