package SANTA.backend.chatting.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.chatting.domain.ChattingRoom;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import io.jsonwebtoken.impl.io.Streams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Stream;

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
            Assertions.assertThat(chattingRoomList).size().isEqualTo(2);
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
            Assertions.assertThat(chattingRoomByName).extracting(ChattingRoomResponseDto::chattingRoomName).containsExactlyInAnyOrder("title1");
            Assertions.assertThat(chattingRoomByName).size().isEqualTo(1);
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
            ChattingRoom chattingRoom = chattingService.createChattingRoom(title, subTitle);

            //then
            assertThat(chattingRoom).extracting(ChattingRoom::getId).isNotNull();
        }
    }
}
