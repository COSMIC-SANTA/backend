package SANTA.backend.core.chatting.dto;

public record MessageDto(
        Long userId,
        Long roomId,
        String message
) {
}
