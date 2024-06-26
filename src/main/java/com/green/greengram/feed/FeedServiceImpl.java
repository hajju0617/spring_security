package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        int result = mapper.postFeed(p);

        FeedPicPostDto picDto = FeedPicPostDto.builder().feedId(p.getFeedId()).build();

        try {
            String path = String.format("feed/%d", p.getFeedId());
            customFileUtils.makeFolders(path);

            for(MultipartFile pic : pics) {
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                picDto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s", path, saveFileName);
                customFileUtils.transferTo(pic, target);
            }
            int affectedRowsPics = mapper.postFeedPics(picDto);    // Post 한 사진을 db에 저장해줌 ( 이게 없을 경우 새로고침 이후 사진이 로딩 되지 않음)
                                            //                                  why? db에 넣질 않아서.
                                            // mapper.postFeedPics(picDto); 는 db에서 영향받은 행 값
            log.info("affectedRowsPics : {}", affectedRowsPics);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("피드 등록 오류 발생");
        }

        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(picDto.getFileNames())
                .build();
    }

    public List<FeedGetRes> getFeed(FeedGetReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        List<FeedGetRes> list = mapper.getFeed(p);
        log.info("list : {}", list);

        for(FeedGetRes res : list) {
            List<String> pics = mapper.getFeedPicsByFeedId(res.getFeedId());    // 이 부분이 n을 뜻함
            res.setPics(pics);

            List<FeedCommentGetRes> comments = mapper.getFeedCommentTopBy4ByFeedId(res.getFeedId());
            if(comments.size() == GlobalConst.COMMENT_SIZE_PER_FEED) {
                res.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
            res.setComments(comments);
        }
        log.info("list : {}", list);
        return list;
    }
}
