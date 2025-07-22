package SANTA.backend.chatting.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import SANTA.backend.core.user.domain.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ChattingServiceTest extends ServiceContext {

    @Nested @Transactional
    class 채팅방_조회_테스트{

        @Test @Transactional
        void 모든_채팅방_리스트를_조회할_수_있다(){
            //given
            String title ="title1";
            String subTitle = "subTitle1";

            String title2 = "title2";
            String subTitle2 = "subTitle2";

            chattingService.createChattingRoom(title,subTitle);
            chattingService.createChattingRoom(title2,subTitle2);

            //when
            List<ChattingRoomResponseDto> chattingRoomList = chattingService.getChattingRoomList();

            //then
            assertThat(chattingRoomList).size().isEqualTo(2);
        }

        @Test @Transactional
        void 채팅방_이름으로_채팅방을_조회할_수_있다(){
            //given
            String title ="title1";
            String subTitle = "subTitle1";

            String title2 = "title2";
            String subTitle2 = "subTitle2";

            chattingService.createChattingRoom(title,subTitle);
            chattingService.createChattingRoom(title2,subTitle2);

            //when
            List<ChattingRoomResponseDto> chattingRoomByName = chattingService.getChattingRoomByName(title);

            //then
            assertThat(chattingRoomByName).extracting(ChattingRoomResponseDto::chattingRoomName).containsExactlyInAnyOrder("title1");
            assertThat(chattingRoomByName).size().isEqualTo(1);
        }

    }

    @Nested @Transactional
    class 채팅방_생성_테스트{
        @Test
        void 채팅방을_생성할_수_있다(){
            //given
            String title = "chattingTitle";
            String subTitle = "chattingSubTitle";

            //when
            ChattingRoomEntity chattingRoom = chattingService.createChattingRoom(title, subTitle);

            //then
            assertThat(chattingRoom).extracting(ChattingRoomEntity::getId).isNotNull();
        }
    }

    @Nested
    class 채팅방_참가_테스트{
        @Test @Transactional
        void 채팅방에_참가할_수_있다(){
            //given
            String username = "user1";
            String password = "password1";
            String nickName = "nickName";
            User user = User.registerUser(username,password,nickName);

            String title = "title1";
            String subTitle = "subTitle1";

            //when
            User registerUser = userService.register(user);
            ChattingRoomEntity chattingRoom = chattingService.createChattingRoom(title, subTitle);

            Long chattingRoomId = chattingRoom.getId();
            Long myId = registerUser.getUserId();
            ChattingRoomUserEntity chattingRoomUser = chattingService.participateChattingRoom(chattingRoomId, myId);

            //then
            assertThat(chattingRoomUser).extracting(ChattingRoomUserEntity::getChattingRoomEntity).isEqualTo(chattingRoom);
            assertThat(chattingRoomUser.getUserEntity()).isNotNull();
            assertThat(chattingRoomUser.getUserEntity().getId()).isEqualTo(myId);
            System.out.println(chattingRoomUser.getUserEntity().getId());
        }
    }
}
