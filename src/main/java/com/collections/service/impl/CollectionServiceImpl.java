package com.collections.service.impl;

import com.collections.dto.CollectionDTO;
import com.collections.mapper.CollectionMapper;
import com.collections.pojo.Collection;
import com.collections.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;

    @Override
    public List<Collection> getMainCollectionsByUser(Long userId) {
        List<Collection> collections = collectionMapper.getMainCollectionsByUser(userId);
        return collections;
    }

    @Override
    public List<Collection> getSubCollectionsByCollection(Long parentCollectionId) {
        List<Collection> subCollections = collectionMapper.getSubCollectionsByCollection(parentCollectionId);
        return subCollections;
    }

    @Override
    public List<Collection> searchByTitle(String title) {
        List<Collection> collections = collectionMapper.getCollectionsByTitle(title);
        return collections;
    }

    @Override
    public Collection getCollectionByCollectionId(Long collectionId) {
        return collectionMapper.getCollectionById(collectionId);
    }

    @Override
    public void addMainCollection(CollectionDTO collectionDTO, Long userId) {
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                collectionDTO.getImagePath(), collectionDTO.isOwned(), null);
        collectionMapper.addCollection(collection);
    }

    @Override
    public void addSubCollection(CollectionDTO collectionDTO, Long parentCollectionId) {
        Long userId = collectionMapper.getCollectionById(parentCollectionId).getUserId();
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                collectionDTO.getImagePath(), collectionDTO.isOwned(), parentCollectionId);
        collectionMapper.addSubCollection(collection);
    }

    @Override
    public void updateCollection(CollectionDTO collectionDTO, Long collectionId) {
        Collection collection = collectionMapper.getCollectionById(collectionId);
        collection.setTitle(collectionDTO.getTitle());
        collection.setWebsiteAddress(collectionDTO.getWebsiteAddress());
        collection.setDescription(collectionDTO.getDescription());
        collection.setImagePath(collectionDTO.getImagePath());
        collection.setOwned(collectionDTO.isOwned());
        collectionMapper.updateCollection(collection);
    }

    @Override
    public void deleteCollection(Long collectionId) {
        collectionMapper.deleteCollection(collectionId);
    }
}
