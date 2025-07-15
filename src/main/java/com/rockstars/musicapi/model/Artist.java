package com.rockstars.musicapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an artist/band in the music system.
 * 
 * This model corresponds to the structure found in artists.json.
 */
@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    @NotNull(message = "Artist ID cannot be null")
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonProperty("Name")
    @NotBlank(message = "Artist name cannot be blank")
    private String name;
}
