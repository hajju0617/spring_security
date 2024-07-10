package com.green.greengram.userfollow;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user/follow")
public class UserFollowControllerImpl implements UserFollowController {
    private final UserFollowService service;

    @PostMapping
    public MyResponse<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        int result = service.postUserFollow(p);

        return MyResponse.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }
    /*
    POST, PUT >>>> 일반적으로 body

    GET, DELETE >>>> 일반적으로 쿼리 스트링 (Why? --> 속도 빠름)
     */

    @DeleteMapping
    public MyResponse<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        int result = service.deleteUserFollow(p);

        return MyResponse.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }
}
