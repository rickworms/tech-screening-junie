package com.rockstars.musicapi.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.repository.ArtistRepository;
import com.rockstars.musicapi.repository.SongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Data loader component that initializes the database with data from JSON files.
 * 
 * This component runs at application startup and loads artists and songs
 * from the JSON files into the database if they don't already exist.
 */
@Component
public class DataLoader implements CommandLineRunner {
    
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final ObjectMapper objectMapper;
    
    public DataLoader(ArtistRepository artistRepository, 
                     SongRepository songRepository, 
                     ObjectMapper objectMapper) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void run(String... args) throws Exception {
        loadArtists();
        loadSongs();
    }
    
    private void loadArtists() {
        try {
            // Only load if database is empty
            if (artistRepository.count() == 0) {
                ClassPathResource resource = new ClassPathResource("instructions/artists.json");
                List<Artist> artists = objectMapper.readValue(
                    resource.getInputStream(), 
                    new TypeReference<List<Artist>>() {}
                );
                
                artistRepository.saveAll(artists);
                System.out.println("Loaded " + artists.size() + " artists from JSON file");
            } else {
                System.out.println("Artists already exist in database, skipping JSON load");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load artists data", e);
        }
    }
    
    private void loadSongs() {
        try {
            // Only load if database is empty
            if (songRepository.count() == 0) {
                ClassPathResource resource = new ClassPathResource("instructions/songs.json");
                List<Song> songs = objectMapper.readValue(
                    resource.getInputStream(), 
                    new TypeReference<List<Song>>() {}
                );
                
                songRepository.saveAll(songs);
                System.out.println("Loaded " + songs.size() + " songs from JSON file");
            } else {
                System.out.println("Songs already exist in database, skipping JSON load");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load songs data", e);
        }
    }
}