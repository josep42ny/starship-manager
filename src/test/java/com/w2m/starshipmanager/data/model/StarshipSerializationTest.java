package com.w2m.starshipmanager.data.model;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class StarshipSerializationTest implements WithAssertions {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldSerializeStarship() throws JacksonException {
        final LocalDateTime timestamp = LocalDateTime.of(2026, 1, 1, 0, 0);
        final Starship starship = Starship.builder()
                .id(1L)
                .name("X-wing")
                .length(240.7)
                .beam(70.7)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();

        final String json = this.objectMapper.writeValueAsString(starship);

        this.assertThat(json).contains("\"id\":1");
        this.assertThat(json).contains("\"name\":\"X-wing\"");
        this.assertThat(json).contains("\"length\":240.7");
        this.assertThat(json).contains("\"beam\":70.7");
        this.assertThat(json).contains("\"created_at\":\"2026-01-01T00:00:00\"");
        this.assertThat(json).contains("\"updated_at\":\"2026-01-01T00:00:00\"");
    }

    @Test
    public void shouldDeserializeStarship() {
        final LocalDateTime timestamp = LocalDateTime.of(2026, 1, 1, 0, 0);
        final String json = """
                {
                    "id": 1,
                    "name": "X-wing",
                    "length": 240.7,
                    "beam": 70.7,
                    "created_at": 2026-01-01T00:00:00",
                    "updated_at": 2026-01-01T00:00:00"
                }
                """;

        final Starship starship = this.objectMapper.readValue(json, Starship.class);

        this.assertThat(starship.getId()).isEqualTo(1L);
        this.assertThat(starship.getName()).isEqualTo("X-wing");
        this.assertThat(starship.getLength()).isEqualTo(240.7);
        this.assertThat(starship.getBeam()).isEqualTo(70.7);
        this.assertThat(starship.getCreatedAt()).isEqualTo(timestamp);
        this.assertThat(starship.getUpdatedAt()).isEqualTo(timestamp);
    }

    @Test
    public void shouldRoundTripStarship() throws JacksonException {
        final LocalDateTime timestamp = LocalDateTime.of(2026, 1, 1, 0, 0);
        final Starship originalStarship = Starship.builder()
                .id(1L)
                .name("X-wing")
                .length(240.7)
                .beam(70.7)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();

        final String json = this.objectMapper.writeValueAsString(originalStarship);
        final Starship serializedStarship = this.objectMapper.convertValue(json, Starship.class);

        this.assertThat(serializedStarship).isEqualTo(originalStarship);
    }
}
