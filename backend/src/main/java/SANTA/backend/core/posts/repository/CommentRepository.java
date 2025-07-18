package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table Where board_id=? order by id desc;
    List<CommentEntity> findAllByPostEntityOrderByCommentIdAsc(PostEntity postEntity);
}
