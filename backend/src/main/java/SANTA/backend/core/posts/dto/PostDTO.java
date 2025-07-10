package SANTA.backend.core.posts.dto;

import SANTA.backend.core.posts.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
//DTO(Data Transfer Object), VO, Bean / Entity
public class PostDTO {
    private Long postId;
    private String author;
    private Long postPass; //password??
    private String title;
    private String body;
    private int postHits; //조회수
    private LocalDateTime postCreatedTime;
    private LocalDateTime postUpdatedTime;

    public PostDTO(Long postId, String author, String title, int postHits, LocalDateTime postCreatedTime) {
        this.postId = postId;
        this.author = author;
        this.title = title;
        this.postHits = postHits;
        this.postCreatedTime = postCreatedTime;
    }

    public static PostDTO toPostDTO(PostEntity postEntity){
        PostDTO postDTO=new PostDTO();
        postDTO.setPostId(postEntity.getPostId());
        postDTO.setAuthor(postEntity.getAuthor());
        postDTO.setPostPass(postEntity.getUserId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setBody(postEntity.getBody());
        postDTO.setPostHits(postEntity.getPostHits());
        postDTO.setPostCreatedTime(postEntity.getCreatedTime());
        postDTO.setPostUpdatedTime(postEntity.getUpdatedTime());
        return postDTO;
    }

}
