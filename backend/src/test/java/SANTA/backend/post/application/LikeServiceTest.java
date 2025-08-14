package SANTA.backend.post.application;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.LikeRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import SANTA.backend.core.posts.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LikeServiceTest {
    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 게시글에_좋아요_추가_및_취소_테스트() {
        // given - 게시글 저장
        PostEntity post = PostEntity.builder()
                .author("작성자")
                .title("테스트 게시글")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        postRepository.save(post);

        Long userId = 100L;  // 테스트용 유저 아이디

        // when - 좋아요 누르기 (추가)
        Long likeCountAfterAdd = likeService.postLike(userId, post.getPostId());

        // then - 좋아요 1개 됐는지 검증
        assertThat(likeCountAfterAdd).isEqualTo(1);

        // when - 다시 좋아요 누르기 (취소)
        Long likeCountAfterRemove = likeService.postLike(userId, post.getPostId());

        // then - 좋아요 0개 됐는지 검증
        assertThat(likeCountAfterRemove).isEqualTo(0);
    }

    @Test
    void 댓글에_좋아요_추가_및_취소_테스트() {
        // given - 게시글 저장
        PostEntity post = PostEntity.builder()
                .author("작성자")
                .title("테스트 게시글")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        postRepository.save(post);

        // 댓글 저장
        CommentEntity comment = CommentEntity.builder()
                .commentWriter("댓글 작성자")
                .commentBody("댓글 내용")
                .postEntity(post)
                .build();
        commentRepository.save(comment);

        Long userId = 200L;

        // when - 댓글 좋아요 누르기 (추가)
        Long likeCountAfterAdd = likeService.commentLike(userId, comment.getCommentId());

        // then - 좋아요 1개 됐는지 검증
        assertThat(likeCountAfterAdd).isEqualTo(1);

        // when - 다시 댓글 좋아요 누르기 (취소)
        Long likeCountAfterRemove = likeService.commentLike(userId, comment.getCommentId());

        // then - 좋아요 0개 됐는지 검증
        assertThat(likeCountAfterRemove).isEqualTo(0);
    }

    @Test
    void 좋아요_개수_조회_토글방식_테스트() {
        // given - 게시글 저장
        PostEntity post = PostEntity.builder()
                .author("작성자")
                .title("테스트 게시글")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        postRepository.save(post);

        // 댓글 저장
        CommentEntity comment = CommentEntity.builder()
                .commentWriter("댓글 작성자")
                .commentBody("댓글 내용")
                .postEntity(post)
                .build();
        commentRepository.save(comment);

        Long userId1 = 1L;
        Long userId2 = 2L;

        // 게시글 좋아요 토글: 두 유저가 각각 좋아요 추가
        likeService.postLike(userId1, post.getPostId()); // 좋아요 1개
        likeService.postLike(userId2, post.getPostId()); // 좋아요 2개

        // 댓글 좋아요 토글: 두 유저가 각각 좋아요 추가
        likeService.commentLike(userId1, comment.getCommentId()); // 좋아요 1개
        likeService.commentLike(userId2, comment.getCommentId()); // 좋아요 2개

        // when - 좋아요 개수 조회
        long postLikesCount = likeService.countLikes(post.getPostId());
        long commentLikesCount = likeService.countcommentLikes(comment.getCommentId());

        // then
        assertThat(postLikesCount).isEqualTo(2);
        assertThat(commentLikesCount).isEqualTo(2);

        // 좋아요 토글로 하나씩 취소
        likeService.postLike(userId1, post.getPostId()); // 좋아요 1개로 감소
        likeService.commentLike(userId2, comment.getCommentId()); // 좋아요 1개로 감소

        // 다시 개수 조회
        long postLikesCountAfterToggle = likeService.countLikes(post.getPostId());
        long commentLikesCountAfterToggle = likeService.countcommentLikes(comment.getCommentId());

        assertThat(postLikesCountAfterToggle).isEqualTo(1);
        assertThat(commentLikesCountAfterToggle).isEqualTo(1);
    }
}
