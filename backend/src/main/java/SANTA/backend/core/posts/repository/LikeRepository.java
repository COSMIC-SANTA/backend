package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserIdAndPost_PostId(Long userId, Long postId);
    Long countByPost_PostId(Long postId);
}

