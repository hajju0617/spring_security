package com.green.greengram.feedcomment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FeedCommentController {

    ResultDto<Long> postFeedComment(FeedCommentPostReq p);

    ResultDto<List<FeedCommentGetRes>> getFeedComment(long feedId);

    ResultDto<Integer> deleteFeedComment(FeedCommentDeleteReq p);

    // 테스트코드 작성시 매개변수 어노테이션은 없어도 됨 (ex. @RequestBody, @ModelAttribute 등등)
}
