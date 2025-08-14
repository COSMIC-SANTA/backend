package SANTA.backend.core.posts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookmark_table")
public class BookMarkEntity {
    @Id @GeneratedValue
    private Long bookmarkId;

    private int bookmarkCount;

    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

}
