package SANTA.backend.core.posts.entity;

import SANTA.backend.core.posts.dto.PostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="post_table")
//데이터베이스 테이블 역할을 하는 클래스
public class PostEntity extends PostBaseEntity {
    @Id //PK 컬럼 지정. 필수임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; //게시물 id

    private Long userId; //password부분에 대입한다

    private String title;
    private String author; //작성자 이름

    @Lob
    private String body; //본문

    private int postHits;

    @OneToMany(mappedBy="post")
    @Builder.Default
    private List<LikeEntity> likes =new ArrayList<>();

    //이거 왜 DB에 안 나오지...?
    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommentEntity> commentEntityList=new ArrayList<>();

    private int fileAttached;

    @Builder.Default
    @OneToMany(mappedBy = "postEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<PostFileEntity> postFileEntityList=new ArrayList<>();

    public static PostEntity tosaveEntity(PostDTO postDTO, Long userId, String nickname){
        return PostEntity.builder()
                .userId(userId)
                .author(nickname)
                .title(postDTO.getTitle())
                .body(postDTO.getBody())
                .postHits(0)
                .fileAttached(0)
                .build();
    }

    public static PostEntity toUpdateEntity(PostDTO postDTO, PostEntity existing) {
        boolean hasNewFiles=postDTO.getPostFile()!=null&&!postDTO.getPostFile().isEmpty();
        return PostEntity.builder()
                .postId(existing.getPostId())                   // 기존 ID 유지
                .userId(existing.getUserId())                   // 기존 작성자 유지
                .author(existing.getAuthor())                   // 기존 작성자명 유지
                .postHits(existing.getPostHits())               // 기존 조회수 유지
                .commentEntityList(existing.getCommentEntityList()) // 기존 댓글 유지
                .title(postDTO.getTitle())                       // 수정할 제목
                .body(postDTO.getBody())                         // 수정할 본문
                .fileAttached(hasNewFiles ? 1: existing.getFileAttached())
                .postFileEntityList(existing.getPostFileEntityList())
                .build();
    }


    public static PostEntity toSaveFileEntity(PostDTO postDTO,Long userId, String nickname) {
        return PostEntity.builder()
                .userId(userId)
                .author(nickname)
                .title(postDTO.getTitle())
                .body(postDTO.getBody())
                .postHits(0)
                .fileAttached(1)
                .build();
    }
}
