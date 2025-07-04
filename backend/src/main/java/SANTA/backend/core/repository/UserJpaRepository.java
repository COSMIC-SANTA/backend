package SANTA.backend.core.repository;

import SANTA.backend.core.domain.User;
import SANTA.backend.core.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository{

    private final EntityManager em;

    @Override
    public User join(User user) {
        UserEntity userEntity = new UserEntity(user);
        em.persist(userEntity);
        return user;
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class,id);
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("select u from user u where u.username =: username",User.class)
                .setParameter("username",username)
                .getSingleResult();
    }

    @Override
    public User findByNickname(String nickname) {
        return null;
    }
}
