package com.rockstars.musicapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a song in the music system.
 * 
 * This model corresponds to the structure found in songs.json.
 */
@Entity
@Table(name = "songs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    @NotNull(message = "Song ID cannot be null")
    private Long id;

    @Column(nullable = false)
    @JsonProperty("Name")
    @NotBlank(message = "Song name cannot be blank")
    private String name;

    @Column(nullable = false)
    @JsonProperty("Year")
    @NotNull(message = "Year cannot be null")
    @Positive(message = "Year must be positive")
    private Integer year;

    @Column(nullable = false)
    @JsonProperty("Artist")
    @NotBlank(message = "Artist name cannot be blank")
    private String artist;

    @JsonProperty("Shortname")
    private String shortname;

    @JsonProperty("Bpm")
    @Positive(message = "BPM must be positive")
    private Integer bpm;

    @JsonProperty("Duration")
    @Positive(message = "Duration must be positive")
    private Long duration;

    @Column(nullable = false)
    @JsonProperty("Genre")
    @NotBlank(message = "Genre cannot be blank")
    private String genre;

    @JsonProperty("SpotifyId")
    private String spotifyId;

    @JsonProperty("Album")
    private String album;
}
