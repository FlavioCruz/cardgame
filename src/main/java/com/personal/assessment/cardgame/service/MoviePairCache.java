package com.personal.assessment.cardgame.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MoviePairCache {

    public final CacheKeyGenerator cacheKeyGenerator;

    public MoviePairCache(CacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    @Cacheable(value = "moviePairsCache", key = "#gameId + '-' + @cacheKeyGenerator.generateKey(#movie1Id, #movie2Id)")
    public boolean isPairUsed(Long gameId, Long movie1Id, Long movie2Id) {
        return false;
    }
    
    @CacheEvict(value = "moviePairsCache", key = "#gameId + '-' + @cacheKeyGenerator.generateKey(#movie1Id, #movie2Id)")
    public void markPairAsUsed(Long gameId, Long movie1Id, Long movie2Id) {
    }
}