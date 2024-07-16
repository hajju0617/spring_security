package com.green.greengram.admin;

import com.green.greengram.admin.model.GroupByProviderCountRes;
import com.green.greengram.common.model.MyResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin")
public class AdminControllerImpl {
    private final AdminServiceImpl service;

    @GetMapping()
    public MyResponse<List<GroupByProviderCountRes>> getGroupByProviderCount() {
        List<GroupByProviderCountRes> list = service.getGroupByProviderCount();

        return MyResponse.<List<GroupByProviderCountRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("조회 완료")
                .resultData(list)
                .build();
    }
}
