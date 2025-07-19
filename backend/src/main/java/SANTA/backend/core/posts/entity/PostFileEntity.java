package SANTA.backend.core.posts.entity;

import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="post_file_table")
public class PostFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String originalFileName;
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    public static PostFileEntity toPostFileEntity(PostEntity postEntity,String originalFileName, String storedFileName){
        return PostFileEntity.builder()
                .postEntity(postEntity)
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .build();
    }
}
