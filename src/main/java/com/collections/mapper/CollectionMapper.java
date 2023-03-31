package com.collections.mapper;

import com.collections.pojo.Collection;
import com.collections.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {
    Collection getCollectionById(Long collectionId);
    List<Collection> getMainCollectionsByUser(Long userId);
    List<Collection> getSubCollectionsByCollection(Long parentCollectionId);
    List<Collection> getCollectionsByTitle(String title);
    List<Collection> getCollectionsByTitleAndUser(String title, Long userId);
    List<Collection> getDeletedCollectionsByUser(Long userId);
    void addCollection(Collection collection);
    void updateCollectionContext(Collection collection);
    void updateCollectionImage(String image, Long collectionId);
    void updateCollectionIsOwned(boolean isOwned, Long collectionId);
    void deleteCollectionById(Long collectionId);
    void recoverCollection(Long collectionId);
}
