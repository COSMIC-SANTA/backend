package SANTA.backend.post.infra;

import SANTA.backend.core.posts.entity.BookMarkEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.BookmarkRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class BookmarkRepositoryTest {
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void 유저가_게시글을_북마크할_수_있다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("북마크 게시글")
                .author("작성자")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();

        PostEntity savedPost = postRepository.save(post);

        BookMarkEntity bookmark = BookMarkEntity.builder()
                .userId(100L)
                .post(savedPost)
                .build();

        bookmarkRepository.save(bookmark);

        // when
        Optional<BookMarkEntity> foundBookmark = bookmarkRepository.findByUserIdAndPost_PostId(100L, savedPost.getPostId());

        // then
        assertThat(foundBookmark).isPresent();
        assertThat(foundBookmark.get().getPost().getPostId()).isEqualTo(savedPost.getPostId());
    }

    @Test
    void 게시글의_북마크_수를_조회한다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("북마크 카운트 테스트")
                .author("작성자")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();

        PostEntity savedPost = postRepository.save(post);

        bookmarkRepository.save(BookMarkEntity.builder().userId(1L).post(savedPost).build());
        bookmarkRepository.save(BookMarkEntity.builder().userId(2L).post(savedPost).build());

        // when
        Long count = bookmarkRepository.countByPost_PostId(savedPost.getPostId());

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void 특정_유저의_모든_북마크를_조회한다() {
        // given
        PostEntity post1 = postRepository.save(PostEntity.builder().userId(1L).title("post1").author("author").body("body").postHits(0).fileAttached(0).build());
        PostEntity post2 = postRepository.save(PostEntity.builder().userId(1L).title("post2").author("author").body("body").postHits(0).fileAttached(0).build());

        bookmarkRepository.save(BookMarkEntity.builder().userId(200L).post(post1).build());
        bookmarkRepository.save(BookMarkEntity.builder().userId(200L).post(post2).build());

        // when
        List<BookMarkEntity> bookmarks = bookmarkRepository.findAllByUserId(200L);

        // then
        assertThat(bookmarks).hasSize(2);
        assertThat(bookmarks).extracting(b -> b.getPost().getTitle())
                .containsExactlyInAnyOrder("post1", "post2");
    }
}
