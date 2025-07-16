package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
public class LikeController {
    private final LikeService likeService;

    // 좋아요 추가 or 취소 (토글 방식)
    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Object>> toggleLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {

        Long likeCount = likeService.postLike(userId, postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "좋아요 처리 완료");
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    // 좋아요 개수 확인
    @GetMapping("/{postId}/like/count")
    public ResponseEntity<Map<String, Object>> countLikes(@PathVariable Long postId) {
        long count = likeService.countLikes(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", count);
        return ResponseEntity.ok(response);
    }
}

