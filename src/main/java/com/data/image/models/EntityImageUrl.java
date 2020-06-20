package com.data.image.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "ImageUrl_Cambridge")
@Getter
@Setter
@NoArgsConstructor
public class EntityImageUrl {
    @Id
    String entity;
    List<String> imageUrls;

    public EntityImageUrl(String entity, List<String> imageUrls) {
        this.entity = entity;
        this.imageUrls = imageUrls;
    }
}
