package SANTA.backend.core.user.infra;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.core.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public User join(User user) {
        UserEntity userEntity = UserEntity.from(user);
        em.persist(userEntity);
        return User.fromEntity(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        return userEntity != null ? Optional.of(User.fromEntity(userEntity)) : Optional.empty();

    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<UserEntity> results = em.createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty(); // 혹은 Optional.empty() 등
        } else {
            return Optional.of(User.fromEntity(results.get(0)));
        }
    }

    @Override
    public User findByNickname(String nickname) {
        return null;
    }

    @Override
    public Optional<UserEntity> findEntityById(Long id) {
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    @Override
    public List<UserEntity> findEntityByUsername(String username) {
        return em.createQuery("select ue from UserEntity ue where ue.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();
    }


    @Override
    public List<UserEntity> findEntityByNickname(String nickname) {
        return em.createQuery("select u from UserEntity u where u.nickname = :nickname", UserEntity.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
