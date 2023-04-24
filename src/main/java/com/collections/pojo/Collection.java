package com.collections.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long collectionId;

    @Column(name = "userId")
    Long userId;

    @Column(name = "title")
    String title;

    @Column(name = "websiteAddress")
    String websiteAddress;

    @Column(name = "description")
    String description;

    @Column(name = "image")
    String image;

    @Column(name = "isOwned")
    boolean isOwned;

    @Column(name = "parentCollectionId")
    Long parentCollectionId;

    @Column(name = "isPublic")
    boolean isPublic;

    @Column(name = "isDeleted")
    boolean isDeleted = false;

    //do not delete
    public boolean getIsOwned() {
        return isOwned;
    }
    public void setIsOwned(boolean owned) {
        isOwned = owned;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
