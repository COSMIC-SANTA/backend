package SANTA.backend.core.posts.entity;

import SANTA.backend.core.posts.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public CommentEntity(String commentWriter,String commentBody, PostEntity postEntity){
        this.commentWriter=commentWriter;
        this.commentBody=commentBody;
        this.postEntity=postEntity;
    }

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, PostEntity postEntity){
        return CommentEntity.builder()
                .commentWriter(commentDTO.getCommentWriter())
                .commentBody(commentDTO.getCommentBody())
                .postEntity(postEntity)
                .build();
    }
}
