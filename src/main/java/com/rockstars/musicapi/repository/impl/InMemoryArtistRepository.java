package com.rockstars.musicapi.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.musicapi.model.Artist;
import com.rockstars.musicapi.repository.ArtistRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ArtistRepository.
 * 
 * Loads artist data from artists.json file and stores it in memory.
 * Provides thread-safe operations using ConcurrentHashMap.
 */
@Repository
public class InMemoryArtistRepository implements ArtistRepository {
    
    private final Map<Long, Artist> artists = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    
    public InMemoryArtistRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("instructions/artists.json");
            List<Artist> artistList = objectMapper.readValue(
                resource.getInputStream(), 
                new TypeReference<List<Artist>>() {}
            );
            
            for (Artist artist : artistList) {
                artists.put(artist.getId(), artist);
            }
            
            System.out.println("Loaded " + artists.size() + " artists from JSON file");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load artists data", e);
        }
    }
    
    @Override
    public List<Artist> findAll() {
        return new ArrayList<>(artists.values());
    }
    
    @Override
    public Optional<Artist> findById(Long id) {
        return Optional.ofNullable(artists.get(id));
    }
    
    @Override
    public Optional<Artist> findByName(String name) {
        return artists.values().stream()
                .filter(artist -> artist.getName().equalsIgnoreCase(name))
                .findFirst();
    }
    
    @Override
    public List<Artist> findByNameContaining(String name) {
        String searchTerm = name.toLowerCase();
        return artists.values().stream()
                .filter(artist -> artist.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public Artist save(Artist artist) {
        if (artist.getId() == null) {
            // Generate new ID for new artists
            Long newId = getNextId();
            artist.setId(newId);
        }
        artists.put(artist.getId(), artist);
        return artist;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return artists.remove(id) != null;
    }
    
    @Override
    public boolean existsById(Long id) {
        return artists.containsKey(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return artists.values().stream()
                .anyMatch(artist -> artist.getName().equalsIgnoreCase(name));
    }
    
    /**
     * Generates the next available ID for a new artist.
     * 
     * @return the next available ID
     */
    private Long getNextId() {
        return artists.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}