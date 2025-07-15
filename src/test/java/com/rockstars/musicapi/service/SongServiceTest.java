package com.rockstars.musicapi.service;

import com.rockstars.musicapi.model.Song;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    private Song oldSong;
    private Song newSong;
    private Song metalSong;

    @BeforeEach
    void setUp() {
        oldSong = new Song(1L, "Bohemian Rhapsody", 1975, "Queen", "bohemianrhapsody", 
                          72, 355000L, "Rock", "fJ9rUzIMcZQ", "A Night at the Opera");
        newSong = new Song(2L, "Shape of You", 2017, "Ed Sheeran", "shapeofyou", 
                          96, 233000L, "Pop", "7qiZfU4dY4M", "÷ (Divide)");
        metalSong = new Song(3L, "Master of Puppets", 1986, "Metallica", "masterofpuppets", 
                            212, 515000L, "Metal", "4uLU6hMCjMI75M1A2tKUQC", "Master of Puppets");
    }

    @Test
    void getAllSongs_ShouldReturnAllSongs() {
        // Given
        List<Song> expectedSongs = Arrays.asList(oldSong, newSong, metalSong);
        when(songRepository.findAll()).thenReturn(expectedSongs);

        // When
        List<Song> result = songService.getAllSongs();

        // Then
        assertEquals(expectedSongs, result);
        verify(songRepository).findAll();
    }

    @Test
    void getSongById_WhenSongExists_ShouldReturnSong() {
        // Given
        when(songRepository.findById(1L)).thenReturn(Optional.of(oldSong));

        // When
        Optional<Song> result = songService.getSongById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(oldSong, result.get());
        verify(songRepository).findById(1L);
    }

    @Test
    void getSongById_WhenSongDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(songRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Song> result = songService.getSongById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(songRepository).findById(999L);
    }

    @Test
    void getSongsByGenre_ShouldReturnSongsOfSpecificGenre() {
        // Given
        List<Song> metalSongs = Arrays.asList(metalSong);
        when(songRepository.findByGenreIgnoreCase("Metal")).thenReturn(metalSongs);

        // When
        List<Song> result = songService.getSongsByGenre("Metal");

        // Then
        assertEquals(metalSongs, result);
        verify(songRepository).findByGenreIgnoreCase("Metal");
    }

    @Test
    void getSongsReleasedBefore_ShouldReturnSongsBeforeSpecifiedYear() {
        // Given
        List<Song> songsBeforeYear = Arrays.asList(oldSong, metalSong);
        when(songRepository.findByYearBefore(2000)).thenReturn(songsBeforeYear);

        // When
        List<Song> result = songService.getSongsReleasedBefore(2000);

        // Then
        assertEquals(songsBeforeYear, result);
        verify(songRepository).findByYearBefore(2000);
    }

    @Test
    void getSongsReleasedBefore2016_ShouldReturnSongsBeforeYear2016() {
        // Given
        List<Song> songsBefore2016 = Arrays.asList(oldSong, metalSong);
        when(songRepository.findByYearBefore(2016)).thenReturn(songsBefore2016);

        // When
        List<Song> result = songService.getSongsReleasedBefore(2016);

        // Then
        assertEquals(songsBefore2016, result);
        verify(songRepository).findByYearBefore(2016);
    }

    @Test
    void getSongsByArtist_ShouldReturnSongsBySpecificArtist() {
        // Given
        List<Song> queenSongs = Arrays.asList(oldSong);
        when(songRepository.findByArtistIgnoreCase("Queen")).thenReturn(queenSongs);

        // When
        List<Song> result = songService.getSongsByArtist("Queen");

        // Then
        assertEquals(queenSongs, result);
        verify(songRepository).findByArtistIgnoreCase("Queen");
    }

    @Test
    void searchSongsByName_ShouldReturnMatchingSongs() {
        // Given
        List<Song> matchingSongs = Arrays.asList(oldSong);
        when(songRepository.findByNameContainingIgnoreCase("Bohemian")).thenReturn(matchingSongs);

        // When
        List<Song> result = songService.searchSongsByName("Bohemian");

        // Then
        assertEquals(matchingSongs, result);
        verify(songRepository).findByNameContainingIgnoreCase("Bohemian");
    }

    @Test
    void getSongsByGenreAndYearBefore_ShouldReturnFilteredSongs() {
        // Given
        List<Song> filteredSongs = Arrays.asList(metalSong);
        when(songRepository.findByGenreIgnoreCaseAndYearBefore("Metal", 2000)).thenReturn(filteredSongs);

        // When
        List<Song> result = songService.getSongsByGenreAndYearBefore("Metal", 2000);

        // Then
        assertEquals(filteredSongs, result);
        verify(songRepository).findByGenreIgnoreCaseAndYearBefore("Metal", 2000);
    }

    @Test
    void createSong_ShouldCreateAndReturnSong() {
        // Given
        Song newSongToCreate = new Song(null, "New Song", 2023, "New Artist", "newsong", 
                                       120, 200000L, "Pop", "newSpotifyId", "New Album");
        Song createdSong = new Song(4L, "New Song", 2023, "New Artist", "newsong", 
                                   120, 200000L, "Pop", "newSpotifyId", "New Album");
        when(songRepository.save(newSongToCreate)).thenReturn(createdSong);

        // When
        Song result = songService.createSong(newSongToCreate);

        // Then
        assertEquals(createdSong, result);
        verify(songRepository).save(newSongToCreate);
    }

    @Test
    void updateSong_WhenSongExists_ShouldUpdateSong() {
        // Given
        Song updatedSong = new Song(null, "Updated Song", 2023, "Updated Artist", "updatedsong", 
                                   130, 250000L, "Rock", "updatedSpotifyId", "Updated Album");
        Song savedSong = new Song(1L, "Updated Song", 2023, "Updated Artist", "updatedsong", 
                                 130, 250000L, "Rock", "updatedSpotifyId", "Updated Album");
        when(songRepository.findById(1L)).thenReturn(Optional.of(oldSong));
        when(songRepository.save(any(Song.class))).thenReturn(savedSong);

        // When
        Song result = songService.updateSong(1L, updatedSong);

        // Then
        assertEquals(savedSong, result);
        verify(songRepository).findById(1L);
        verify(songRepository).save(any(Song.class));
    }

    @Test
    void updateSong_WhenSongDoesNotExist_ShouldThrowException() {
        // Given
        Song updatedSong = new Song(null, "Updated Song", 2023, "Updated Artist", "updatedsong", 
                                   130, 250000L, "Rock", "updatedSpotifyId", "Updated Album");
        when(songRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> songService.updateSong(999L, updatedSong)
        );
        assertEquals("Song with ID 999 not found", exception.getMessage());
        verify(songRepository).findById(999L);
        verify(songRepository, never()).save(any());
    }

    @Test
    void deleteSong_WhenSongExists_ShouldReturnTrue() {
        // Given
        when(songRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = songService.deleteSong(1L);

        // Then
        assertTrue(result);
        verify(songRepository).existsById(1L);
        verify(songRepository).deleteById(1L);
    }

    @Test
    void deleteSong_WhenSongDoesNotExist_ShouldReturnFalse() {
        // Given
        when(songRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = songService.deleteSong(999L);

        // Then
        assertFalse(result);
        verify(songRepository).existsById(999L);
        verify(songRepository, never()).deleteById(999L);
    }

    @Test
    void existsById_WhenSongExists_ShouldReturnTrue() {
        // Given
        when(songRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = songService.existsById(1L);

        // Then
        assertTrue(result);
        verify(songRepository).existsById(1L);
    }

    @Test
    void existsById_WhenSongDoesNotExist_ShouldReturnFalse() {
        // Given
        when(songRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = songService.existsById(999L);

        // Then
        assertFalse(result);
        verify(songRepository).existsById(999L);
    }
}
