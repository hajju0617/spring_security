package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedFavoriteServiceImpl implements FeedFavoriteService{
    private final FeedFavoriteMapper mapper;

    public int toggleFavorite(FeedFavoriteToggleReq p) {
        int deleteAffectedRows = mapper.delFeedFavorite(p);

        if(deleteAffectedRows == 1) {
            return 0;
        }
        return mapper.insFeedFavorite(p);

    }
}
