package com.anynoti.animation.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.anynoti.domain.animation.Animation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class AnimationResponse {
    private Long id;
    private String thumbnail;
    private String title;
    private Integer recent_episode;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime recent_datetime;
    private Boolean end;
    private String memo;
    private Boolean liked;
    private Boolean notied;

    public static AnimationResponse ofTodos(Animation animation) {
        return AnimationResponse.builder()
            .id(animation.getId())
            .thumbnail(animation.getThumbnail())
            .title(animation.getTitle())
            .recent_episode(animation.getRecentEpisode())
            .end(animation.isEnd())
            .build();
    }

}