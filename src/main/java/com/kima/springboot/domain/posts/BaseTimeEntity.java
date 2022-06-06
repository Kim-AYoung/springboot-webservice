package com.kima.springboot.domain.posts;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 'BaseTimeEntity'을 상속할 경우 생성시간, 수정시간도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity { // 모든 Entity 클래스의 상위 클래스가 되어, Entity들의 생성시간, 수정시간을 자동으로 관리

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
