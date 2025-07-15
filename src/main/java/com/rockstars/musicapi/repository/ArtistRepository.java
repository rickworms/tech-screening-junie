package com.rockstars.musicapi.repository;

import com.rockstars.musicapi.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository interface for Artist data access operations.
 * 
 * Provides CRUD operations and search functionality for artists using Spring Data JPA.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Finds an artist by name (case-insensitive).
     * 
     * @param name the artist name
     * @return Optional containing the artist if found, empty otherwise
     */
    Optional<Artist> findByNameIgnoreCase(String name);

    /**
     * Finds artists whose names contain the given search term (case-insensitive).
     * 
     * @param name the search term
     * @return List of matching artists
     */
    List<Artist> findByNameContainingIgnoreCase(String name);

    /**
     * Checks if an artist exists by name (case-insensitive).
     * 
     * @param name the artist name
     * @return true if the artist exists, false otherwise
     */
    boolean existsByNameIgnoreCase(String name);

}
