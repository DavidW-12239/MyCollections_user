package com.collections.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDTO {
    String title;
    String websiteAddress;
    String description;
    String imagePath;
    boolean owned;
}
