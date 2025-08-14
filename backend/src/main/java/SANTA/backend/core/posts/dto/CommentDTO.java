package SANTA.backend.core.posts.dto;


import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.CommentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long commentId;
    private String commentWriter;
    private String commentBody;
    private Long postId;
    private LocalDateTime commentCreatedTime;

    private CommentType commentType;
    private Long parentCommentId;

  //  private List<CommentEntity> replies=new ArrayList<>();

    private List<CommentDTO> replies=new ArrayList<>();

    public static CommentDTO toCommentDTO(CommentEntity commentEntiry, Long postId) {
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setCommentId(commentEntiry.getCommentId());
        commentDTO.setCommentWriter(commentEntiry.getCommentWriter());
        commentDTO.setCommentBody(commentEntiry.getCommentBody());
        commentDTO.setCommentCreatedTime(commentEntiry.getCreatedTime());
        commentDTO.setCommentType(commentEntiry.getCommentType());
        commentDTO.setParentCommentId(commentEntiry.getParent()!=null?commentEntiry.getParent().getCommentId():null);
        commentDTO.setPostId(postId);
        return commentDTO;
    }
}
