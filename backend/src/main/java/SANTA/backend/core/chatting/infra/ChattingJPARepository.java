package SANTA.backend.core.chatting.infra;

import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.chatting.domain.ChattingRoom;
import SANTA.backend.core.chatting.domain.ChattingRoomUser;
import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChattingJPARepository implements ChattingRepository {

    private final EntityManager em;

    @Override
    public List<ChattingRoom> findAllRooms() {
        List<ChattingRoomEntity> chattingRooms = em.createQuery("select cr from ChattingRoomEntity cr", ChattingRoomEntity.class).getResultList();
        return chattingRooms.stream().map(ChattingRoom::from).toList();
    }

    @Override
    public List<ChattingRoom> findByName(String roomName) {
        List<ChattingRoomEntity> chattingRooms = em.createQuery("select cr from ChattingRoomEntity cr where cr.title =: roomName", ChattingRoomEntity.class)
                .setParameter("roomName", roomName)
                .getResultList();
        return chattingRooms.stream().map(ChattingRoom::from).toList();
    }

    @Override
    public ChattingRoom saveChattingRoom(ChattingRoom chattingRoom) {
        ChattingRoomEntity chattingRoomEntity = ChattingRoomEntity.from(chattingRoom);
        em.persist(chattingRoomEntity);
        return ChattingRoom.from(chattingRoomEntity);
    }

    @Override
    public ChattingRoom findById(Long chattingRoomId) {
        ChattingRoomEntity chattingRoomEntity = em.find(ChattingRoomEntity.class, chattingRoomId);
        return ChattingRoom.from(chattingRoomEntity);
    }

    @Override
    public ChattingRoomUser saveChattingRoomUser(ChattingRoomUser chattingRoomUser) {
        ChattingRoomUserEntity chattingRoomUserEntity = ChattingRoomUserEntity.from(chattingRoomUser);
        em.persist(chattingRoomUserEntity);
        return ChattingRoomUser.from(chattingRoomUserEntity);
    }
}
