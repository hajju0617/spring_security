package com.green.greengram.feed.model;

import lombok.*;
import java.util.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedPostRes {
    private long feedId;
    private List<String> pics;

}
