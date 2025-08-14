package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.entity.BookMarkEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.BookmarkRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import SANTA.backend.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookMarkRepository;
    private final PostRepository postRepository;

    public boolean toggleBookmark(User userId, Long postId) {
        Optional<BookMarkEntity> existing = bookMarkRepository.findByUserIdAndPost_PostId(userId.getUserId(), postId);

        if (existing.isPresent()) {
            bookMarkRepository.delete(existing.get());
            return false;
        } else {
            PostEntity post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
            BookMarkEntity newBookmark = BookMarkEntity.builder()
                    .userId(userId.getUserId())
                    .post(post)
                    .build();            bookMarkRepository.save(newBookmark);
            return true;
        }
    }

    public List<PostEntity> getBookmarkedPosts(Long userId) {
        return bookMarkRepository.findAllByUserId(userId).stream()
                .map(BookMarkEntity::getPost)
                .collect(Collectors.toList());
    }
}
