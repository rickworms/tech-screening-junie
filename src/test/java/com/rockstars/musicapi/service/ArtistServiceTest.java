package com.rockstars.musicapi.service;

import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.repository.ArtistRepository;
import com.rockstars.musicapi.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private ArtistService artistService;

    private Artist testArtist;
    private Song testMetalSong;
    private Song testRockSong;

    @BeforeEach
    void setUp() {
        testArtist = new Artist(1L, "Metallica");
        testMetalSong = new Song(1L, "Master of Puppets", 1986, "Metallica", "masterofpuppets", 
                                212, 515000L, "Metal", "4uLU6hMCjMI75M1A2tKUQC", "Master of Puppets");
        testRockSong = new Song(2L, "Bohemian Rhapsody", 1975, "Queen", "bohemianrhapsody", 
                               72, 355000L, "Rock", "fJ9rUzIMcZQ", "A Night at the Opera");
    }

    @Test
    void getAllArtists_ShouldReturnAllArtists() {
        // Given
        List<Artist> expectedArtists = Arrays.asList(testArtist, new Artist(2L, "Queen"));
        when(artistRepository.findAll()).thenReturn(expectedArtists);

        // When
        List<Artist> result = artistService.getAllArtists();

        // Then
        assertEquals(expectedArtists, result);
        verify(artistRepository).findAll();
    }

    @Test
    void getArtistById_WhenArtistExists_ShouldReturnArtist() {
        // Given
        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));

        // When
        Optional<Artist> result = artistService.getArtistById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testArtist, result.get());
        verify(artistRepository).findById(1L);
    }

    @Test
    void getArtistById_WhenArtistDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(artistRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Artist> result = artistService.getArtistById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(artistRepository).findById(999L);
    }

    @Test
    void searchArtistsByName_ShouldReturnMatchingArtists() {
        // Given
        List<Artist> expectedArtists = Arrays.asList(testArtist);
        when(artistRepository.findByNameContainingIgnoreCase("Metal")).thenReturn(expectedArtists);

        // When
        List<Artist> result = artistService.searchArtistsByName("Metal");

        // Then
        assertEquals(expectedArtists, result);
        verify(artistRepository).findByNameContainingIgnoreCase("Metal");
    }

    @Test
    void getArtistsByGenre_ShouldReturnArtistsWithSpecifiedGenreSongs() {
        // Given
        List<Song> metalSongs = Arrays.asList(testMetalSong);
        when(songRepository.findByGenreIgnoreCase("Metal")).thenReturn(metalSongs);
        when(artistRepository.findByNameIgnoreCase("Metallica")).thenReturn(Optional.of(testArtist));

        // When
        List<Artist> result = artistService.getArtistsByGenre("Metal");

        // Then
        assertEquals(1, result.size());
        assertEquals(testArtist, result.get(0));
        verify(songRepository).findByGenreIgnoreCase("Metal");
        verify(artistRepository).findByNameIgnoreCase("Metallica");
    }

    @Test
    void getArtistsByGenre_WhenNoSongsOfSpecifiedGenre_ShouldReturnEmptyList() {
        // Given
        when(songRepository.findByGenreIgnoreCase("Metal")).thenReturn(Arrays.asList());

        // When
        List<Artist> result = artistService.getArtistsByGenre("Metal");

        // Then
        assertTrue(result.isEmpty());
        verify(songRepository).findByGenreIgnoreCase("Metal");
        verify(artistRepository, never()).findByNameIgnoreCase(anyString());
    }

    @Test
    void createArtist_WhenNameDoesNotExist_ShouldCreateArtist() {
        // Given
        Artist newArtist = new Artist(null, "New Artist");
        Artist savedArtist = new Artist(3L, "New Artist");
        when(artistRepository.existsByNameIgnoreCase("New Artist")).thenReturn(false);
        when(artistRepository.save(newArtist)).thenReturn(savedArtist);

        // When
        Artist result = artistService.createArtist(newArtist);

        // Then
        assertEquals(savedArtist, result);
        verify(artistRepository).existsByNameIgnoreCase("New Artist");
        verify(artistRepository).save(newArtist);
    }

    @Test
    void createArtist_WhenNameAlreadyExists_ShouldThrowException() {
        // Given
        Artist newArtist = new Artist(null, "Metallica");
        when(artistRepository.existsByNameIgnoreCase("Metallica")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> artistService.createArtist(newArtist)
        );
        assertEquals("Artist with name 'Metallica' already exists", exception.getMessage());
        verify(artistRepository).existsByNameIgnoreCase("Metallica");
        verify(artistRepository, never()).save(any());
    }

    @Test
    void updateArtist_WhenArtistExists_ShouldUpdateArtist() {
        // Given
        Artist updatedArtist = new Artist(null, "Updated Name");
        Artist savedArtist = new Artist(1L, "Updated Name");
        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));
        when(artistRepository.findByNameIgnoreCase("Updated Name")).thenReturn(Optional.empty());
        when(artistRepository.save(any(Artist.class))).thenReturn(savedArtist);

        // When
        Artist result = artistService.updateArtist(1L, updatedArtist);

        // Then
        assertEquals(savedArtist, result);
        verify(artistRepository).findById(1L);
        verify(artistRepository).findByNameIgnoreCase("Updated Name");
        verify(artistRepository).save(any(Artist.class));
    }

    @Test
    void updateArtist_WhenArtistDoesNotExist_ShouldThrowException() {
        // Given
        Artist updatedArtist = new Artist(null, "Updated Name");
        when(artistRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> artistService.updateArtist(999L, updatedArtist)
        );
        assertEquals("Artist with ID 999 not found", exception.getMessage());
        verify(artistRepository).findById(999L);
        verify(artistRepository, never()).save(any());
    }

    @Test
    void deleteArtist_WhenArtistExists_ShouldReturnTrue() {
        // Given
        when(artistRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = artistService.deleteArtist(1L);

        // Then
        assertTrue(result);
        verify(artistRepository).existsById(1L);
        verify(artistRepository).deleteById(1L);
    }

    @Test
    void deleteArtist_WhenArtistDoesNotExist_ShouldReturnFalse() {
        // Given
        when(artistRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = artistService.deleteArtist(999L);

        // Then
        assertFalse(result);
        verify(artistRepository).existsById(999L);
        verify(artistRepository, never()).deleteById(999L);
    }

    @Test
    void existsById_WhenArtistExists_ShouldReturnTrue() {
        // Given
        when(artistRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = artistService.existsById(1L);

        // Then
        assertTrue(result);
        verify(artistRepository).existsById(1L);
    }
}
