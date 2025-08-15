package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFileEntity,Long> {
    List<PostFileEntity> findByPostEntity(PostEntity post);
}
