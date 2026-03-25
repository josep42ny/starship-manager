package com.w2m.starshipmanager.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "starship")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Starship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "beam", nullable = false)
    private Double beam;

    @JsonProperty("created_at")
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
