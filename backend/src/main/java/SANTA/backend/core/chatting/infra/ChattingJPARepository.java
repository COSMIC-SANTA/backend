package SANTA.backend.core.chatting.infra;

import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChattingJPARepository implements ChattingRepository {

    private final EntityManager em;

    @Override
    public List<ChattingRoomEntity> findAllRooms() {
        return em.createQuery("select cr from ChattingRoomEntity cr", ChattingRoomEntity.class).getResultList();
    }

    @Override
    public List<ChattingRoomEntity> findByName(String roomName) {
        return em.createQuery("select cr from ChattingRoomEntity cr where cr.title =: roomName", ChattingRoomEntity.class)
                .setParameter("roomName", roomName)
                .getResultList();
    }

    @Override
    public ChattingRoomEntity saveChattingRoom(ChattingRoomEntity chattingRoomEntity) {
        em.persist(chattingRoomEntity);
        return chattingRoomEntity;
    }

    @Override
    public Optional<ChattingRoomEntity> findById(Long chattingRoomId) {
        return Optional.ofNullable(em.find(ChattingRoomEntity.class, chattingRoomId));
    }

    @Override
    public ChattingRoomUserEntity saveChattingRoomUser(ChattingRoomUserEntity chattingRoomUser) {
        em.persist(chattingRoomUser);
        return chattingRoomUser;
    }

    @Override
    public Optional<ChattingRoomUserEntity> findChattingRoomUser(Long roomId, Long userId) {
        return Optional.ofNullable(em.createQuery("""
        select cru
        from ChattingRoomUserEntity cru
        join fetch cru.userEntity ue
        join fetch cru.chattingRoomEntity cr
        where ue.id = :userId and cr.id = :roomId
        """, ChattingRoomUserEntity.class)
                .setParameter("userId", userId)
                .setParameter("roomId", roomId)
                .getSingleResult());
    }

    @Override
    public void saveChattingRoomMessage(ChattingRoomMessageEntity chattingRoomMessageEntity) {
        em.persist(chattingRoomMessageEntity);
    }

    @Override
    public List<ChattingRoomMessageEntity> getChattingRoomMessageByRoomId(Long roomId) {
        return em.createQuery("""
        select crm 
        from ChattingRoomMessageEntity crm
        join fetch crm.chattingRoomUser cru
        join fetch cru.chattingRoomEntity cre
        where cre.id = :roomId
        """, ChattingRoomMessageEntity.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }
}
