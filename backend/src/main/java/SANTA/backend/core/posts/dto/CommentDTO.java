package SANTA.backend.core.posts.dto;


import SANTA.backend.core.posts.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long commentId;
    private String commentWriter;
    private String commentBody;
    private Long postId;
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntiry, Long postId) {
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setCommentId(commentEntiry.getCommentId());
        commentDTO.setCommentWriter(commentEntiry.getCommentWriter());
        commentDTO.setCommentBody(commentEntiry.getCommentBody());
        commentDTO.setCommentCreatedTime(commentEntiry.getCreatedTime());
        commentDTO.setPostId(postId);
        return commentDTO;
    }
}
