package SANTA.backend.core.user.infra;

import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.core.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository {

    private final EntityManager em;

    @Override
    @Transactional
    public User join(User user) {
        UserEntity userEntity = UserEntity.from(user);
        em.persist(userEntity);
        return User.fromEntity(userEntity);
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        return userEntity != null ? User.fromEntity(userEntity) : null;

    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        List<UserEntity> results = em.createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList();

        if (results.isEmpty()) {
            return null; // 혹은 Optional.empty() 등
        } else {
            return User.fromEntity(results.get(0));
        }
    }

    @Override
    public User findByNickname(String nickname) {
        return null;
    }
}
