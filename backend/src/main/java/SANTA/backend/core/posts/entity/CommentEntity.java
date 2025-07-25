package SANTA.backend.core.posts.entity;

import SANTA.backend.core.posts.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_table")
public class CommentEntity extends PostBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentWriter; //이건 댓글 작성자의 userId

    @Lob
    private String commentBody;

    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    //Post : Comment - 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_comment_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<CommentEntity> children=new ArrayList<>();

    //좋아요
    @OneToMany(mappedBy="comment")
    @Builder.Default
    private List<LikeEntity> likes =new ArrayList<>();

    @Builder
    public CommentEntity(String commentWriter,String commentBody, PostEntity postEntity){
        this.commentWriter=commentWriter;
        this.commentBody=commentBody;
        this.postEntity=postEntity;
    }

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, PostEntity postEntity, CommentEntity parent){
        return CommentEntity.builder()
                .commentWriter(commentDTO.getCommentWriter())
                .commentBody(commentDTO.getCommentBody())
                .postEntity(postEntity)
                .commentType(commentDTO.getCommentType())
                .parent(parent)
                .build();
    }
}
