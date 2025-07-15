package com.rockstars.musicapi.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.repository.ArtistRepository;
import com.rockstars.musicapi.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

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
        // Only load if database is empty
        if (artistRepository.count() == 0) {
            List<Artist> artists = loadEntities("instructions/artists.json", 
                        new TypeReference<List<Artist>>() {}, "artists");
            artistRepository.saveAll(artists);
            logger.info("Loaded {} artists from JSON file", artists.size());
        } else {
            logger.info("Artists already exist in database, skipping JSON load");
        }
    }

    private void loadSongs() {
        // Only load if database is empty
        if (songRepository.count() == 0) {
            List<Song> songs = loadEntities("instructions/songs.json", 
                        new TypeReference<List<Song>>() {}, "songs");
            songRepository.saveAll(songs);
            logger.info("Loaded {} songs from JSON file", songs.size());
        } else {
            logger.info("Songs already exist in database, skipping JSON load");
        }
    }

    private <T> List<T> loadEntities(String jsonFilePath,
                                     TypeReference<List<T>> typeReference,
                                     String entityTypeName) {
        try {
            ClassPathResource resource = new ClassPathResource(jsonFilePath);
            List<T> entities = objectMapper.readValue(
                resource.getInputStream(), 
                typeReference
            );
            return entities;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + entityTypeName + " data", e);
        }
    }
}
