package SANTA.backend.core.posts.dto;

import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.PostFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
//DTO(Data Transfer Object), VO, Bean / Entity
public class PostDTO {
    private Long postId;
    private String author;
    private Long postPass;
    private String title;
    private String body;
    private int postHits; //조회수
    private int likeCount; //좋아요 수
    private LocalDateTime postCreatedTime;
    private LocalDateTime postUpdatedTime;

    //파일추가
    private List<MultipartFile> postFile; //파일을 담는 용도
    private List<String> originalFilename; //원본 파일 이름
    private List<String> storedFilename; //서버 저장용 파일 이름
    private int fileAttached; //파일 첨부 여부(첨부-1, 미첨부-0)

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
        postDTO.setLikeCount(postEntity.getLikes().size());

        if(postEntity.getFileAttached()==0){
            postDTO.setFileAttached(postEntity.getFileAttached());
        }else{
            List<String> originalFileNameList=new ArrayList<>();
            List<String> storedFileNameList=new ArrayList<>();
            postDTO.setFileAttached(postEntity.getFileAttached());
            for(PostFileEntity postFileEntity:postEntity.getPostFileEntityList()) {
                originalFileNameList.add(postFileEntity.getOriginalFileName());
                storedFileNameList.add(postFileEntity.getStoredFileName());
            }
            postDTO.setOriginalFilename(originalFileNameList);
            postDTO.setStoredFilename(storedFileNameList);
        }

        return postDTO;
    }

}
