package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.service.CommentService;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    // 댓글 저장 (POST /api/community/comment/saveChattingRoom)
//    @PostMapping("/saveChattingRoom")
//    public ResponseEntity<?> saveChattingRoom(@RequestBody CommentDTO commentDTO) {
//        // 로그인 사용자 이름 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // username으로 사용자 정보 조회
//        User user = userService.findByUsername(username);
//
//        // 사용자 닉네임을 댓글 작성자(commentWriter)에 설정
//        commentDTO.setCommentWriter(user.getNickname());
//
//        Long saveResult = commentService.saveChattingRoom(commentDTO);
//        if (saveResult != null) {
//            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostId());
//            return ResponseEntity.ok(commentDTOList);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("해당 게시글이 존재하지 않습니다.");
//        }
//    }

    //테스트용
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CommentDTO commentDTO) {
        // 테스트용 nickname 직접 지정
        commentDTO.setCommentWriter("테스트유저");  // 로그인 없이 nickname 강제 주입

        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostId());
            return ResponseEntity.ok(commentDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 게시글이 존재하지 않습니다.");
        }
    }

    // 특정 게시글 댓글 조회 (GET /api/community/comment/{postId})
    @GetMapping("/{postId}")
    public ResponseEntity<?> findAll(@PathVariable Long postId) {
        try {
            List<CommentDTO> commentDTOList = commentService.findAll(postId);
            return ResponseEntity.ok(commentDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 게시글이 존재하지 않거나 댓글을 찾을 수 없습니다.");
        }
    }
}
