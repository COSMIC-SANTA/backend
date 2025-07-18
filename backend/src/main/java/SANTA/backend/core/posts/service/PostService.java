package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.repository.PostRepository;
import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserRepository userRepository;

    public PostDTO save(PostDTO postDTO, User user) {
        PostEntity postEntity = PostEntity.tosaveEntity(postDTO, user.getUserId(), user.getNickname());
        PostEntity savedEntity = postRepository.save(postEntity); // save 후 DB에 반영된 엔티티 받음
        return PostDTO.toPostDTO(savedEntity); // 다시 DTO로 변환해서 반환
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
        PostEntity existing = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // Builder로 새 객체를 생성하되 기존 객체의 정보 일부를 유지
        PostEntity updated = PostEntity.builder()
                .postId(existing.getPostId())         // 유지해야 하는 ID
                .userId(existing.getUserId())         // 원 작성자 유지
                .author(existing.getAuthor())         // 작성자 유지
                .postHits(existing.getPostHits())     // 기존 조회수 유지
                .title(postDTO.getTitle())            // 수정된 제목
                .body(postDTO.getBody())              // 수정된 내용
                .build();

        postRepository.save(updated);
        return PostDTO.toPostDTO(updated);
    }


    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<PostDTO> paging(Pageable pageable) {
        int page= pageable.getPageNumber()-1;
        int pageLimit=3; //한 페이지에 보여지는 글 갯수
        //한페이지당 3개씩 글을 보여주고 정렬 기준은 ID 기준으로 내림차순 정렬
        Page<PostEntity> postEntities= postRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"postId")));


        //목록 : id, author, title, hits, createdTime
        Page<PostDTO> postDTOS= postEntities.map(post -> new PostDTO(post.getPostId(), post.getAuthor(), post.getTitle(), post.getPostHits(), post.getCreatedTime()));
        return postDTOS;
    }
}
