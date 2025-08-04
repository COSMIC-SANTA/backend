package SANTA.backend.core.posts.controller;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.posts.dto.BookmarkPostDTO;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.service.BookmarkService;
import SANTA.backend.core.user.domain.User;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
@Slf4j
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{postId}/bookmark")
    public ResponseEntity<ResponseHandler<Map<String, Object>>> toggleBookmark(@PathVariable("postId") Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        // 북마크 토글 처리 후 결과 반환
        boolean isBookmarked = bookmarkService.toggleBookmark(user, postId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "게시글 북마크 처리 완료");
        response.put("isBookmarked", isBookmarked);  // true면 추가됨, false면 해제됨

        return ResponseEntity.ok(ResponseHandler.success(response));
    }

    // 북마크한 게시글들 보기
    @GetMapping("/bookmark/status")
    public ResponseEntity<ResponseHandler<List<BookmarkPostDTO>>> getBookmarkedPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getUserId();
        List<PostEntity> posts = bookmarkService.getBookmarkedPosts(userId);
        List<BookmarkPostDTO> result = posts.stream()
                .map(BookmarkPostDTO::from)
                .toList();

        return ResponseEntity.ok(ResponseHandler.success(result));
    }
}
