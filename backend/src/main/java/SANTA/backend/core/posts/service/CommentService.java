package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Long save(CommentDTO commentDTO) {
        //부모 엔티티 조회
        Optional<PostEntity> optionalPostEntity =postRepository.findById(commentDTO.getPostId());
        if(optionalPostEntity.isPresent()){
            PostEntity post= optionalPostEntity.get();
            CommentEntity parent=null;
            if(commentDTO.getParentCommentId()!=null){
                parent=commentRepository.findById(commentDTO.getParentCommentId())
                        .orElseThrow(()->new IllegalArgumentException("부모 댓글이 없습니다"));
            }
            CommentEntity comment=CommentEntity.toSaveEntity(commentDTO,post,parent);
            return commentRepository.save(comment).getCommentId();
        }else{
            return null;
        }
    }

    public List<CommentDTO> findAll(Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow();
        List<CommentEntity> parentComments = commentRepository.findByPostEntityAndParentIsNullOrderByCommentIdAsc(post);

        List<CommentDTO> result = new ArrayList<>();
        for (CommentEntity parent : parentComments) {
            CommentDTO parentDTO = CommentDTO.toCommentDTO(parent, postId);

            // 대댓글 조회
            List<CommentEntity> replies = commentRepository.findByParentOrderByCommentIdAsc(parent);
            List<CommentDTO> replyDTOs = replies.stream()
                    .map(reply -> CommentDTO.toCommentDTO(reply, postId))
                    .toList();

            parentDTO.setReplies(replyDTOs);
            result.add(parentDTO);
        }

        return result;
    }
}
