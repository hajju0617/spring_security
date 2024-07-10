package com.green.greengram.userfollow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.common.model.MyResponse;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import( CharEncodingConfiguration.class )
@WebMvcTest( UserFollowControllerImpl.class )
class UserFollowControllerTest {
//    @Autowired private ObjectMapper om;
//    @Autowired private MockMvc mvc;
//    @Autowired private UserFollowController controller;
//    @MockBean private UserFollowServiceImpl service;
//    private final String BASE_URL = "/api/user/follow";
//
////    @Test
////    void postUserFollow() throws Exception {
////        UserFollowReq p = new UserFollowReq(1, 2);
////        int resultData = 1;
////        given(service.postUserFollow(p)).willReturn(resultData);
////        String json = om.writeValueAsString(p);
////
////        MyResponse<Integer> expectedResult = MyResponse.<Integer>builder()
////                .statusCode(HttpStatus.OK)
////                .resultMsg(HttpStatus.OK.toString())
////                .resultData(resultData)
////                .build();
////
//////        Map expectedResultMap = new HashMap();
//////        expectedResultMap.put("statusCode", HttpStatus.OK);
//////        expectedResultMap.put("resultMsg", HttpStatus.OK.toString());
//////        expectedResultMap.put("resultData", resultData);
////
////        String expectedResultJson = om.writeValueAsString(expectedResult);
////
////        mvc.perform(MockMvcRequestBuilders
////                            .post(BASE_URL)
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(json)
////                )
////                .andExpect(status().isOk())
////                .andExpect(content().string(expectedResultJson))
////                .andDo(print());        // 결과를 콘솔에 출력
////
////        verify(service).postUserFollow(p);
////    }
//
//    @Test
//    void postUserFollow2() throws Exception {
//        UserFollowReq p = new UserFollowReq(1, 2);
//        int resultData = 10;
//        given(service.postUserFollow(p)).willReturn(resultData);
//        String json = om.writeValueAsString(p);
//
//        MyResponse<Integer> expectedResult = MyResponse.<Integer>builder()
//                .statusCode(HttpStatus.OK)
//                .resultMsg(HttpStatus.OK.toString())
//                .resultData(resultData)
//                .build();
//
////        Map expectedResultMap = new HashMap();
////        expectedResultMap.put("statusCode", HttpStatus.OK);
////        expectedResultMap.put("resultMsg", HttpStatus.OK.toString());
////        expectedResultMap.put("resultData", resultData);
//
//        String expectedResultJson = om.writeValueAsString(expectedResult);
//
//        mvc.perform(MockMvcRequestBuilders
//                        .post(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedResultJson))
//                .andDo(print());        // 결과를 콘솔에 출력
//
//        verify(service).postUserFollow(p);
//    }
//
//    @Test
//    void deleteUserFollow() throws Exception {
//        UserFollowReq p = new UserFollowReq(1, 2);
//        int resultData = 1;
//        given(service.deleteUserFollow(p)).willReturn(1);
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("from_user_id", String.valueOf(p.getFromUserId()));
//        params.add("to_user_id", String.valueOf(p.getToUserId()));
//
//        MyResponse<Integer> expectedResult = MyResponse.<Integer>builder()
//                .statusCode(HttpStatus.OK)
//                .resultMsg(HttpStatus.OK.toString())
//                .resultData(resultData)
//                .build();
//
//        String expectedResultJson = om.writeValueAsString(expectedResult);
//
//        mvc.perform(
//                delete(BASE_URL).params(params)
////                delete(BASE_URL + "?from_user_id=1&to_user_id=2")     // 위 아래 둘 중 하나 선택해서 하면 됨 (위쪽이 좀 더 유연한 코드)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().json(expectedResultJson))
//                .andDo(print());
//
//        verify(service).deleteUserFollow(p);
//
//    }
}