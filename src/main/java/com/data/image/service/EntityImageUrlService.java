package com.data.image.service;

import com.data.image.models.EntityImageUrl;
import com.data.image.repository.EntityImageUrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityImageUrlService {

    private final EntityImageUrlRepository repository;

    public EntityImageUrlService(EntityImageUrlRepository repository) {
        this.repository = repository;
    }

    public EntityImageUrl findOne(String entity) {
        return this.repository.findOne(entity);
    }

    public List<EntityImageUrl> findAll() {
//        return this.repository.findAll(3);
        return this.repository.findAllStartWith("^a", 1);
    }

    public List<String> findAllEntityStartWith(String startRegex) {
        return this.repository.findAllEntityStartWith(startRegex);
    }

}
