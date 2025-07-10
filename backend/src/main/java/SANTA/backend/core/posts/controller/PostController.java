package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.service.CommentService;
import SANTA.backend.core.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/community/board")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PostDTO postDTO){
        System.out.println("postDTO = "+ postDTO);
        postService.save(postDTO);
        return "redirect:/api/community/board/home";
    }

    @GetMapping("/")
    public String findAll(Model model){//data가져올 땐 model객체 사용
        //DB에서 전체 게시글 게이터를 가져와 list.html에 보여준다.
        List<PostDTO> postDTOList= postService.findAll();
        model.addAttribute("postList",postDTOList);
        return "list";
    }

    //게시글 조회
    @GetMapping("/{postId}")//현재 상태는 새로고침 하면 조회수가 올라가는 문제점이 있음
    public String findById(@PathVariable Long postId,Model model, @PageableDefault(page=1)Pageable pageable){
        //해당 게시글의 조회수를 하나 올리고(1) 게시글 데이터 가져와 detail.html에 출력(2)
        postService.updateHits(postId); //(1)
        PostDTO postDTO= postService.findBypostId(postId); //(2)
        //댓글 목록 가져옴
        List<CommentDTO> commentDTOList=commentService.findAll(postId);
        model.addAttribute("commentList",commentDTOList);

        model.addAttribute("post",postDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    //게시글 수정
    //상세화면에서 수정 버튼 클릭
    //서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
    //제목, 내용 수정 입력 받아서 서버로 요청
    //수정 처리
    @GetMapping("/update/{postId}")
    public String updateForm(@PathVariable Long postId,Model model){
        PostDTO postDTO=postService.findBypostId(postId);
        model.addAttribute("postUpdate",postDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute PostDTO postDTO, Model model){
        PostDTO post= postService.update(postDTO);
        model.addAttribute("post",post);
        return "detail";
    }

    @GetMapping("/delete/{postId}")
    public String delete(@PathVariable Long postId){
        postService.delete(postId);
        return "redirect:/api/community/board/";
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
