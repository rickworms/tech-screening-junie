package com.rockstars.musicapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Represents an artist/band in the music system.
 * 
 * This model corresponds to the structure found in artists.json.
 */
public class Artist {
    
    @JsonProperty("Id")
    @NotNull(message = "Artist ID cannot be null")
    private Long id;
    
    @JsonProperty("Name")
    @NotBlank(message = "Artist name cannot be blank")
    private String name;
    
    // Default constructor for Jackson
    public Artist() {}
    
    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) && Objects.equals(name, artist.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}