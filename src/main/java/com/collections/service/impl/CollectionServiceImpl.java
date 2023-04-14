package com.collections.service.impl;

import com.collections.dao.CollectionDao;
import com.collections.dto.CollectionDTO;
import com.collections.repository.CollectionRepository;
import com.collections.pojo.Collection;
import com.collections.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionDao collectionDao;

    @Override
    public List<Collection> getMainCollectionsByUser(Long userId) {
        List<Collection> collections = collectionDao.getMainCollectionsByUser(userId);
        return collections;
    }

    @Override
    public List<Collection> getSubCollectionsByCollection(Long parentCollectionId) {
        List<Collection> subCollections = collectionDao.getSubCollectionsByCollection(parentCollectionId);
        return subCollections;
    }

    @Override
    public List<Collection> getCollectionsById(Long collectionId) {
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        List<Collection> collections;
        if (collection.getParentCollectionId() == null){
            collections = collectionDao.getMainCollectionsByUser(collection.getUserId());
        } else {
            collections = collectionDao.getSubCollectionsByCollection(collection.getParentCollectionId());
        }
        return collections;
    }

    @Override
    public List<Collection> searchByTitle(String title) {
        List<Collection> collections = collectionDao.getCollectionsByTitle(title);
        return collections;
    }

    @Override
    public List<Collection> getCollectionsByTitleAndId(String title, Long userId) {
        List<Collection> collections = collectionDao.getCollectionsByTitleAndUser(title, userId);
        return collections;
    }

    @Override
    public List<Collection> getDeletedCollectionsByUser(Long userId) {
        List<Collection> collections = collectionDao.getDeletedCollectionsByUser(userId);
        return collections;
    }

    @Override
    public Collection getCollectionByCollectionId(Long collectionId) {
        return collectionDao.getCollectionByCollectionId(collectionId);
    }

    @Override
    public Collection addMainCollection(CollectionDTO collectionDTO, Long userId, String imagePath) {
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                imagePath, collectionDTO.getIsOwned(), null, collectionDTO.getIsPublic(), false);
        collectionDao.addCollection(collection);
        return collection;
    }

    @Override
    public Collection addSubCollection(CollectionDTO collectionDTO, Long parentCollectionId, String imagePath) {
        Long userId = collectionDao.getCollectionByCollectionId(parentCollectionId).getUserId();
        Collection collection = new Collection(null, userId, collectionDTO.getTitle(),
                collectionDTO.getWebsiteAddress(), collectionDTO.getDescription(),
                imagePath, collectionDTO.getIsOwned(), parentCollectionId, collectionDTO.getIsPublic(), false);
        collectionDao.addCollection(collection);
        return collection;
    }

    @Override
    public Collection updateCollectionContext(CollectionDTO collectionDTO, Long collectionId) {
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        collection.setTitle(collectionDTO.getTitle());
        collection.setWebsiteAddress(collectionDTO.getWebsiteAddress());
        collection.setDescription(collectionDTO.getDescription());
        collection.setIsOwned(collectionDTO.getIsOwned());
        collectionDao.updateCollectionContext(collection);
        collection = collectionDao.getCollectionByCollectionId(collectionId);
        return collection;
    }

    @Override
    public Collection updateCollectionImage(String image, Long collectionId) {
        collectionDao.updateCollectionImage(image, collectionId);
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        return collection;
    }

    @Override
    public Collection updateCollectionIsOwned(boolean isOwned, Long collectionId) {
        collectionDao.updateCollectionIsOwned(isOwned, collectionId);
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        return collection;
    }

    @Override
    public Collection updateCollectionIsPublic(boolean isPublic, Long collectionId) {
        collectionDao.updateCollectionIsPublic(isPublic, collectionId);
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        List<Collection> subCollections = collectionDao.getSubCollectionsByCollection(collectionId);
        //recursion
        if (subCollections.size()!=0){
            for (Collection subCollection: subCollections){
                Long subCollectionId = subCollection.getCollectionId();
                updateCollectionIsPublic(isPublic, subCollectionId);
            }
        }
        return collection;
    }

    @Override
    public void deleteCollectionById(Long collectionId) {
        collectionDao.deleteCollectionById(collectionId);
        List<Collection> subCollections = collectionDao.getSubCollectionsByCollection(collectionId);
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
        Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
        if (collection!=null){
            collectionDao.recoverCollectionById(collectionId);
        }
    }
}
