package SANTA.backend.core.posts.entity;

import jakarta.persistence.*;

@Entity
public class LikeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity post;
}
