package com.collections.repository;


import com.collections.pojo.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>{
    Collection findCollectionByCollectionId(Long collectionId);
    List<Collection> findCollectionsByUserIdAndParentCollectionIdIsNullAndIsDeletedFalse(Long userId);
    List<Collection> findCollectionsByParentCollectionIdAndIsDeletedFalse(Long parentCollectionId);

    @Query("SELECT c FROM Collection c WHERE c.title LIKE CONCAT('%', :title, '%') AND c.isDeleted = false")
    List<Collection> findByTitleContainingAndIsDeletedFalse(@Param("title") String title);

    @Query("SELECT c FROM Collection c WHERE c.title LIKE CONCAT('%', :title, '%') AND c.userId = :userId AND c.isDeleted = false")
    List<Collection> findByTitleContainingAndUserIdAndIsDeletedFalse(@Param("title") String title, @Param("userId") Long userId);

    List<Collection> findCollectionsByUserIdAndIsDeletedTrue(Long userId);
}
