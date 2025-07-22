    package SANTA.backend.core.chatting.domain;

    import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
    import lombok.Builder;
    import lombok.Getter;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Getter
    public class ChattingRoom {

        private Long id;

        private String title;

        private String subTitle;

        private String clientUrl;

        private Integer userNum;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private List<ChattingRoomUser> chattingRoomUsers = new ArrayList<>();

        private static String clientBaseUrl = "spring/getMessage/";

        @Builder
        private ChattingRoom(Long id, String title, String subTitle, String clientUrl, Integer userNum ,LocalDateTime createdAt, LocalDateTime modifiedAt,
                             List<ChattingRoomUser> chattingRoomUsers) {
            this.id = id;
            this.title = title;
            this.subTitle = subTitle;
            this.clientUrl = clientUrl;
            this.userNum = userNum;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.chattingRoomUsers = chattingRoomUsers;
        }

        public static ChattingRoom from(ChattingRoomEntity chattingRoomEntity) {
            return ChattingRoom.builder()
                    .id(chattingRoomEntity.getId())
                    .title(chattingRoomEntity.getTitle())
                    .subTitle(chattingRoomEntity.getSubTitle())
                    .clientUrl(chattingRoomEntity.getClientUrl())
                    .userNum(chattingRoomEntity.getUserNum())
                    .createdAt(chattingRoomEntity.getCreatedAt())
                    .modifiedAt(chattingRoomEntity.getModifiedAt())
                    .chattingRoomUsers(
                            Optional.ofNullable(chattingRoomEntity.getChattingRoomUserEntities())
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .map(ChattingRoomUser::from)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }

        public static ChattingRoom createChattingRoom(String title, String subTitle){
            return ChattingRoom.builder()
                    .title(title)
                    .subTitle(subTitle)
                    .userNum(0)
                    .chattingRoomUsers(new ArrayList<>())
                    .build();
        }

        public String getClientUrl(){
            return clientBaseUrl+id;
        }
    }
