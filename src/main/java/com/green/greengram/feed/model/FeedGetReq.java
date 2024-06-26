package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
public class FeedGetReq extends Paging {

//    @Schema(name="signed_user_id")
    @JsonIgnore
    private long signedUserId;  // 좋아요 처리 구분하기 위해서

    @Schema(name="profile_user_id", description = "프로필 사용자 ID (Not Required), 프로필 화면에서 사용")
    // swagger 에서 profile_user_id 이 이름으로 테스트 하기 위해서
    private Long profileUserId;     // 사용자의 프로필에 들어갔을 때 사용. 그 사용자가 쓴 피드만 보고 싶을 때( =알파 정보)

    public FeedGetReq(Integer page, Integer size
            , @BindParam("signed_user_id") long signedUserId
            , @BindParam("profile_user_id") Long profileUserId) {
        super(page, size == null ? GlobalConst.FEED_PAGING_SIZE : size);
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}

/*
BindParam, ConstructorProperties

ConstructorProperties : 전체 수정
BindParam : 부분 수정

쿼리 스트링의 키값은 대문자를 안 쓰는 게 일반적
네이버에 블랙핑크 검색시
https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EB%B8%94%EB%9E%99%ED%95%91%ED%81%AC

?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EB%B8%94%EB%9E%99%ED%95%91%ED%81%AC <- 이 부분이 쿼리 스트링
where :  키 값, query : 키 값
nexearch : 벨류 값, %EB%B8%94%EB%9E%99%ED%95%91%ED%81%AC : 벨류 값 (한글 블랙핑크 -> 16진수)

네이밍이 다른 걸 바인딩 해줌
signed_user_id 에 들어오는 값을 signedUserId 로 바인딩

BindParam 세팅을 안 했다면 쿼리스트링에 signedUserId 로 값을 받아야 한다
BindParam 세팅을 했으므로 signed_user_id 로 값을 받는다.

 */



