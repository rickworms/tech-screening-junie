package com.rockstars.musicapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

/**
 * Represents a song in the music system.
 * 
 * This model corresponds to the structure found in songs.json.
 */
public class Song {
    
    @JsonProperty("Id")
    @NotNull(message = "Song ID cannot be null")
    private Long id;
    
    @JsonProperty("Name")
    @NotBlank(message = "Song name cannot be blank")
    private String name;
    
    @JsonProperty("Year")
    @NotNull(message = "Year cannot be null")
    @Positive(message = "Year must be positive")
    private Integer year;
    
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
    
    @JsonProperty("Genre")
    @NotBlank(message = "Genre cannot be blank")
    private String genre;
    
    @JsonProperty("SpotifyId")
    private String spotifyId;
    
    @JsonProperty("Album")
    private String album;
    
    // Default constructor for Jackson
    public Song() {}
    
    public Song(Long id, String name, Integer year, String artist, String shortname, 
                Integer bpm, Long duration, String genre, String spotifyId, String album) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.artist = artist;
        this.shortname = shortname;
        this.bpm = bpm;
        this.duration = duration;
        this.genre = genre;
        this.spotifyId = spotifyId;
        this.album = album;
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
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getShortname() {
        return shortname;
    }
    
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    
    public Integer getBpm() {
        return bpm;
    }
    
    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }
    
    public Long getDuration() {
        return duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getSpotifyId() {
        return spotifyId;
    }
    
    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id) && 
               Objects.equals(name, song.name) && 
               Objects.equals(year, song.year) && 
               Objects.equals(artist, song.artist) && 
               Objects.equals(genre, song.genre);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, artist, genre);
    }
    
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}