package SANTA.backend.chatting.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.chatting.domain.ChattingRoom;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ChattingServiceTest extends ServiceContext {

    @Nested
    class 채팅방_조회_테스트{


    }

    @Nested
    class 채팅방_생성_테스트{
        @Test
        void 채팅방을_생성할_수_있다(){
            //given
            String title = "chattingTitle";
            String subTitle = "chattingSubTitle";

            //when
            ChattingRoom chattingRoom = chattingService.createChattingRoom(title, subTitle);

            //then
            assertThat(chattingRoom).extracting(ChattingRoom::getId).isNotNull();
        }
    }
}
