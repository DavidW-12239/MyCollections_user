package com.collections.service;

import com.collections.dto.CollectionDTO;
import com.collections.pojo.Collection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionService {

    List<Collection> getMainCollectionsByUser(Long userId);
    List<Collection> getSubCollectionsByCollection(Long parentCollectionId);
    List<Collection> getCollectionsById(Long collectionId);
    List<Collection> searchByTitle(String title);
    Collection getCollectionByCollectionId(Long collectionId);
    Collection addMainCollection(CollectionDTO collectionDTO, Long userId, String image);
    Collection addSubCollection(CollectionDTO collectionDTO, Long parentCollectionId, String image);
    Collection updateCollectionContext(CollectionDTO collectionDTO, Long collectionId);
    Collection updateCollectionImage(String image, Long collectionId);
    void deleteCollectionById(Long collectionId);
}
