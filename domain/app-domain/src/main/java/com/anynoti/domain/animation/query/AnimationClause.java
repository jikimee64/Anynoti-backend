package com.anynoti.domain.animation.query;

import com.anynoti.enums.appweb.SearchKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimationClause {

    private String providerId;
    private SearchKind searchKind;
    private Long id;

    public static AnimationClause ofTodos(String providerId, SearchKind searchKind) {
        return AnimationClause.builder()
            .providerId(providerId)
            .searchKind(searchKind)
            .build();
    }

    public static AnimationClause ofDetails(String providerId, Long id) {
        return AnimationClause.builder()
            .providerId(providerId)
            .id(id)
            .build();
    }

}