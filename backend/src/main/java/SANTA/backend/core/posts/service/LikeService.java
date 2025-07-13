package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.entity.LikeEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.LikeRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long postLike(Long userId, Long postId){
        LikeEntity like = likeRepository.findByUserIdAndPost_PostId(userId, postId).orElse(null);
        if (like == null) {
            PostEntity post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
            LikeEntity newLike = LikeEntity.builder()
                    .userId(userId)
                    .post(post)
                    .build();
            likeRepository.save(newLike);
        } else {
            likeRepository.delete(like);
        }
        return likeRepository.countByPost_PostId(postId);
    }


    public void addLike(Long postId, Long userId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 중복 좋아요 체크 등 필요하면 추가 가능
        LikeEntity like = LikeEntity.builder()
                .userId(userId)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    public long countLikes(Long postId) {
        return likeRepository.countByPost_PostId(postId);
    }
}
