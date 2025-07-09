package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//DTO를 Entity로 변환 (in Entity class)
//Entity를 DTO로 변환 (in DTO class)

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void save(PostDTO postDTO) {
        PostEntity postEntity=PostEntity.tosaveEntity(postDTO);
        postRepository.save(postEntity); //save는 JPArepository에서 상속받은 메서드
    }

    public List<PostDTO> findAll() {
       List<PostEntity> postEntityList = postRepository.findAll();
       //Entity로 온 객체들을 다시 DTO로 바꿔 받아야 함
        List<PostDTO> postDTOList=new ArrayList<>();
        for(PostEntity postEntity:postEntityList){
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    @Transactional
    public void updateHits(Long postId) {
        postRepository.updateHits(postId);
    }

    public PostDTO findBypostId(Long postId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);
        if(optionalPostEntity.isPresent()){
            PostEntity postEntity=optionalPostEntity.get();
            PostDTO postDTO=PostDTO.toPostDTO(postEntity);
            return postDTO;
        }else {
            return null;
        }
    }

    public PostDTO update(PostDTO postDTO) {
        PostEntity postEntity=PostEntity.toUpdateEntity(postDTO);
        postRepository.save(postEntity);
        return findBypostId(postDTO.getPostId());
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
