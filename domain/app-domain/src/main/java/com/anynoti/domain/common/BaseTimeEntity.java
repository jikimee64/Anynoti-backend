package com.anynoti.domain.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDateTime;

    //엔티티가 수정될때 마다 갱신
    @LastModifiedDate
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDateTime;

}