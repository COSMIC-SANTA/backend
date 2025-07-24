package SANTA.backend.core.posts.controller;

import SANTA.backend.core.auth.service.CustomUserDetails;
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

    // 좋아요 추가 or 취소 (토글 방식)
    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable("postId") Long postId) {
        log.info("@@ 좋아요 추가 취소 실행");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        Long likeCount = likeService.postLike(user.getUserId(), postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "게시글 좋아요 처리 완료");
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    //댓글 좋아요 실행
    @PostMapping("/{commentId}/commentlike")
    public ResponseEntity<Map<String, Object>> commentLike(@PathVariable("commentId") Long commentId) {
        log.info("@@ 좋아요 추가 취소 실행");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        Long likeCount = likeService.commentLike(user.getUserId(), commentId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "댓글 좋아요 처리 완료");
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    // 게시글 좋아요 개수 확인
    @GetMapping("/{postId}/like/count")
    public ResponseEntity<Map<String, Object>> countLikes(@PathVariable("postId") Long postId) {
        long count = likeService.countLikes(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("post likeCount", count);
        return ResponseEntity.ok(response);
    }

    // 댓글 좋아요 개수 확인
    @GetMapping("/{commentId}/commentlike/count")
    public ResponseEntity<Map<String, Object>> commentcountLikes(@PathVariable("commentId") Long commentId) {
        long count = likeService.countcommentLikes(commentId);

        Map<String, Object> response = new HashMap<>();
        response.put("comment likeCount", count);
        return ResponseEntity.ok(response);
    }
}

