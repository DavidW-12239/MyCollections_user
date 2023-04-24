package com.collections.dao.impl;

import com.collections.dao.CollectionDao;
import com.collections.pojo.Collection;
import com.collections.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionDaoImpl implements CollectionDao {
    @Autowired
    CollectionRepository collectionRepository;

    @Override
    public Collection getCollectionByCollectionId(Long collectionId) {
        return collectionRepository.findCollectionByCollectionId(collectionId);
    }

    @Override
    public List<Collection> getMainCollectionsByUser(Long userId) {
        return collectionRepository.findCollectionsByUserIdAndParentCollectionIdIsNullAndIsDeletedFalse(userId);
    }

    @Override
    public List<Collection> getSubCollectionsByCollection(Long parentCollectionId) {
        return collectionRepository.findCollectionsByParentCollectionIdAndIsDeletedFalse(parentCollectionId);
    }

    @Override
    public List<Collection> getCollectionsByTitle(String title) {
        return collectionRepository.findByTitleContainingAndIsDeletedFalse(title);
    }

    @Override
    public List<Collection> getCollectionsByTitleAndUser(String title, Long userId) {
        return collectionRepository.findByTitleContainingAndUserIdAndIsDeletedFalse(title,userId);
    }

    @Override
    public List<Collection> getDeletedCollectionsByUser(Long userId) {
        return collectionRepository.findCollectionsByUserIdAndIsDeletedTrue(userId);
    }

    @Override
    public void addCollection(Collection collection) {
        collectionRepository.save(collection);
    }

    @Override
    public void updateCollectionContext(Collection collection) {
        collectionRepository.save(collection);
    }

    @Override
    public void updateCollectionImage(String image, Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        collection.setImage(image);
        collectionRepository.save(collection);
    }

    @Override
    public void updateCollectionIsOwned(boolean isOwned, Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        collection.setIsOwned(isOwned);
        collectionRepository.save(collection);
    }

    @Override
    public void updateCollectionIsPublic(boolean isPublic, Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        collection.setIsPublic(isPublic);
        collectionRepository.save(collection);
    }

    @Override
    public void deleteCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        collection.setDeleted(true);
        collectionRepository.save(collection);
    }

    @Override
    public void recoverCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        collection.setDeleted(false);
        collectionRepository.save(collection);
    }
}
