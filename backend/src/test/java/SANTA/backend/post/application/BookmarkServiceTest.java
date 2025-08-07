package SANTA.backend.post.application;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.PostFileEntity;
import SANTA.backend.core.posts.repository.*;
import SANTA.backend.core.posts.service.BookmarkService;
import SANTA.backend.core.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookmarkServiceTest {
    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private PostFileRepository postFileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository; // 좋아요 엔티티도 지워야 함

    @BeforeEach
    void cleanUp() {
        // 1. 좋아요 삭제
        likeRepository.deleteAllInBatch();

        // 2. 대댓글 → 부모 댓글 삭제 (명확히 정렬해서 순서 보장)
        List<CommentEntity> allComments = commentRepository.findAll();

        // 대댓글 먼저
        allComments.stream()
                .filter(c -> c.getParent() != null)
                .forEach(commentRepository::delete);

        // 부모 댓글
        allComments.stream()
                .filter(c -> c.getParent() == null)
                .forEach(commentRepository::delete);

        // 3. 북마크 삭제
        bookmarkRepository.deleteAllInBatch();

        // 4. 게시글 파일 삭제
        postFileRepository.deleteAllInBatch();

        // 5. 게시글 삭제
        postRepository.deleteAllInBatch();
    }
    @Test
    void 북마크_토글_및_조회_테스트() {
        // given
        User user = User.builder().userId(1L).build(); // 테스트용 User 객체
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("북마크 테스트")
                .author("작성자")
                .body("내용")
                .postHits(0)
                .fileAttached(0)
                .build();

        postRepository.save(post);

        // when - 북마크 추가
        boolean isBookmarked = bookmarkService.toggleBookmark(user, post.getPostId());

        // then - 저장됐는지 확인
        assertThat(isBookmarked).isTrue();
        List<PostEntity> bookmarkedPosts = bookmarkService.getBookmarkedPosts(user.getUserId());
        assertThat(bookmarkedPosts).hasSize(1);
        assertThat(bookmarkedPosts.get(0).getPostId()).isEqualTo(post.getPostId());

        // when - 북마크 제거
        boolean isUnbookmarked = bookmarkService.toggleBookmark(user, post.getPostId());

        // then - 삭제됐는지 확인
        assertThat(isUnbookmarked).isFalse();
        List<PostEntity> afterUnbookmark = bookmarkService.getBookmarkedPosts(user.getUserId());
        assertThat(afterUnbookmark).isEmpty();
    }
}
