package com.collections.service.impl;

import com.collections.dto.CollectionDTO;
import com.collections.exception.NotEmptySubCollectionException;
import com.collections.mapper.CollectionMapper;
import com.collections.pojo.Collection;
import com.collections.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Collection> getCollectionsById(Long collectionId) {
        Collection collection = collectionMapper.getCollectionById(collectionId);
        List<Collection> collections;
        if (collection.getParentCollectionId() == null){
            collections = collectionMapper.getMainCollectionsByUser(collection.getUserId());
        } else {
            collections = collectionMapper.getSubCollectionsByCollection(collection.getParentCollectionId());
        }
        return collections;
    }

    @Override
    public List<Collection> searchByTitle(String title) {
        List<Collection> collections = collectionMapper.getCollectionsByTitle(title);
        return collections;
    }

    @Override
    public List<Collection> getDeletedCollectionsByUser(Long userId) {
        List<Collection> collections = collectionMapper.getDeletedCollectionsByUser(userId);
        return collections;
    }

    @Override
    public Collection getCollectionByCollectionId(Long collectionId) {
        return collectionMapper.getCollectionById(collectionId);
    }

    @Override
    public Collection addMainCollection(CollectionDTO collectionDTO, Long userId, String imagePath) {
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                imagePath, collectionDTO.getIsOwned(), null, false);
        collectionMapper.addCollection(collection);
        return collection;
    }

    @Override
    public Collection addSubCollection(CollectionDTO collectionDTO, Long parentCollectionId, String imagePath) {
        Long userId = collectionMapper.getCollectionById(parentCollectionId).getUserId();
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                imagePath, collectionDTO.getIsOwned(), parentCollectionId, false);
        collectionMapper.addCollection(collection);
        return collection;
    }

    @Override
    public Collection updateCollectionContext(CollectionDTO collectionDTO, Long collectionId) {
        Collection collection = collectionMapper.getCollectionById(collectionId);
        collection.setTitle(collectionDTO.getTitle());
        collection.setWebsiteAddress(collectionDTO.getWebsiteAddress());
        collection.setDescription(collectionDTO.getDescription());
        collection.setIsOwned(collectionDTO.getIsOwned());
        collectionMapper.updateCollectionContext(collection);
        collection = collectionMapper.getCollectionById(collectionId);
        return collection;
    }

    @Override
    public Collection updateCollectionImage(String image, Long collectionId) {
        Collection collection = collectionMapper.getCollectionById(collectionId);
        collectionMapper.updateCollectionImage(image, collection.getCollectionId());
        collection = collectionMapper.getCollectionById(collectionId);
        return collection;
    }

    @Override
    public void deleteCollectionById(Long collectionId) {
        collectionMapper.deleteCollectionById(collectionId);
        List<Collection> subCollections = collectionMapper.getSubCollectionsByCollection(collectionId);
        //recursion
        if (subCollections.size()!=0){
            for (Collection subCollection: subCollections){
                Long subCollectionId = subCollection.getCollectionId();
                deleteCollectionById(subCollectionId);
            }
        }
    }

    @Override
    public void recoverCollection(Long collectionId) {
        Collection collection = collectionMapper.getCollectionById(collectionId);
        if (collection!=null){
            collectionMapper.recoverCollection(collectionId);
        }
    }
}
