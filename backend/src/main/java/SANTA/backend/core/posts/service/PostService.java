package SANTA.backend.core.posts.service;

import SANTA.backend.core.posts.dto.PostDTO;
import SANTA.backend.core.posts.entity.PostEntity;
import SANTA.backend.core.posts.entity.PostFileEntity;
import SANTA.backend.core.posts.repository.PostFileRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//DTO를 Entity로 변환 (in Entity class)
//Entity를 DTO로 변환 (in DTO class)

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    public PostDTO save(PostDTO postDTO, User user) throws IOException {

        if(postDTO.getPostFile() == null ||postDTO.getPostFile().isEmpty()){
            //파일 첨부 없음
            PostEntity postEntity = PostEntity.tosaveEntity(postDTO, user.getUserId(), user.getNickname());
            PostEntity savedEntity = postRepository.save(postEntity); // save 후 DB에 반영된 엔티티 받음
            return PostDTO.toPostDTO(savedEntity); // 다시 DTO로 변환해서 반환
        }else{
            //파일첨부 있음
            PostEntity postEntity =PostEntity.toSaveFileEntity(postDTO,user.getUserId(), user.getNickname());
            Long savedId=postRepository.save(postEntity).getPostId();
            PostEntity post=postRepository.findById(savedId).get();
            for(MultipartFile postFile:postDTO.getPostFile()){
                String originalFilename=postFile.getOriginalFilename();
                String storedFileName= System.currentTimeMillis()+"_"+originalFilename;
                //안2. String storedFileName = UUID.randomUUID() + "_" + originalFilename;
                String savePath="C:/Users/User/Desktop/springtemp_img/"+storedFileName; // C:/springboot_img/파일이름.jpg이렇게 저장되게 함.->이거 바꾸든가 하자
                postFile.transferTo(new File(savePath));//savePath로 파일을 넘긴다.

                PostFileEntity postFileEntity=PostFileEntity.toPostFileEntity(post, originalFilename,storedFileName);
                postFileRepository.save(postFileEntity);
            }
            return PostDTO.toPostDTO(post);
        }

    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public PostDTO update(PostDTO postDTO) throws IOException {
        PostEntity existing = postRepository.findById(postDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        PostEntity updated = PostEntity.toUpdateEntity(postDTO, existing);

        PostEntity saved = postRepository.save(updated);
        // 기존 파일 삭제
        List<PostFileEntity> existingFiles = postFileRepository.findByPostEntity(existing);
        for (PostFileEntity file : existingFiles) {
            File storedFile = new File("C:/Users/User/Desktop/springtemp_img/" + file.getStoredFileName());
            if (storedFile.exists()) storedFile.delete();
            postFileRepository.delete(file);
        }

        // 새 파일 첨부가 있을 경우 추가 저장
        if (postDTO.getPostFile() != null && !postDTO.getPostFile().isEmpty()) {
            for (MultipartFile postFile : postDTO.getPostFile()) {
                String originalFileName = postFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:/Users/User/Desktop/springtemp_img/" + storedFileName;

                postFile.transferTo(new File(savePath));  // 실제 파일 저장

                PostFileEntity postFileEntity = PostFileEntity.toPostFileEntity(saved, originalFileName, storedFileName);
                postFileRepository.save(postFileEntity);

                saved.getPostFileEntityList().add(postFileEntity);
            }
        }
        return PostDTO.toPostDTO(saved);
    }


    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<PostDTO> paging(Pageable pageable) {
        int page= pageable.getPageNumber()-1;
        int pageLimit=10; //한 페이지에 보여지는 글 갯수
        //한페이지당 3개씩 글을 보여주고 정렬 기준은 ID 기준으로 내림차순 정렬
        Page<PostEntity> postEntities= postRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"postId")));

        //목록 : id, author, title, hits, createdTime
        Page<PostDTO> postDTOS= postEntities.map(post -> new PostDTO(post.getPostId(), post.getAuthor(), post.getTitle(), post.getPostHits(), post.getCreatedTime()));
        return postDTOS;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findPopularPostsLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Pageable topTen = PageRequest.of(0, 10); // 상위 10개
        List<PostEntity> entities = postRepository.findTopPostsLast7Days(sevenDaysAgo, topTen);

        List<PostDTO> dtoList = new ArrayList<>();
        for (PostEntity entity : entities) {
            dtoList.add(PostDTO.toPostDTO(entity));
        }
        return dtoList;
    }

}
