package com.rockstars.musicapi.service;

import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Song business logic.
 * 
 * Handles CRUD operations and business rules for songs.
 */
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    /**
     * Retrieves all songs.
     * 
     * @return List of all songs
     */
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    /**
     * Finds a song by ID.
     * 
     * @param id the song ID
     * @return Optional containing the song if found
     */
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    /**
     * Searches for songs by genre (case-insensitive).
     * 
     * @param genre the genre to search for
     * @return List of songs matching the genre
     */
    public List<Song> getSongsByGenre(String genre) {
        return songRepository.findByGenreIgnoreCase(genre);
    }

    /**
     * Finds songs released before the specified year.
     * This method implements the requirement to filter songs released before 2016.
     * 
     * @param year the year threshold
     * @return List of songs released before the given year
     */
    public List<Song> getSongsReleasedBefore(Integer year) {
        return songRepository.findByYearBefore(year);
    }


    /**
     * Finds songs by artist name (case-insensitive).
     * 
     * @param artistName the artist name
     * @return List of songs by the specified artist
     */
    public List<Song> getSongsByArtist(String artistName) {
        return songRepository.findByArtistIgnoreCase(artistName);
    }

    /**
     * Searches for songs by name (case-insensitive partial match).
     * 
     * @param name the search term
     * @return List of matching songs
     */
    public List<Song> searchSongsByName(String name) {
        return songRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Finds songs by genre and released before the specified year.
     * 
     * @param genre the genre to search for
     * @param year the year threshold
     * @return List of songs matching both criteria
     */
    public List<Song> getSongsByGenreAndYearBefore(String genre, Integer year) {
        return songRepository.findByGenreIgnoreCaseAndYearBefore(genre, year);
    }

    /**
     * Creates a new song.
     * 
     * @param song the song to create
     * @return the created song
     */
    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    /**
     * Updates an existing song.
     * 
     * @param id the song ID
     * @param updatedSong the updated song data
     * @return the updated song
     * @throws IllegalArgumentException if song not found
     */
    public Song updateSong(Long id, Song updatedSong) {
        Optional<Song> existingSong = songRepository.findById(id);
        if (existingSong.isEmpty()) {
            throw new IllegalArgumentException("Song with ID " + id + " not found");
        }

        updatedSong.setId(id);
        return songRepository.save(updatedSong);
    }

    /**
     * Deletes a song by ID.
     * 
     * @param id the song ID
     * @return true if the song was deleted, false if not found
     */
    public boolean deleteSong(Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Checks if a song exists by ID.
     * 
     * @param id the song ID
     * @return true if the song exists
     */
    public boolean existsById(Long id) {
        return songRepository.existsById(id);
    }
}
