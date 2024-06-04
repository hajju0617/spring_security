package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.*;

@Getter
@Builder
@EqualsAndHashCode
public class FeedPicPostDto {
    private long feedId;

    @Builder.Default
    private List<String> fileNames = new ArrayList<>();
}
