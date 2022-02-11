package com.anynoti.domain.animation.query;

import com.anynoti.domain.animation.Animation;
import com.anynoti.domain.animation.QAnimation;
import com.anynoti.domain.animation.QAnimationBox;
import com.anynoti.domain.common.BaseRepository;
import com.anynoti.enums.appweb.SearchKind;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class AnimationBoxQueryRepository extends BaseRepository {

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        this.queryFactory = new JPAQueryFactory(entityManager);
        super.setQueryFactory(queryFactory);
    }

    private JPAQueryFactory queryFactory;
    private QAnimationBox animationBox = QAnimationBox.animationBox;
    private QAnimation animation = QAnimation.animation;

    public List<Animation> findTodoAnimationsClause(String providerId, SearchKind searchKind){
        return selectFrom(animation)
            .innerJoin(animation.animationBoxes, animationBox)
            .where(
                equalsProviderId(providerId),
                equalsSearchKind(searchKind)
            )
            //TODO: 정렬 기준 생각하기
            .orderBy(animationBox.createdDateTime.desc())
            .fetch();
    }

    private BooleanExpression equalsProviderId(String providerId) {
        return animationBox.user.providerId.eq(providerId);
    }

    private BooleanExpression equalsSearchKind(SearchKind searchKind) {
        if (Objects.isNull(searchKind)) {
            return null;
        }
        return compareSearchKind(SearchKind.findSearchKind(searchKind));
    }

    private BooleanExpression compareSearchKind(SearchKind searchKind){
        if(searchKind == SearchKind.LIKE){
            return animationBox.liked.eq(true);
        }else if(searchKind == SearchKind.NOTI){
            return animationBox.notied.eq(true);
        }else{
            return null;
        }
    }

}