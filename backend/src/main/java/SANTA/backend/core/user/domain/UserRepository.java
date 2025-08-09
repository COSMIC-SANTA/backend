package SANTA.backend.core.user.domain;

import SANTA.backend.core.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
//Integer은 가변. userentity의 id값의 자료형을 넣으면 됨

    User join(User user);
    Optional<User> findById(Long id);
    Boolean existsByNickname(String nickname);
    //동일 username이 있는지 확인하는 JPA구문 + username말고 nickname으로 하자
    //username을 받아 DB데이블에서 회원을 조회하는 메소드 작성
    Optional<User> findByUsername(String username);
    User findByNickname(String nickname);
    Optional<UserEntity> findEntityById(Long id);
    List<UserEntity> findEntityByUsername(String username);
    List<UserEntity> findEntityByNickname(String nickname);
}
