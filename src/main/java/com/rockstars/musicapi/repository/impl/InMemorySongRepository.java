package com.rockstars.musicapi.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.musicapi.model.Song;
import com.rockstars.musicapi.repository.SongRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of SongRepository.
 * 
 * Loads song data from songs.json file and stores it in memory.
 * Provides thread-safe operations using ConcurrentHashMap.
 */
@Repository
public class InMemorySongRepository implements SongRepository {
    
    private final Map<Long, Song> songs = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    
    public InMemorySongRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadData() {
        try {
            ClassPathResource resource = new ClassPathResource("instructions/songs.json");
            List<Song> songList = objectMapper.readValue(
                resource.getInputStream(), 
                new TypeReference<List<Song>>() {}
            );
            
            for (Song song : songList) {
                songs.put(song.getId(), song);
            }
            
            System.out.println("Loaded " + songs.size() + " songs from JSON file");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load songs data", e);
        }
    }
    
    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songs.values());
    }
    
    @Override
    public Optional<Song> findById(Long id) {
        return Optional.ofNullable(songs.get(id));
    }
    
    @Override
    public List<Song> findByGenre(String genre) {
        return songs.values().stream()
                .filter(song -> song.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> findByYearBefore(Integer year) {
        return songs.values().stream()
                .filter(song -> song.getYear() < year)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> findByArtist(String artistName) {
        return songs.values().stream()
                .filter(song -> song.getArtist().equalsIgnoreCase(artistName))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> findByNameContaining(String name) {
        String searchTerm = name.toLowerCase();
        return songs.values().stream()
                .filter(song -> song.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> findByGenreAndYearBefore(String genre, Integer year) {
        return songs.values().stream()
                .filter(song -> song.getGenre().equalsIgnoreCase(genre) && song.getYear() < year)
                .collect(Collectors.toList());
    }
    
    @Override
    public Song save(Song song) {
        if (song.getId() == null) {
            // Generate new ID for new songs
            Long newId = getNextId();
            song.setId(newId);
        }
        songs.put(song.getId(), song);
        return song;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return songs.remove(id) != null;
    }
    
    @Override
    public boolean existsById(Long id) {
        return songs.containsKey(id);
    }
    
    @Override
    public Long getNextId() {
        return songs.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}