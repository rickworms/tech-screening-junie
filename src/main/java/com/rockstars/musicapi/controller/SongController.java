package com.rockstars.musicapi.controller;

import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Song operations.
 * 
 * Provides RESTful endpoints for managing songs with CRUD operations
 * and specialized filtering by genre and release year.
 */
@RestController
@RequestMapping("/api/v1/songs")
@Tag(name = "Songs", description = "Song management operations")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    @Operation(summary = "Get all songs", description = "Retrieves a list of all songs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get song by ID", description = "Retrieves a specific song by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Song found"),
        @ApiResponse(responseCode = "404", description = "Song not found")
    })
    public ResponseEntity<Song> getSongById(
            @Parameter(description = "Song ID") @PathVariable Long id) {
        Optional<Song> song = songService.getSongById(id);
        return song.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search songs by name", description = "Searches for songs by name (case-insensitive partial match)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<Song>> searchSongs(
            @Parameter(description = "Search term for song name") @RequestParam String name) {
        List<Song> songs = songService.searchSongsByName(name);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get songs by genre", description = "Retrieves all songs of a specific genre")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs by genre")
    public ResponseEntity<List<Song>> getSongsByGenre(
            @Parameter(description = "Genre name") @PathVariable String genre) {
        List<Song> songs = songService.getSongsByGenre(genre);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/artist/{artistName}")
    @Operation(summary = "Get songs by artist", description = "Retrieves all songs by a specific artist")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs by artist")
    public ResponseEntity<List<Song>> getSongsByArtist(
            @Parameter(description = "Artist name") @PathVariable String artistName) {
        List<Song> songs = songService.getSongsByArtist(artistName);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/before-year/{year}")
    @Operation(summary = "Get songs released before year", description = "Retrieves all songs released before the specified year")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs released before the specified year")
    public ResponseEntity<List<Song>> getSongsReleasedBefore(
            @Parameter(description = "Year threshold") @PathVariable Integer year) {
        List<Song> songs = songService.getSongsReleasedBefore(year);
        return ResponseEntity.ok(songs);
    }


    @GetMapping("/filter")
    @Operation(summary = "Filter songs by genre and year", description = "Retrieves songs by genre released before the specified year")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered songs")
    public ResponseEntity<List<Song>> getFilteredSongs(
            @Parameter(description = "Genre name") @RequestParam String genre,
            @Parameter(description = "Year threshold") @RequestParam Integer year) {
        List<Song> songs = songService.getSongsByGenreAndYearBefore(genre, year);
        return ResponseEntity.ok(songs);
    }

    @PostMapping
    @Operation(summary = "Create new song", description = "Creates a new song")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Song created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
        Song createdSong = songService.createSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update song", description = "Updates an existing song")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Song updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Song not found")
    })
    public ResponseEntity<Song> updateSong(
            @Parameter(description = "Song ID") @PathVariable Long id,
            @Valid @RequestBody Song song) {
        try {
            Song updatedSong = songService.updateSong(id, song);
            return ResponseEntity.ok(updatedSong);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete song", description = "Deletes a song by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Song deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Song not found")
    })
    public ResponseEntity<Void> deleteSong(
            @Parameter(description = "Song ID") @PathVariable Long id) {
        boolean deleted = songService.deleteSong(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
}
