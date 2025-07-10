package SANTA.backend.core.posts.controller;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/community/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO){
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null){
            //작성 성공시 댓글 목록을 가져와서 리턴함.
            //댓글 목록: 해당 게시긍의 댓글 전체. 기준은 ID.
            List<CommentDTO> commentDTOList= commentService.findAll(commentDTO.getPostId());
            return new ResponseEntity(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
