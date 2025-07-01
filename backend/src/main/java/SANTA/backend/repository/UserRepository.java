package SANTA.backend.repository;

import SANTA.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
//Integer은 가변. userentity의 id값의 자료형을 넣으면 됨

    Boolean existsByNickname(String nickname);
    Boolean existsByuserId(String userId);
    //동일 username이 있는지 확인하는 JPA구문 + username말고 nickname으로 하자

    //username을 받아 DB데이블에서 회원을 조회하는 메소드 작성
    UserEntity findByUsername(String username);
}
