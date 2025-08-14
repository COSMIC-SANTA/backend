package SANTA.backend.core.chatting.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ChattingLockRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private static final Duration ttl = Duration.ofMillis(3_000);

    public Boolean participateChattingRoomLock(Long chattingRoomId) {
        return redisTemplate.opsForValue()
                .setIfAbsent(generateParticipateChattingRoomKey(chattingRoomId), "lock", ttl);
    }

    public Boolean participateChattingRoomUnlock(Long chattingRoomId) {
        return redisTemplate.delete(generateParticipateChattingRoomKey(chattingRoomId));
    }

    private static String generateParticipateChattingRoomKey(Long key) {
        return "participate:" + key.toString();
    }
}
