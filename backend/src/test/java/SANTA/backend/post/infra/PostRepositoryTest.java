package SANTA.backend.post.infra;

import SANTA.backend.core.posts.entity.LikeEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Nested
    class 조회수_증가_테스트 {

        @Test
        @Transactional
        void 게시글_조회수를_1_증가시킨다() {
            // given
            PostEntity post = PostEntity.builder()
                    .postHits(0)
                    .likes(new ArrayList<>()) // 빈 좋아요 리스트
                    .build();
            PostEntity saved = postRepository.save(post);
            Long postId = saved.getPostId();

            // when
            postRepository.updateHits(postId);
            em.flush();  // 변경 사항을 DB에 반영
            em.clear();  // 1차 캐시 비우기

            // then
            PostEntity updated = postRepository.findById(postId).orElseThrow();
            assertThat(updated.getPostHits()).isEqualTo(1L);
        }
    }

    @Nested
    class 인기_게시글_조회_테스트 {

        @Test
        @Transactional
        void 최근7일_이내_좋아요_많은_게시글을_조회한다() {
            // given
            LocalDateTime now = LocalDateTime.now();
            List<PostEntity> posts = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                PostEntity post = PostEntity.builder()
                        .postHits(0)
                        .likes(createDummyLikes(i))     // i개의 좋아요
                        .build();
                posts.add(postRepository.save(post));
            }

            em.flush();
            em.clear();

            // when
            List<PostEntity> topPosts = postRepository.findTopPostsLast7Days(now.minusDays(7), PageRequest.of(0, 3));

            // then
            assertThat(topPosts).hasSize(3);
            assertThat(topPosts.get(0).getLikes().size()).isGreaterThanOrEqualTo(topPosts.get(1).getLikes().size());
        }

        private List<LikeEntity> createDummyLikes(int count) {
            List<LikeEntity> likes = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                likes.add(LikeEntity.builder().build()); // 진짜 LikeEntity 대신 더미 객체 사용
            }
            return likes;
        }
    }
}
