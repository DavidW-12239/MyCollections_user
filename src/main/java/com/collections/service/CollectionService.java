package com.collections.service;

import com.collections.dto.CollectionDTO;
import com.collections.pojo.Collection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionService {

    List<Collection> getMainCollectionsByUser(Long userId);
    List<Collection> getSubCollectionsByCollection(Long parentCollectionId);
    List<Collection> searchByTitle(String title);
    Collection getCollectionByCollectionId(Long collectionId);
    void addMainCollection(CollectionDTO collectionDTO, Long userId);
    void addSubCollection(CollectionDTO collectionDTO, Long parentCollectionId);
    void updateCollection(CollectionDTO collectionDTO, Long collectionId);
    void deleteCollection(Long collectionId);
}
