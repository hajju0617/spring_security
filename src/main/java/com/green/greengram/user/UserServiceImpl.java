package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int postSignUp(MultipartFile pic, SignUpPostReq p) {
        log.info("pic : {}",pic);
        String saveFileName = customFileUtils.makeRandomFileName(pic);
        log.info("saveFileName : {}",saveFileName);
        p.setPic(saveFileName);
        String hashedPw = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashedPw);
        int result = mapper.postUser(p);
        log.info("p : {}", p);

        if(pic == null) {
            return result;
        }
        try {
            String path = String.format("user/%d", p.getUserId());
            customFileUtils.makeFolders(path);
            log.info("path : {}", path);

            String target = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(pic, target);
            log.info("target : {}",target);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 오류");
        }
        return result;
    }

    public SignInRes postSignIn(SignInPostReq p) {
        log.info("p:{}", p);
        User user = mapper.getUserById(p.getUid());
        log.info("user:{}", user);

        if (user == null) {
            throw new RuntimeException("아이디를 확인하세요.");
        } else if (!(BCrypt.checkpw(p.getUpw(), user.getUpw()))) {
            throw new RuntimeException("비밀번호를 확인하세요.");
        }

        return SignInRes.builder()
                .userId(user.getUserId())
                .nm(user.getNm())
                .pic(user.getPic())
                .build();
    }

    public int patchPassword(PatchPasswordReq p) {
        User user = mapper.getUserById(p.getUid());
        log.info("p:{}", p);

        if(user == null) {
            throw new RuntimeException("아이디를 확인해 주세요");
        } else if (!(BCrypt.checkpw(p.getCurrentPw(), user.getUpw()))) {
            throw new RuntimeException("비밀번호를 확인해 주세요");
        }
        String newPassword = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setNewPw(newPassword);
        log.info("p:{}", p);
        p.setUserId(user.getUserId());
        return mapper.patchPassword(p);
    }

    public UserInfoGetRes getUserInfo(UserInfoGetReq p) {
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {  // 기존  폴더 삭제
        String fileNm = customFileUtils.makeRandomFileName(p.getPic()); // 랜덤 파일명 얻어와서 fileNm에 저장
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        try {
            String midPath = String.format("user/%d", p.getSignedUserId());
//    //        String folderPath = customFileUnits.uploadPath + "/user/" + p.getSignedUserId();
//            String folderPath = String.format("%s/user/%d", customFileUtils.uploadPath, p.getSignedUserId());
//            // + 기호를 쓰기 싫다면 이렇게 수정
            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);

            customFileUtils.makeFolders(midPath);
            String filePath = String.format("%s/%s", midPath, fileNm);

            customFileUtils.transferTo(p.getPic(), filePath);   // public void transferTo(MultipartFile mf, String target) throws Exception
                                                                // 예외를 던지고 있으므로 예외처리를 해줘야 레드라인이 생기지 않는다.
        } catch (Exception e) {
            throw new RuntimeException("프로필 사진 수정 실패");
        }
        return fileNm;
    }
}
