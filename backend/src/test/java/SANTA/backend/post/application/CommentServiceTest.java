package SANTA.backend.post.application;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.entity.CommentType;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import SANTA.backend.core.posts.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글을_저장하고_조회할_수_있다() {
        // given - 게시글 저장: builder와 생성 메서드로 생성
        PostEntity post = PostEntity.builder()
                .author("작성자")
                .title("테스트 게시글")
                .body("본문")
                .postHits(0)
                .fileAttached(0)
                .build();
        postRepository.save(post);

        // 댓글 DTO 준비
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());  // getPostId()로 PK 가져오기
        commentDTO.setCommentWriter("댓글작성자");
        commentDTO.setCommentBody("댓글 내용");
        commentDTO.setCommentType(CommentType.COMMENT);//대댓글 테스트 - REPLY

        // when - 댓글 저장
        Long commentId = commentService.save(commentDTO);

        // then - 저장된 댓글 조회 및 검증
        List<CommentDTO> commentList = commentService.findAll(post.getPostId());

        assertThat(commentList).isNotEmpty();
        assertThat(commentList.get(0).getCommentId()).isEqualTo(commentId);
        assertThat(commentList.get(0).getCommentBody()).isEqualTo("댓글 내용");
    }
}
