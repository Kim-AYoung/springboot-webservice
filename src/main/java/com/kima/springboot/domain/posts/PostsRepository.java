package com.kima.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> { // 기본적인 CRUD 메소드가 자동 생성

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // Spring Data JPA에서 제공하지 않는 메소드는 이와 같이 쿼리로 작성, Entity 클래스만으로 처리하기 어려운 복잡한 조회를 할 때는 보통 spring data jpa 대신 `querydsl`이라는 프레임워크로 사용
    List<Posts> findAllDesc();

}
