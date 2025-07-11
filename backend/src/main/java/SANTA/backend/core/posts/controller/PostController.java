package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.service.CommentService;
import SANTA.backend.core.posts.service.PostService;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.Level;
import SANTA.backend.core.user.domain.Role;
import SANTA.backend.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/save")
    public String saveForm(){

        return "save";
    }

    /*@PostMapping("/save")
@ResponseBody
public Map<String, List<Map<String, Object>>> save(@RequestBody PostDTO postDTO) {
    // 테스트용 mock 사용자
    User mockUser = User.builder()
            .userId(1L)
            .username("testUser")
            .password("1234")
            .nickname("테스트유저")
            .age(20)
            .role(Role.ROLE_USER)
            .level(Level.BEGINER)
            .build();

    // 게시글 저장 및 반환
    PostDTO savedPost = postService.save(postDTO, mockUser);

    // 응답 JSON 생성
    Map<String, Object> postData = new HashMap<>();
    postData.put("post_id", savedPost.getPostId());
    postData.put("post_title", savedPost.getTitle());
    postData.put("post_body", savedPost.getBody());
    postData.put("post_author", savedPost.getAuthor());

    Map<String, List<Map<String, Object>>> response = new HashMap<>();
    response.put("post", List.of(postData));
    return response;
}
*/

    @PostMapping("/save")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> save(@RequestBody PostDTO postDTO) {
        // 현재 로그인 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

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
    public PostDTO findById(@PathVariable Long postId) {
        postService.updateHits(postId); // 조회수 증가
        return postService.findBypostId(postId);
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
    public PostDTO update(@RequestBody PostDTO postDTO) {
        return postService.update(postDTO);
    }

    @DeleteMapping("/delete/{postId}")
    public Map<String, String> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return Map.of("message", "삭제 성공");
    }

    //페이징 처리 /post/paging?page=1이런 식으로 감
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        //pageable.getPageNumber();
        Page<PostDTO> postList = postService.paging(pageable);
        int blockLimit = 3; //보여질 페이지 번호 갯수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < postList.getTotalPages()) ? startPage + blockLimit - 1 : postList.getTotalPages();
        model.addAttribute("postList",postList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
