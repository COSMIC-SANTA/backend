package SANTA.backend.core.posts.dto;

import SANTA.backend.core.posts.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookmarkPostDTO {
    private Long postId;
    private String title;
    private String author;
    private int postHits;

    public static BookmarkPostDTO from(PostEntity post) {
        return BookmarkPostDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .postHits(post.getPostHits())
                .build();
    }
}
