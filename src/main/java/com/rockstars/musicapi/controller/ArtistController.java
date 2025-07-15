package com.rockstars.musicapi.controller;

import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Artist operations.
 * 
 * Provides RESTful endpoints for managing artists with CRUD operations
 * and specialized filtering for Metal artists.
 */
@RestController
@RequestMapping("/api/v1/artists")
@Tag(name = "Artists", description = "Artist management operations")
public class ArtistController {
    
    private final ArtistService artistService;
    
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }
    
    @GetMapping
    @Operation(summary = "Get all artists", description = "Retrieves a list of all artists")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved artists")
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get artist by ID", description = "Retrieves a specific artist by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artist found"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Artist> getArtistById(
            @Parameter(description = "Artist ID") @PathVariable Long id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search artists by name", description = "Searches for artists by name (case-insensitive partial match)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<Artist>> searchArtists(
            @Parameter(description = "Search term for artist name") @RequestParam String name) {
        List<Artist> artists = artistService.searchArtistsByName(name);
        return ResponseEntity.ok(artists);
    }
    
    @GetMapping("/metal")
    @Operation(summary = "Get Metal artists", description = "Retrieves all artists who have songs in the Metal genre")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved Metal artists")
    public ResponseEntity<List<Artist>> getMetalArtists() {
        List<Artist> metalArtists = artistService.getMetalArtists();
        return ResponseEntity.ok(metalArtists);
    }
    
    @PostMapping
    @Operation(summary = "Create new artist", description = "Creates a new artist")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Artist created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or artist name already exists")
    })
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist artist) {
        try {
            Artist createdArtist = artistService.createArtist(artist);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update artist", description = "Updates an existing artist")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artist updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or name conflict"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Artist> updateArtist(
            @Parameter(description = "Artist ID") @PathVariable Long id,
            @Valid @RequestBody Artist artist) {
        try {
            Artist updatedArtist = artistService.updateArtist(id, artist);
            return ResponseEntity.ok(updatedArtist);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete artist", description = "Deletes an artist by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Artist deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Void> deleteArtist(
            @Parameter(description = "Artist ID") @PathVariable Long id) {
        boolean deleted = artistService.deleteArtist(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
}