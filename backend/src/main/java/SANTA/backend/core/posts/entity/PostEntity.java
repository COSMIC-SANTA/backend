package SANTA.backend.core.posts.entity;

import SANTA.backend.core.posts.dto.PostDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private List<LikeEntity> likes =new ArrayList<>();

    public static PostEntity tosaveEntity(PostDTO postDTO){
        PostEntity postEntity= new PostEntity();
        postEntity.setAuthor(postDTO.getAuthor());
        postEntity.setUserId(postDTO.getPostPass());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setBody(postDTO.getBody());
        postEntity.setPostHits(0);
        return postEntity;
    }

    public static PostEntity toUpdateEntity(PostDTO postDTO) {
        PostEntity postEntity= new PostEntity();
        postEntity.setPostId(postDTO.getPostId());
        postEntity.setAuthor(postDTO.getAuthor());
        postEntity.setUserId(postDTO.getPostPass());
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setBody(postDTO.getBody());
        postEntity.setPostHits(postDTO.getPostHits());
        return postEntity;
    }
}
