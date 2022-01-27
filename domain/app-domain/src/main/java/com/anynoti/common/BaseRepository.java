package com.anynoti.common;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
public abstract class BaseRepository extends QuerydslRepositorySupport {

    public BaseRepository() {
        super(BaseRepository.class);
    }

    private JPAQueryFactory queryFactory;

    protected void setQueryFactory(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    protected <DTO> JPAQuery<DTO> select(Expression<DTO> expr) {
        return queryFactory.select(expr);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return queryFactory.selectFrom(from);
    }

    public <T> T save(T entity) {
        Objects.requireNonNull(super.getEntityManager()).persist(entity);
        return entity;
    }

    public <T> List<T> saveAll(List<T> entities) {
        List<T> result = new ArrayList<T>();
        for (T entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    protected <DTO> JPAQuery<DTO> select(Class<DTO> clazz, Expression<?>... exprs) {
        return queryFactory.select(Projections.fields(clazz, exprs));
    }

    protected <T> BooleanExpression condition(T value, Function<T, BooleanExpression> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }

}