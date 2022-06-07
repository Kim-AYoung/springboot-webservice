package com.kima.springboot.service.posts;

import com.kima.springboot.domain.posts.Posts;
import com.kima.springboot.domain.posts.PostsRepository;
import com.kima.springboot.web.dto.PostsListResponseDto;
import com.kima.springboot.web.dto.PostsResponseDto;
import com.kima.springboot.web.dto.PostsSaveRequestDto;
import com.kima.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository; // 생성자로 Bean을 주입받는 방식 (권장)

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent()); // dirty checking : 영속성 컨텍스트가 유지된 상태이므로 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영
        return id;
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선됨
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // 람다식, 다음과 뜻이 같음 .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList()); // List로 반환
    }
}
