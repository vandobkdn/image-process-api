package com.data.image.repository;

import com.google.gson.Gson;
import com.data.image.models.EntityImageUrl;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.sun.javadoc.Doc;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class EntityImageUrlRepository {

    private final MongoTemplate mongoTemplate;

    public EntityImageUrlRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public EntityImageUrl findOne(String entity) {
        String collectionName = mongoTemplate.getCollectionName(EntityImageUrl.class);
        Query query = Query.query(Criteria.where("_id").is(entity));
        return mongoTemplate.findOne(query, EntityImageUrl.class, collectionName);
    }

    public List<EntityImageUrl> findAll(Integer size) {
        return this.getEntityImageUrlBySize(size);
    }

    public List<EntityImageUrl> findAllStartWith(String startCharacter, Integer size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").regex(startCharacter));
        Iterator<EntityImageUrl> iterator = mongoTemplate.find(query, EntityImageUrl.class).iterator();
        Gson gson = new Gson();
        List<EntityImageUrl> entityImageUrls = new ArrayList<>();
        Integer count = 0;
        while (iterator.hasNext() && count < size) {
            org.bson.Document doc = new Document(Document.parse(gson.toJson(iterator.next())));
            entityImageUrls.add(gson.fromJson(doc.toJson(), EntityImageUrl.class));
            count++;
        }
        return entityImageUrls;
    }

    public List<String> findAllEntityStartWith(String startRegex) {
        String collectionName = mongoTemplate.getCollectionName(EntityImageUrl.class);
        MongoCollection<Document> documents = mongoTemplate.getCollection(collectionName);
        FindIterable<Document>  docIterator = documents.find(
                Filters.and(
                        Filters.regex("_id", startRegex)
                )
        ).projection(Projections.exclude("imageUrl"));
        MongoCursor<org.bson.Document> cursor = docIterator.iterator();
        Gson gson = new Gson();
        List<String> entities = new ArrayList<>();
        while (cursor.hasNext()) {
            org.bson.Document doc = cursor.next();
            entities.add(doc.get("_id").toString());
        }
        return entities;
    }

    private List<EntityImageUrl> getEntityImageUrlBySize(Integer size) {
        String collectionName = mongoTemplate.getCollectionName(EntityImageUrl.class);
        MongoCollection<Document> documents = mongoTemplate.getCollection(collectionName);
        FindIterable<Document> docIterator = documents.find().limit(size);
        MongoCursor<org.bson.Document> cursor = docIterator.iterator();
        Gson gson = new Gson();

        List<EntityImageUrl> entityImageUrls = new ArrayList<>();
        while (cursor.hasNext()) {
            org.bson.Document doc = cursor.next();

            EntityImageUrl entityImageUrl = gson.fromJson(doc.toJson(), EntityImageUrl.class);
            entityImageUrl.setEntity(doc.get("_id").toString());
            entityImageUrls.add(entityImageUrl);
        }

        return entityImageUrls;
    }


}
