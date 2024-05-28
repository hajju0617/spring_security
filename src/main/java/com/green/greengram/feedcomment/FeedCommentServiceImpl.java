package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedCommentServiceImpl {
    private final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p) {
        int affectedRows = mapper.postFeedComment(p);
        return p.getFeedCommentId();
    }

    public int deleteFeedComment(FeedCommentDeleteReq p) {
        return mapper.deleteFeedComment(p);
    }

    public List<FeedCommentGetRes> getFeedComment(long feedId) {
        return mapper.selFeedCommentList(feedId);
    }
}
