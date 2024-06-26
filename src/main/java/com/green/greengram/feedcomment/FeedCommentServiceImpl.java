package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService {
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    public long postFeedComment(FeedCommentPostReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        int affectedRows = mapper.postFeedComment(p);
        return p.getFeedCommentId();
    }

    public int deleteFeedComment(FeedCommentDeleteReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        return mapper.deleteFeedComment(p);
    }

    public List<FeedCommentGetRes> getFeedComment(long feedId) {
        return mapper.selFeedCommentList(feedId);
    }
}
