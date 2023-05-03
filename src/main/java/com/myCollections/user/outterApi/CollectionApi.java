package com.myCollections.user.outterApi;

import com.myCollections.user.dto.CollectionDTO;
import com.myCollections.user.dto.UpdateCollectionIsPublicRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CollectionApi {

    @Value("${collectionServiceUrl}")
    String collectionServiceUrl;

    private String generateUpdateCollectionIsPublicRequestUrl(Long collectionId){
        return collectionServiceUrl + "/collection/" + collectionId + "/updateCollectionIsPublic";
    }

    public void updateCollectionIsPublic(Long collectionId, boolean isPublic){
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = generateUpdateCollectionIsPublicRequestUrl(collectionId) + "?isPublic=" + isPublic;
        restTemplate.exchange(requestUrl, HttpMethod.POST, HttpEntity.EMPTY, CollectionDTO.class);
    }

}
