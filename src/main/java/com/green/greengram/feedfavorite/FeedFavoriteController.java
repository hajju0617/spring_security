package com.green.greengram.feedfavorite;

import com.green.greengram.common.model.MyResponse;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface FeedFavoriteController {
    MyResponse<Integer> toggleFavorite(@ParameterObject @ModelAttribute FeedFavoriteToggleReq p);
}
