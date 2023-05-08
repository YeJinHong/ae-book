package com.c201.aebook.auth.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Getter
    @Setter
    public class Properties {
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    @Getter
    @Setter
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;

        // 권한을 받아서 전화번호도 입력
        public boolean has_phone_number;
        public boolean phone_number_needs_agreement;
        public String phone_number;

        @Getter
        @Setter
        public class Profile {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public Boolean is_default_image;
        }
    }

}

