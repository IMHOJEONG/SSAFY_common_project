package com.B305.ogym.domain.users.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfilePicture {

    @Column(name = "profile_picture_addr")
    private String pictureAddr; // S3 주소

    @Builder
    public ProfilePicture(String pictureAddr) {
        this.pictureAddr = pictureAddr;
    }

}
