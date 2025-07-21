package SANTA.backend.core.posts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "post_id", nullable = true)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "comment_id", nullable = true)
    private CommentEntity comment;
}
