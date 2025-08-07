package SANTA.backend.post.infra;

import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.PostFileEntity;
import SANTA.backend.core.posts.repository.PostFileRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class PostFileRepositoryTest {
    @Autowired
    PostFileRepository postFileRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @Transactional
    void 게시글에_연관된_파일을_조회한다() {
        // given
        PostEntity post = PostEntity.builder()
                .userId(1L)
                .title("테스트 제목")
                .author("작성자")
                .body("본문입니다")
                .postHits(0)
                .fileAttached(1)
                .build();

        PostEntity savedPost = postRepository.save(post);

        PostFileEntity file1 = PostFileEntity.builder()
                .postEntity(savedPost)
                .originalFileName("original1.jpg")
                .storedFileName("stored1.jpg")
                .build();

        PostFileEntity file2 = PostFileEntity.builder()
                .postEntity(savedPost)
                .originalFileName("original2.jpg")
                .storedFileName("stored2.jpg")
                .build();

        postFileRepository.save(file1);
        postFileRepository.save(file2);

        // when
        List<PostFileEntity> files = postFileRepository.findByPostEntity(savedPost);

        // then
        assertThat(files).hasSize(2);
        assertThat(files).extracting(PostFileEntity::getOriginalFileName)
                .containsExactlyInAnyOrder("original1.jpg", "original2.jpg");
    }
}
