package com.data.image.controller;

import com.data.image.models.EntityImageUrl;
import com.data.image.service.EntityImageUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api")
public class EntityImageUrlController {

    private final EntityImageUrlService service;

    public EntityImageUrlController(EntityImageUrlService service) {
        this.service = service;
    }

    @GetMapping(value = "/entity")
    public ResponseEntity<EntityImageUrl> findOne(@RequestParam(value = "entity") String entity) {
        return ResponseEntity.ok(this.service.findOne(entity));
    }

    @GetMapping(value = "/entities")
    public ResponseEntity<List<EntityImageUrl>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping(value = "/entityList")
    public ResponseEntity<List<String>> findAllEntityStartWith(@RequestParam(value = "startRegex", required = false) String startRegex) {
        if (!"".equals(startRegex)) {
            return ResponseEntity.ok(this.service.findAllEntityStartWith(startRegex));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
