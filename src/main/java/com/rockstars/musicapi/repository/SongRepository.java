package com.rockstars.musicapi.repository;

import com.rockstars.musicapi.model.Song;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Song data access operations.
 * 
 * Provides CRUD operations and search functionality for songs.
 */
public interface SongRepository {
    
    /**
     * Retrieves all songs.
     * 
     * @return List of all songs
     */
    List<Song> findAll();
    
    /**
     * Finds a song by ID.
     * 
     * @param id the song ID
     * @return Optional containing the song if found, empty otherwise
     */
    Optional<Song> findById(Long id);
    
    /**
     * Finds songs by genre (case-insensitive).
     * 
     * @param genre the genre to search for
     * @return List of songs matching the genre
     */
    List<Song> findByGenre(String genre);
    
    /**
     * Finds songs released before the specified year.
     * 
     * @param year the year threshold
     * @return List of songs released before the given year
     */
    List<Song> findByYearBefore(Integer year);
    
    /**
     * Finds songs by artist name (case-insensitive).
     * 
     * @param artistName the artist name
     * @return List of songs by the specified artist
     */
    List<Song> findByArtist(String artistName);
    
    /**
     * Finds songs whose names contain the given search term (case-insensitive).
     * 
     * @param name the search term
     * @return List of matching songs
     */
    List<Song> findByNameContaining(String name);
    
    /**
     * Finds songs by genre and released before the specified year.
     * 
     * @param genre the genre to search for
     * @param year the year threshold
     * @return List of songs matching both criteria
     */
    List<Song> findByGenreAndYearBefore(String genre, Integer year);
    
    /**
     * Saves a song (create or update).
     * 
     * @param song the song to save
     * @return the saved song
     */
    Song save(Song song);
    
    /**
     * Deletes a song by ID.
     * 
     * @param id the song ID
     * @return true if the song was deleted, false if not found
     */
    boolean deleteById(Long id);
    
    /**
     * Checks if a song exists by ID.
     * 
     * @param id the song ID
     * @return true if the song exists, false otherwise
     */
    boolean existsById(Long id);
    
    /**
     * Gets the next available ID for a new song.
     * 
     * @return the next available ID
     */
    Long getNextId();
}