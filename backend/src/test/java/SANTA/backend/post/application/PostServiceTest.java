package SANTA.backend.post.application;

import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.entity.LikeEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.PostFileRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import SANTA.backend.core.posts.service.PostService;
import SANTA.backend.core.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    private PostService postService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 테스트용 유저 생성
        testUser = User.registerUser("testuser", "password", "nickname");
    }

    @Test
    void 게시글_파일없이_저장_성공() throws Exception {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("테스트 제목");
        postDTO.setBody("테스트 내용");
        postDTO.setAuthor("nickname");

        // when
        PostDTO savedPost = postService.save(postDTO, testUser);

        // then
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("테스트 제목");
        assertThat(savedPost.getFileAttached()).isEqualTo(0);
    }

    @Test
    void 게시글_파일과_함께_저장_성공() throws Exception {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("파일 첨부 게시글");
        postDTO.setBody("본문입니다");
        postDTO.setAuthor("nickname");

        // Mock 파일 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "postFile", "test.jpg", "image/jpeg", "test image content".getBytes()
        );
        postDTO.setPostFile(List.of(mockFile));

        // when
        PostDTO savedPost = postService.save(postDTO, testUser);

        // then
        assertThat(savedPost.getFileAttached()).isEqualTo(1);
        assertThat(savedPost.getStoredFilename()).isNotEmpty();
    }

    @Test
    void 게시글_전체_조회() throws Exception {
        // given
        PostDTO post1 = new PostDTO();
        post1.setTitle("글1");
        post1.setBody("내용1");
        post1.setAuthor("nickname");
        postService.save(post1, testUser);

        PostDTO post2 = new PostDTO();
        post2.setTitle("글2");
        post2.setBody("내용2");
        post2.setAuthor("nickname");
        postService.save(post2, testUser);

        // when
        List<PostDTO> allPosts = postService.findAll();

        // then
        assertThat(allPosts).hasSizeGreaterThanOrEqualTo(2);
    }

    @PersistenceContext
    EntityManager em;
    @Test
    void 게시글_조회수_증가() throws Exception {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("조회수 테스트");
        postDTO.setBody("조회수 내용");
        postDTO.setAuthor("nickname");

        PostDTO saved = postService.save(postDTO, testUser);
        Long id = saved.getPostId();

        // when
        postService.updateHits(id);
        em.flush();  // 변경사항을 DB에 강제로 반영
        em.clear();  // 1차 캐시 제거

        PostDTO updated = postService.findBypostId(id);


        // then
        assertThat(updated.getPostHits()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void 게시글_수정_성공() throws Exception {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("수정 전 제목");
        postDTO.setBody("수정 전 내용");
        postDTO.setAuthor("nickname");

        PostDTO saved = postService.save(postDTO, testUser);
        saved.setTitle("수정된 제목");
        saved.setBody("수정된 내용");

        // when
        PostDTO updated = postService.update(saved);

        // then
        assertThat(updated.getTitle()).isEqualTo("수정된 제목");
        assertThat(updated.getBody()).isEqualTo("수정된 내용");
    }

    @Test
    void 게시글_삭제() throws Exception {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("삭제 테스트");
        postDTO.setBody("삭제할 게시글");
        postDTO.setAuthor("nickname");

        PostDTO saved = postService.save(postDTO, testUser);
        Long postId = saved.getPostId();

        // when
        postService.delete(postId);
        PostDTO deleted = postService.findBypostId(postId);

        // then
        assertThat(deleted).isNull();
    }

    @Test
    void 최근7일_인기게시글_조회_좋아요기준() throws Exception {
        // given
        List<PostEntity> posts = new ArrayList<>();

        // 3개의 최근 게시글 생성 (좋아요 5, 10, 15개)
        for (int i = 0; i < 3; i++) {
            PostEntity post = PostEntity.builder()
                    .title("최근 인기글 " + i)
                    .body("내용 " + i)
                    .author("nickname")
                    .build();

            for (int j = 0; j < (i + 1) * 5; j++) {
                LikeEntity like = LikeEntity.builder()
                        .userId((long) j)
                        .post(post)
                        .build();
                post.getLikes().add(like);
            }

            em.persist(post);
            posts.add(post);
        }

        // 오래된 게시글 생성 (좋아요 100개)
        PostEntity oldPost = PostEntity.builder()
                .title("오래된 게시글")
                .body("오래된 내용")
                .author("nickname")
                .build();

        for (int i = 0; i < 100; i++) {
            LikeEntity like = LikeEntity.builder()
                    .userId((long) i)
                    .post(oldPost)
                    .build();
            oldPost.getLikes().add(like);
        }

        em.persist(oldPost);
        em.flush();

        // 오래된 게시글의 createdTime을 8일 전으로 수정하는 JPQL 쿼리 실행
        em.createQuery("update PostEntity p set p.createdTime = :past where p.postId = :id")
                .setParameter("past", LocalDateTime.now().minusDays(8))
                .setParameter("id", oldPost.getPostId())
                .executeUpdate();

        em.clear();

        // when
        List<PostDTO> popularPosts = postService.findPopularPostsLast7Days();

        // then
        // 최근 게시글 3개만 조회되어야 함 (오래된 게시글 제외)
        assertThat(popularPosts).hasSize(3);

        // 좋아요 수 내림차순 정렬 확인
        for (int i = 0; i < popularPosts.size() - 1; i++) {
            int currentLikes = popularPosts.get(i).getLikeCount();
            int nextLikes = popularPosts.get(i + 1).getLikeCount();
            assertThat(currentLikes).isGreaterThanOrEqualTo(nextLikes);
        }

        // 오래된 게시글이 포함되지 않았는지 확인
        boolean containsOldPost = popularPosts.stream()
                .anyMatch(post -> post.getTitle().equals("오래된 게시글"));
        assertThat(containsOldPost).isFalse();
    }
}
