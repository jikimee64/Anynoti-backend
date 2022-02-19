package com.anynoti.domain.animation.query;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimationDetailQuery {
    private Long id;
    private String thumbnail;
    private String title;
    private Integer recentEpisode;
    private LocalDateTime recentDatetime;
    private String memo;
    private Boolean liked;
    private Boolean notied;
}