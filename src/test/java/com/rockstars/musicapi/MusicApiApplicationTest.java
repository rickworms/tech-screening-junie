package com.rockstars.musicapi;

import com.rockstars.musicapi.service.ArtistService;
import com.rockstars.musicapi.service.SongService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify the application starts correctly and data loads properly.
 * Uses Testcontainers to run PostgreSQL in a Docker container for realistic integration testing.
 */
@SpringBootTest
@Testcontainers
class MusicApiApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(MusicApiApplicationTest.class);

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @Test
    void contextLoads() {
        // Verify that the Spring context loads successfully
        assertNotNull(artistService);
        assertNotNull(songService);
    }

    @Test
    void dataLoadsCorrectly() {
        // Verify that JSON data is loaded
        var artists = artistService.getAllArtists();
        var songs = songService.getAllSongs();

        assertFalse(artists.isEmpty(), "Artists should be loaded from JSON");
        assertFalse(songs.isEmpty(), "Songs should be loaded from JSON");

        logger.info("[DEBUG_LOG] Loaded {} artists", artists.size());
        logger.info("[DEBUG_LOG] Loaded {} songs", songs.size());
    }

    @Test
    void metalArtistsFilterWorks() {
        // Verify that Metal artists filtering works
        var metalArtists = artistService.getArtistsByGenre("Metal");

        assertFalse(metalArtists.isEmpty(), "Should find Metal artists");
        logger.info("[DEBUG_LOG] Found {} Metal artists", metalArtists.size());

        // Print first few Metal artists for verification
        metalArtists.stream()
                .limit(5)
                .forEach(artist -> logger.info("[DEBUG_LOG] Metal artist: {}", artist.getName()));
    }

    @Test
    void songsBefore2016FilterWorks() {
        // Verify that songs before 2016 filtering works
        var songsBefore2016 = songService.getSongsReleasedBefore(2016);

        assertFalse(songsBefore2016.isEmpty(), "Should find songs before 2016");
        logger.info("[DEBUG_LOG] Found {} songs before 2016", songsBefore2016.size());

        // Verify all songs are actually before 2016
        boolean allBefore2016 = songsBefore2016.stream()
                .allMatch(song -> song.getYear() < 2016);
        assertTrue(allBefore2016, "All songs should be released before 2016");

        // Print first few songs for verification
        songsBefore2016.stream()
                .limit(5)
                .forEach(song -> logger.info("[DEBUG_LOG] Song before 2016: {} ({})", song.getName(), song.getYear()));
    }
}
