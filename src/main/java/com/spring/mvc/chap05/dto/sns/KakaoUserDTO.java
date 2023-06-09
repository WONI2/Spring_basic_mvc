package com.spring.mvc.chap05.dto.sns;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter @Getter
@ToString
public class KakaoUserDTO {

//필요한 항목만 DTO를 만들어서 정보를 받아 옴
    private long id;
    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount; // 객체 안에 객체니까 또 새로운 클래스를 형성해줌


    @Setter @Getter @ToString
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Setter @Getter @ToString
        public static class Profile{
            private String nickname;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }

    }
}
