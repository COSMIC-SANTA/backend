package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.dto.CommentDTO;
import SANTA.backend.core.posts.entity.CommentEntity;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.CommentRepository;
import SANTA.backend.core.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            PostEntity postEntity= optionalPostEntity.get();
            CommentEntity commentEntiry = CommentEntity.toSaveEntity(commentDTO,postEntity);
            return commentRepository.save(commentEntiry).getCommentId();
        }else{
            return null;
        }
    }

    public List<CommentDTO> findAll(Long postId) {
        // select * from comment_table Where board_id=? order by id desc;
        PostEntity postEntity= postRepository.findById(postId).get();
        List<CommentEntity> commentEntiryList=commentRepository.findAllByPostEntityOrderByCommentIdAsc(postEntity);
        //EntityList -> DTOList
        List<CommentDTO> commentDTOList=new ArrayList<>();
        for(CommentEntity commentEntity: commentEntiryList){
            CommentDTO commentDTO=CommentDTO.toCommentDTO(commentEntity, postId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
