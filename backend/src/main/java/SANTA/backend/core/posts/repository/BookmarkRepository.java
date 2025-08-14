package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.BookMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookMarkEntity, Long> {

    Optional<BookMarkEntity> findByUserIdAndPost_PostId(Long userId, Long postId);
    Long countByPost_PostId(Long postId);

    List<BookMarkEntity> findAllByUserId(Long userId);
}
