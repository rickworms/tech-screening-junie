package com.rockstars.musicapi.repository;

import com.rockstars.musicapi.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository interface for Song data access operations.
 * 
 * Provides CRUD operations and search functionality for songs using Spring Data JPA.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    /**
     * Finds songs by genre (case-insensitive).
     * 
     * @param genre the genre to search for
     * @return List of songs matching the genre
     */
    List<Song> findByGenreIgnoreCase(String genre);

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
    List<Song> findByArtistIgnoreCase(String artistName);

    /**
     * Finds songs whose names contain the given search term (case-insensitive).
     * 
     * @param name the search term
     * @return List of matching songs
     */
    List<Song> findByNameContainingIgnoreCase(String name);

    /**
     * Finds songs by genre and released before the specified year.
     * 
     * @param genre the genre to search for
     * @param year the year threshold
     * @return List of songs matching both criteria
     */
    List<Song> findByGenreIgnoreCaseAndYearBefore(String genre, Integer year);
}
