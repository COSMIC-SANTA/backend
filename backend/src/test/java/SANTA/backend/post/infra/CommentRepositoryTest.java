package SANTA.backend.post.infra;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.CommentType;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시글에_딸린_모든_댓글을_조회한다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("테스트 게시글")
                .author("작성자")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        PostEntity savedPost = postRepository.save(post);

        CommentEntity comment1 = CommentEntity.builder()
                .commentWriter("user1")
                .commentBody("댓글 1")
                .commentType(CommentType.COMMENT)
                .postEntity(savedPost)
                .build();

        CommentEntity comment2 = CommentEntity.builder()
                .commentWriter("user2")
                .commentBody("댓글 2")
                .commentType(CommentType.COMMENT)
                .postEntity(savedPost)
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<CommentEntity> comments = commentRepository.findAllByPostEntityOrderByCommentIdAsc(savedPost);

        // then
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).getCommentBody()).isEqualTo("댓글 1");
        assertThat(comments.get(1).getCommentBody()).isEqualTo("댓글 2");
    }

    @Test
    void 부모_댓글만_조회한다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("테스트 게시글")
                .author("작성자")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        PostEntity savedPost = postRepository.save(post);

        CommentEntity parentComment = CommentEntity.builder()
                .commentWriter("parentUser")
                .commentBody("부모 댓글")
                .commentType(CommentType.COMMENT)
                .postEntity(savedPost)
                .build();

        CommentEntity childComment = CommentEntity.builder()
                .commentWriter("childUser")
                .commentBody("대댓글")
                .commentType(CommentType.REPLY)
                .postEntity(savedPost)
                .parent(parentComment)
                .build();

        commentRepository.save(parentComment);
        commentRepository.save(childComment);

        // when
        List<CommentEntity> parents = commentRepository.findByPostEntityAndParentIsNullOrderByCommentIdAsc(savedPost);

        // then
        assertThat(parents).hasSize(1);
        assertThat(parents.get(0).getCommentBody()).isEqualTo("부모 댓글");
    }

    @Test
    void 특정_댓글의_대댓글을_조회한다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("테스트 게시글")
                .author("작성자")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        PostEntity savedPost = postRepository.save(post);

        CommentEntity parentComment = CommentEntity.builder()
                .commentWriter("parentUser")
                .commentBody("부모 댓글")
                .commentType(CommentType.COMMENT)
                .postEntity(savedPost)
                .build();

        CommentEntity childComment1 = CommentEntity.builder()
                .commentWriter("childUser1")
                .commentBody("대댓글 1")
                .commentType(CommentType.REPLY)
                .postEntity(savedPost)
                .parent(parentComment)
                .build();

        CommentEntity childComment2 = CommentEntity.builder()
                .commentWriter("childUser2")
                .commentBody("대댓글 2")
                .commentType(CommentType.REPLY)
                .postEntity(savedPost)
                .parent(parentComment)
                .build();

        commentRepository.save(parentComment);
        commentRepository.save(childComment1);
        commentRepository.save(childComment2);

        // when
        List<CommentEntity> children = commentRepository.findByParentOrderByCommentIdAsc(parentComment);

        // then
        assertThat(children).hasSize(2);
        assertThat(children).extracting("commentBody")
                .containsExactlyInAnyOrder("대댓글 1", "대댓글 2");
    }
}
