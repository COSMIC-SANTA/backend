package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.service.LikeService;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
@Slf4j
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    // 좋아요 추가 or 취소 (토글 방식)
    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable("postId") Long postId) {
        log.info("@@ 좋아요 추가 취소 실행");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName()) ;

        User user = userService.findById(userId);

        Long likeCount = likeService.postLike(userId, postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "좋아요 처리 완료");
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    // 좋아요 개수 확인
    @GetMapping("/{postId}/like/count")
    public ResponseEntity<Map<String, Object>> countLikes(@PathVariable("postId") Long postId) {
        long count = likeService.countLikes(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", count);
        return ResponseEntity.ok(response);
    }
}

