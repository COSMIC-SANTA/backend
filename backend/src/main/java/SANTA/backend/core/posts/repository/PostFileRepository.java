package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFileEntity,Long> {
}
