package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;

import java.util.List;

public interface ChattingRepository {
    List<ChattingRoomEntity> findAllRooms();

    List<ChattingRoomEntity> findByName(String roomName);

    ChattingRoomEntity saveChattingRoom(ChattingRoomEntity chattingRoom);

    ChattingRoomEntity findById(Long chattingRoomId);

    ChattingRoomUserEntity saveChattingRoomUser(ChattingRoomUserEntity chattingRoomUser);

    ChattingRoomUserEntity findChattingRoomUser(Long roomId, Long userId);

    void saveChattingRoomMessage(ChattingRoomMessageEntity chattingRoomMessage);

    List<ChattingRoomMessageEntity> getChattingRoomMessageByRoomId(Long roomId);
}
