package com.anynoti.animation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchAnimationRequest {
    private String memo;
    private Boolean liked;
    private Boolean notied;
}