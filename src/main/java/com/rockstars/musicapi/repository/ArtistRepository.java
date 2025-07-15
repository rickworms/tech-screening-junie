package com.rockstars.musicapi.repository;

import com.rockstars.musicapi.model.Artist;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Artist data access operations.
 * 
 * Provides CRUD operations and search functionality for artists.
 */
public interface ArtistRepository {
    
    /**
     * Retrieves all artists.
     * 
     * @return List of all artists
     */
    List<Artist> findAll();
    
    /**
     * Finds an artist by ID.
     * 
     * @param id the artist ID
     * @return Optional containing the artist if found, empty otherwise
     */
    Optional<Artist> findById(Long id);
    
    /**
     * Finds an artist by name (case-insensitive).
     * 
     * @param name the artist name
     * @return Optional containing the artist if found, empty otherwise
     */
    Optional<Artist> findByName(String name);
    
    /**
     * Finds artists whose names contain the given search term (case-insensitive).
     * 
     * @param name the search term
     * @return List of matching artists
     */
    List<Artist> findByNameContaining(String name);
    
    /**
     * Saves an artist (create or update).
     * 
     * @param artist the artist to save
     * @return the saved artist
     */
    Artist save(Artist artist);
    
    /**
     * Deletes an artist by ID.
     * 
     * @param id the artist ID
     * @return true if the artist was deleted, false if not found
     */
    boolean deleteById(Long id);
    
    /**
     * Checks if an artist exists by ID.
     * 
     * @param id the artist ID
     * @return true if the artist exists, false otherwise
     */
    boolean existsById(Long id);
    
    /**
     * Checks if an artist exists by name (case-insensitive).
     * 
     * @param name the artist name
     * @return true if the artist exists, false otherwise
     */
    boolean existsByName(String name);
}