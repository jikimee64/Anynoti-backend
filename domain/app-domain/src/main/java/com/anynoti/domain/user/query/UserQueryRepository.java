package com.anynoti.domain.user.query;

import com.anynoti.domain.common.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;

public class UserQueryRepository extends BaseRepository {

    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        this.queryFactory = new JPAQueryFactory(entityManager);
        super.setQueryFactory(queryFactory);
    }

    private JPAQueryFactory queryFactory;

}