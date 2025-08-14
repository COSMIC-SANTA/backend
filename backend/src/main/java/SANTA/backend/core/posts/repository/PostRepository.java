package SANTA.backend.core.posts.repository;

import SANTA.backend.core.posts.entity.PostEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    //update post_table set post_hits=post_hits+1 where id=?
    @Modifying
    @Query(value = "update PostEntity p set p.postHits=p.postHits+1 where p.postId=:postId")
    void updateHits(@Param("postId")Long postId);

    @Query("SELECT p FROM PostEntity p WHERE p.createdTime >= :sevenDaysAgo ORDER BY SIZE(p.likes) DESC")
    List<PostEntity> findTopPostsLast7Days(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo, Pageable topTen);

}
