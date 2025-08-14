package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.infra.ChattingLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final ChattingService chattingService;

    private final ChattingLockRepository chattingLockRepository;

    public void participateChattingRoom(Long chattingRoomId, Long myId) throws InterruptedException {
        while (!chattingLockRepository.participateChattingRoomLock(chattingRoomId)) {
            Thread.sleep(100);
        }
        try {
            chattingService.participateChattingRoom(chattingRoomId, myId);
        } finally {
            chattingLockRepository.participateChattingRoomUnlock(chattingRoomId);
        }
    }
}
