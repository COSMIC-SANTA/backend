package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table Where board_id=? order by id desc;
    List<CommentEntity> findAllByPostEntityOrderByCommentIdAsc(PostEntity postEntity);

    // 부모 댓글(댓글만) 조회
    List<CommentEntity> findByPostEntityAndParentIsNullOrderByCommentIdAsc(PostEntity postEntity);

    // 특정 댓글의 대댓글 조회
    List<CommentEntity> findByParentOrderByCommentIdAsc(CommentEntity parent);
}
