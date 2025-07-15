package com.rockstars.musicapi.service;

import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.repository.ArtistRepository;
import com.rockstars.musicapi.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Artist business logic.
 * 
 * Handles CRUD operations and business rules for artists.
 */
@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    /**
     * Retrieves all artists.
     * 
     * @return List of all artists
     */
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    /**
     * Finds an artist by ID.
     * 
     * @param id the artist ID
     * @return Optional containing the artist if found
     */
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    /**
     * Searches for artists by name (case-insensitive partial match).
     * 
     * @param name the search term
     * @return List of matching artists
     */
    public List<Artist> searchArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Finds all artists with Metal genre songs.
     * This method filters songs by "Metal" genre and returns unique artists.
     * 
     * @return List of artists who have Metal songs
     */
    public List<Artist> getMetalArtists() {
        // Get all Metal songs
        List<String> metalArtistNames = songRepository.findByGenreIgnoreCase("Metal")
                .stream()
                .map(song -> song.getArtist())
                .distinct()
                .collect(Collectors.toList());

        // Find corresponding Artist objects
        return metalArtistNames.stream()
                .map(artistRepository::findByNameIgnoreCase)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new artist.
     * 
     * @param artist the artist to create
     * @return the created artist
     * @throws IllegalArgumentException if artist name already exists
     */
    public Artist createArtist(Artist artist) {
        if (artistRepository.existsByNameIgnoreCase(artist.getName())) {
            throw new IllegalArgumentException("Artist with name '" + artist.getName() + "' already exists");
        }
        return artistRepository.save(artist);
    }

    /**
     * Updates an existing artist.
     * 
     * @param id the artist ID
     * @param updatedArtist the updated artist data
     * @return the updated artist
     * @throws IllegalArgumentException if artist not found or name conflict
     */
    public Artist updateArtist(Long id, Artist updatedArtist) {
        Optional<Artist> existingArtist = artistRepository.findById(id);
        if (existingArtist.isEmpty()) {
            throw new IllegalArgumentException("Artist with ID " + id + " not found");
        }

        // Check for name conflicts (excluding current artist)
        Optional<Artist> artistWithSameName = artistRepository.findByNameIgnoreCase(updatedArtist.getName());
        if (artistWithSameName.isPresent() && !artistWithSameName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Artist with name '" + updatedArtist.getName() + "' already exists");
        }

        updatedArtist.setId(id);
        return artistRepository.save(updatedArtist);
    }

    /**
     * Deletes an artist by ID.
     * 
     * @param id the artist ID
     * @return true if the artist was deleted, false if not found
     */
    public boolean deleteArtist(Long id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Checks if an artist exists by ID.
     * 
     * @param id the artist ID
     * @return true if the artist exists
     */
    public boolean existsById(Long id) {
        return artistRepository.existsById(id);
    }
}
