package com.collections.controller;

import com.collections.dto.CollectionDTO;
import com.collections.pojo.Collection;
import com.collections.pojo.User;
import com.collections.service.CollectionService;
import com.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
@CrossOrigin(origins = "http://localhost:3000")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @Autowired
    UserService userService;

    @GetMapping("/{userId}/mainCollections")
    public ResponseEntity<?> displayMainCollectionsByUser(@PathVariable Long userId){
        List<Collection> collections = collectionService.getMainCollectionsByUser(userId);
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/{collectionId}/subCollections")
    public ResponseEntity<?> displaySubCollections(@PathVariable Long collectionId){
        List<Collection> subCollections = collectionService.getSubCollectionsByCollection(collectionId);
        return ResponseEntity.ok(subCollections);
    }

    @PostMapping("/{userId}/addMainCollection")
    public ResponseEntity<?> addMainCollection(@PathVariable Long userId, @RequestBody CollectionDTO collectionDTO){
        collectionService.addMainCollection(collectionDTO, userId);
        List<Collection> collections = collectionService.getMainCollectionsByUser(userId);
        return ResponseEntity.ok(collections);
    }

    @PostMapping("/{collectionId}/addSubCollection")
    public ResponseEntity<?> addSubCollection(@PathVariable Long collectionId, @RequestBody CollectionDTO collectionDTO){
        collectionService.addSubCollection(collectionDTO, collectionId);
        List<Collection> subCollections = collectionService.getSubCollectionsByCollection(collectionId);
        return ResponseEntity.ok(subCollections);
    }

    @PostMapping("/{collectionId}/updateCollection")
    public ResponseEntity<?> updateCollection(@PathVariable Long collectionId, @RequestBody CollectionDTO collectionDTO){
        collectionService.updateCollection(collectionDTO, collectionId);
        Collection collection = collectionService.getCollectionByCollectionId(collectionId);
        List<Collection> collections;
        if (collection.getParentCollectionId() == null){
            collections = collectionService.getMainCollectionsByUser(collection.getUserId());
        } else {
            collections = collectionService.getSubCollectionsByCollection(collectionId);
        }
        return ResponseEntity.ok(collections);
    }


}
