package SANTA.backend.post.infra;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.LikeEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.LikeRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class LikeRepositoryTest {
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @Transactional
    void 게시글_좋아요_조회_및_카운트() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("테스트 게시글")
                .author("작성자")
                .body("내용")
                .postHits(0)
                .fileAttached(0)
                .build();

        PostEntity savedPost = postRepository.save(post);

        LikeEntity like = LikeEntity.builder()
                .userId(10L)
                .post(savedPost)
                .build();

        likeRepository.save(like);

        // when
        Optional<LikeEntity> foundLike = likeRepository.findByUserIdAndPost_PostId(10L, savedPost.getPostId());
        Long likeCount = likeRepository.countByPost_PostId(savedPost.getPostId());

        // then
        assertThat(foundLike).isPresent();
        assertThat(likeCount).isEqualTo(1L);
    }
    @Test
    @Transactional
    void 댓글_좋아요_조회_및_카운트() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("댓글 있는 게시글")
                .author("작성자")
                .body("내용")
                .postHits(0)
                .fileAttached(0)
                .build();

        PostEntity savedPost = postRepository.save(post);

        CommentEntity comment = CommentEntity.builder()
                .commentWriter("댓글작성자")
                .commentBody("댓글입니다")
                .postEntity(savedPost)
                .build();

        CommentEntity savedComment = commentRepository.save(comment);

        LikeEntity like = LikeEntity.builder()
                .userId(30L)
                .comment(savedComment)
                .build();

        likeRepository.save(like);

        // when
        Optional<LikeEntity> foundLike = likeRepository.findByUserIdAndComment_CommentId(30L, savedComment.getCommentId());
        Long likeCount = likeRepository.countByComment_CommentId(savedComment.getCommentId());

        // then
        assertThat(foundLike).isPresent();
        assertThat(likeCount).isEqualTo(1L);
    }
}
