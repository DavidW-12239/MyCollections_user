package com.collections.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    Long collectionId;
    Long userId;
    String title;
    String websiteAddress;
    String description;
    String image;
    boolean isOwned;
    Long parentCollectionId;

    public boolean getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(boolean owned) {
        isOwned = owned;
    }
}
