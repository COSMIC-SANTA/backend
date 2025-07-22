package SANTA.backend.core.chatting.domain;

import java.util.List;

public interface ChattingRepository {
    List<ChattingRoom> findAllRooms();

    List<ChattingRoom> findByName(String roomName);

    ChattingRoom saveChattingRoom(ChattingRoom chattingRoom);

    ChattingRoom findById(Long chattingRoomId);

    ChattingRoomUser saveChattingRoomUser(ChattingRoomUser chattingRoomUser);

    ChattingRoomUser findChattingRoomUser(Long roomId, Long userId);

    void saveChattingRoomMessage(ChattingRoomMessage chattingRoomMessage);

    List<ChattingRoomMessage> getChattingRoomMessageByRoomId(Long roomId);
}
