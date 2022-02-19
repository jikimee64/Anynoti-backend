package com.anynoti.domain.animation.query;

import com.anynoti.domain.animation.Animation;
import com.anynoti.domain.animation.AnimationBox;
import com.anynoti.domain.animation.QAnimation;
import com.anynoti.domain.animation.QAnimationBox;
import com.anynoti.domain.common.BaseRepository;
import com.anynoti.domain.user.QUser;
import com.anynoti.domain.user.User;
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

    private JPAQueryFactory queryFactory;
    private QAnimationBox animationBox = QAnimationBox.animationBox;
    private QAnimation animation = QAnimation.animation;
    private QUser user = QUser.user;

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        this.queryFactory = new JPAQueryFactory(entityManager);
        super.setQueryFactory(queryFactory);
    }

    public List<Animation> findTodoAnimationsClause(
        AnimationClause animationClause
    ) {
        return selectFrom(animation)
            .innerJoin(animation.animationBoxes, animationBox)
            .where(
                equalsProviderId(animationClause.getProviderId()),
                equalsSearchKind(animationClause.getSearchKind()),
                equalsAnimationId(animationClause.getId())
            )
            //TODO: 정렬 기준 생각하기
            .orderBy(animationBox.createdDateTime.desc())
            .fetch();
    }

    public List<Animation> findTodoAnimationsByTitle(String title) {
        return selectFrom(animation)
            .innerJoin(animation.animationBoxes, animationBox)
            .where(
                animation.title.contains(title)
            )
            //TODO: 정렬 기준 생각하기
            .orderBy(animationBox.createdDateTime.desc())
            .fetch();
    }

    public AnimationDetailQuery findDetailsAnimationsClause(
        AnimationClause animationClause
    ) {
        return select(
            AnimationDetailQuery.class,
            animation.id,
            animation.thumbnail,
            animation.title,
            animation.recentEpisode,
            animation.recentDatetime,
            animationBox.memo,
            animationBox.liked,
            animationBox.notied
        )
            .from(animationBox)
            .innerJoin(animationBox.user, user)
            .innerJoin(animationBox.animation, animation)
            .where(
                equalsProviderId(animationClause.getProviderId()),
                equalsAnimationId(animationClause.getId())
            )
            .fetchOne();
    }

    public AnimationBox findAnimationBox(User user, Animation animation) {
        return selectFrom(animationBox)
            .where(
                equalsUser(user),
                equalsAnimation(animation)
            )
            .fetchOne();
    }

    private BooleanExpression equalsProviderId(String providerId) {
        if (Objects.isNull(providerId)) {
            return null;
        }
        return animationBox.user.providerId.eq(providerId);
    }

    private BooleanExpression equalsAnimationId(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return animation.id.eq(id);
    }

    private BooleanExpression equalsUser(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        return animationBox.user.eq(user);
    }

    private BooleanExpression equalsAnimation(Animation animation) {
        if (Objects.isNull(animation)) {
            return null;
        }
        return animationBox.animation.eq(animation);
    }

    private BooleanExpression equalsSearchKind(SearchKind searchKind) {
        if (Objects.isNull(searchKind)) {
            return null;
        }
        return compareSearchKind(SearchKind.findSearchKind(searchKind));
    }

    private BooleanExpression compareSearchKind(SearchKind searchKind) {
        if (searchKind == SearchKind.LIKE) {
            return animationBox.liked.eq(true);
        } else if (searchKind == SearchKind.NOTI) {
            return animationBox.notied.eq(true);
        } else {
            return null;
        }
    }

}