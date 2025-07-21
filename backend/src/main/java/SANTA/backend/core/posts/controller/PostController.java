package SANTA.backend.core.posts.controller;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.service.CommentService;
import SANTA.backend.core.posts.service.LikeService;
import SANTA.backend.core.posts.service.PostService;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    private final LikeService likeService;

    @GetMapping("/save")
    public String saveForm(){

        return "save";
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> save(@ModelAttribute PostDTO postDTO) throws IOException {
        // 현재 로그인 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        // 게시글 저장
        PostDTO savedPost = postService.save(postDTO, user);

        // JSON 응답 형태 구성
        Map<String, Object> postData = new HashMap<>();
        postData.put("post_id", savedPost.getPostId());
        postData.put("post_title", savedPost.getTitle());
        postData.put("post_body", savedPost.getBody());
        postData.put("post_author", savedPost.getAuthor());

        Map<String, List<Map<String, Object>>> response = new HashMap<>();
        response.put("post", List.of(postData));
        return response;
    }

    @GetMapping("/")
    public List<PostDTO> findAll() {
        return postService.findAll();
    }

    //게시글 조회
    @GetMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPostWithComments(@PathVariable Long postId) {
        // 1. 조회수 증가
        postService.updateHits(postId);

        // 2. 게시글 데이터 조회
        PostDTO postDTO = postService.findBypostId(postId);

        // 3. 댓글 목록 조회
        List<CommentDTO> commentDTOList = commentService.findAll(postId);

        // 4. JSON 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("post", postDTO);
        response.put("comments", commentDTOList);

        return ResponseEntity.ok(response);
    }


    //게시글 수정
    //상세화면에서 수정 버튼 클릭
    //서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
    //제목, 내용 수정 입력 받아서 서버로 요청
    //수정 처리

    //이부분 수정 되는지 확인할 수 있도록 고치기 필요. 아직 안 고침
    @GetMapping("/update/{postId}")
    public String updateForm(@PathVariable Long postId,Model model){
        PostDTO postDTO=postService.findBypostId(postId);
        model.addAttribute("postUpdate",postDTO);
        return "update";
    }

    @PutMapping("/update")
    public PostDTO update(@ModelAttribute PostDTO postDTO) throws IOException {
        return postService.update(postDTO);
    }

    @DeleteMapping("/delete/{postId}")
    public Map<String, String> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return Map.of("message", "삭제 성공");
    }

    //페이징 처리 /post/paging?page=1이런 식으로 감
    //이거 postman에서 확인할 수 있도록 하기
    @GetMapping("/paging")
    @ResponseBody
    public Map<String, Object> paging(@PageableDefault(page=1) Pageable pageable){
        //pageable.getPageNumber();
        Page<PostDTO> postList = postService.paging(pageable);

        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < postList.getTotalPages()) ? startPage + blockLimit - 1 : postList.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("postList", postList.getContent()); // 실제 게시글 리스트
        response.put("currentPage", postList.getNumber() + 1); // 0부터 시작하므로 +1
        response.put("totalPages", postList.getTotalPages());
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return response;
    }

    @GetMapping("/bestposts")
    public ResponseEntity<List<PostDTO>> getPopularPosts() {
        List<PostDTO> posts = postService.findPopularPostsLast7Days();
        return ResponseEntity.ok(posts);
    }

}
