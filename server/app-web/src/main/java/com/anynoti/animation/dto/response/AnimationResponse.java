package com.anynoti.animation.dto.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class AnimationResponse {
    private Integer id;
    private String thumbnail;
    private String title;
    private Integer recentEpisode;
    private Boolean end;
}