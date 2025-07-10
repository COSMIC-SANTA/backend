package SANTA.backend.core.posts.entity;

import SANTA.backend.core.posts.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends PostBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentWriter; //이건 댓글 작성자의 userId

    @Lob
    private String commentBody;

    //Post : Comment - 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, PostEntity postEntity) {
        CommentEntity commentEntiry = new CommentEntity();
        commentEntiry.setCommentWriter(commentDTO.getCommentWriter());
        commentEntiry.setCommentBody(commentDTO.getCommentBody());

        commentEntiry.setPostEntity(postEntity);
        return  commentEntiry;
    }
}
