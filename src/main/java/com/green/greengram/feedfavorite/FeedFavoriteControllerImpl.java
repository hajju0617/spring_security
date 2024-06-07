package com.green.greengram.feedfavorite;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/feed/favorite")
public class FeedFavoriteControllerImpl implements FeedFavoriteController{
    private final FeedFavoriteService service;

    @GetMapping
    @Operation(summary = "좋아요", description = "toggle 처리")
    public ResultDto<Integer> toggleFavorite(@ParameterObject @ModelAttribute FeedFavoriteToggleReq p) {
        int result = service.toggleFavorite(p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 0 ? "좋아요 취소" : "좋아요")
                .resultData(result)
                .build();
    }
}
