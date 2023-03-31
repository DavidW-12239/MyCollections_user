package com.collections.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    boolean isDeleted;

    //do not delete
    public boolean getIsOwned() {
        return isOwned;
    }
    public void setIsOwned(boolean owned) {
        isOwned = owned;
    }
}
