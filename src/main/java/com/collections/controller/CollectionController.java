package com.collections.controller;

import com.collections.dto.CollectionDTO;
import com.collections.pojo.Collection;
import com.collections.service.CollectionService;
import com.collections.service.UserService;
import com.collections.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/collection")
@CrossOrigin(origins = "http://localhost:3000")
public class CollectionController {
    private static final String UPLOAD_DIR = "E:\\React Project\\my-app\\public\\images";

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
    public ResponseEntity<?> addMainCollection(@PathVariable Long userId,
                                               @RequestPart(value = "collection") String collectionJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionDTO collectionDTO = objectMapper.readValue(collectionJson, CollectionDTO.class);
        String imagePath = Utils.uploadImage(image);
        Collection collection = collectionService.addMainCollection(collectionDTO, userId, imagePath);
        return ResponseEntity.ok(collection);
    }

    @PostMapping("/{collectionId}/addSubCollection")
    public ResponseEntity<?> addSubCollection(@PathVariable Long collectionId, @RequestPart(value = "collection") String collectionJson,
                                              @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionDTO collectionDTO = objectMapper.readValue(collectionJson, CollectionDTO.class);
        String imagePath = Utils.uploadImage(image);
        Collection collection = collectionService.addSubCollection(collectionDTO, collectionId, imagePath);
        return ResponseEntity.ok(collection);
    }

    @PostMapping("/{collectionId}/updateCollectionContext")
    public ResponseEntity<?> updateCollection(@PathVariable Long collectionId, @RequestPart(value = "collection") String collectionJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionDTO collectionDTO = objectMapper.readValue(collectionJson, CollectionDTO.class);
        Collection collection = collectionService.updateCollectionContext(collectionDTO, collectionId);
        return ResponseEntity.ok(collection);
    }

    @PostMapping("/{collectionId}/updateCollectionImage")
    public ResponseEntity<?> updateCollectionImage(@PathVariable Long collectionId, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        String imagePath = Utils.uploadImage(image);
        Collection collection = collectionService.updateCollectionImage(imagePath, collectionId);
        return ResponseEntity.ok(collection);
    }

    @DeleteMapping ("{userId}/{collectionId}/deleteMainCollection")
    public ResponseEntity<?> deleteMainCollection(@PathVariable Long userId, @PathVariable Long collectionId){
        collectionService.deleteCollectionById(collectionId);
        List<Collection> collections = collectionService.getMainCollectionsByUser(userId);
        return ResponseEntity.ok(collections);
    }

    @DeleteMapping ("{collectionId}/deleteSubCollection")
    public ResponseEntity<?> deleteSubCollection(@PathVariable Long collectionId){
        collectionService.deleteCollectionById(collectionId);
        List<Collection> subCollections = collectionService.getSubCollectionsByCollection(collectionId);
        return ResponseEntity.ok(subCollections);
    }


}
