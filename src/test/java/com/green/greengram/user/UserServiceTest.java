package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@Import({ CustomFileUtils.class, UserServiceImpl.class })
@TestPropertySource(
        properties = {
                "file.directory=D:/2024-1/download/greengram_ver3tdd/"
        }
)
class UserServiceTest {

    @MockBean
    UserMapper mapper;
    @Autowired
    UserService service;
    @Autowired CustomFileUtils customFileUtils;

    @Value("${file.directory}") String uploadPath;

    @Test
    void postSignUp() throws IOException {
        String p1Upw = "abc";
        SignUpPostReq p1 = new SignUpPostReq();
        p1.setUserId(100);
        p1.setUpw(p1Upw);

        given(mapper.postUser(p1)).willReturn(1);

        String p2Upw = "def";
        SignUpPostReq p2 = new SignUpPostReq();
        p2.setUserId(200);
        p2.setUpw("p2Upw");

        given(mapper.postUser(p2)).willReturn(2);

        MultipartFile fm1 = new MockMultipartFile(
                "pic", "28bfba96-587b-4c0a-8e3d-dc1a473350ca.jpg", "image/jpg"
                , new FileInputStream("D:/2024-1/download/greengram_ver3tdd/user/66/28bfba96-587b-4c0a-8e3d-dc1a473350ca.jpg")
        );

        int result = service.postSignUp(fm1, p1);
        assertEquals(1, result);

        p1.getPic();

        File saveFile1 = new File(uploadPath, String.format("%s/%d/%s", "user", p1.getUserId(), p1.getPic()));
        assertTrue(saveFile1.exists(), "1. 파일이 만들어지지 않음");
                                              // 에러가 발생한 경우 출력하는 메세지
        saveFile1.delete();

        assertNotEquals(p1Upw, p1.getUpw()); // 비밀번호가 달라져야 true

        int result2 = service.postSignUp(fm1, p2);
        assertEquals(2, result2);

        File saveFile2 = new File(uploadPath, String.format("%s/%d/%s", "user", p2.getUserId(), p2.getPic()));
        assertTrue(saveFile2.exists(), "2. 파일이 만들어지지 않음");
        // 에러가 발생한 경우 출력하는 메세지
        saveFile2.delete();

        assertNotEquals(p2Upw, p2.getUpw());

    }

    @Test
    void postSignIn() {
        SignInPostReq req1 = new SignInPostReq();
        req1.setUid("id1");
        req1.setUpw("1212");
        String hashedUpw1 = BCrypt.hashpw(req1.getUpw(), BCrypt.gensalt());

        SignInPostReq req2 = new SignInPostReq();
        req2.setUid("id2");
        req2.setUpw("2323");
        String hashedUpw2 = BCrypt.hashpw(req2.getUpw(), BCrypt.gensalt());


        User user1 = new User(10, req1.getUid(), hashedUpw1, "홍길동1", "사진1.jpg", "", "null");
        given(mapper.getUserById(req1.getUid())).willReturn(user1);

        User user2 = new User(10, req2.getUid(), hashedUpw2, "홍길동2", "사진2.jpg", "", "null");
        given(mapper.getUserById(req2.getUid())).willReturn(user2);



        try(MockedStatic<BCrypt> mockedStatic = mockStatic(BCrypt.class)) {
            mockedStatic.when(() -> BCrypt.checkpw(req1.getUpw(), user1.getUpw())).thenReturn(true);
            mockedStatic.when(() -> BCrypt.checkpw(req2.getUpw(), user2.getUpw())).thenReturn(true);

            SignInRes res1 = service.postSignIn(req1);
            assertEquals(user1.getUserId(), res1.getUserId(), "1. userId 다름");
            assertEquals(user1.getNm(), res1.getNm(), "1. nm 다름");
            assertEquals(user1.getPic(), res1.getPic(), "1. pic 다름");

            mockedStatic.verify(() -> BCrypt.checkpw(req1.getUpw(), user1.getUpw()));

            SignInRes res2 = service.postSignIn(req2);
            assertEquals(user2.getUserId(), res2.getUserId(), "2. userId 다름");
            assertEquals(user2.getNm(), res2.getNm(), "2. nm 다름");
            assertEquals(user2.getPic(), res2.getPic(), "2. pic 다름");

            mockedStatic.verify(() -> BCrypt.checkpw(req2.getUpw(), user2.getUpw()));
        }

        SignInPostReq req3 = new SignInPostReq();
        req3.setUid("id3");
        given(mapper.getUserById(req3.getUid())).willReturn(null);

        Throwable ex1 = assertThrows(RuntimeException.class, () -> {
            service.postSignIn(req3);
        }, "아이디 없음 예외 처리 안 함");
        assertEquals("아이디를 확인하세요.", ex1.getMessage(), "아이디 없음 에러메세지 다름");

        SignInPostReq req4 = new SignInPostReq();
        req1.setUid("id4");
        req1.setUpw("6666");

        String hashedUpw4 = BCrypt.hashpw("7777", BCrypt.gensalt());

        User user4 = new User(100, req4.getUid(), hashedUpw4, "홍길동4", "사진4.jpg", null,"");

        given(mapper.getUserById(req3.getUid())).willReturn(null);

        Throwable ex2 = assertThrows(RuntimeException.class, () -> {
            service.postSignIn(req4);
        }, "비밀번호 다름 예외 처리 안 함");
        assertEquals("아이디를 확인하세요.", ex1.getMessage(), "비밀번호 다름 에러메세지 다름");
    }

    @Test
    void getUserInfo() {
        UserInfoGetReq p1 = new UserInfoGetReq(2, 1);
        UserInfoGetRes result1 = new UserInfoGetRes();
        result1.setNm("test1");
        given(mapper.selProfileUserInfo(p1)).willReturn(result1);

        UserInfoGetReq p2 = new UserInfoGetReq(3, 1);
        UserInfoGetRes result2 = new UserInfoGetRes();
        result2.setNm("test2");
        given(mapper.selProfileUserInfo(p2)).willReturn(result2);

        UserInfoGetRes res1 = service.getUserInfo(p1);
        assertEquals(result1, res1);

        UserInfoGetRes res2 = service.getUserInfo(p2);
        assertEquals(result2, res2);
    }

    @Test
    void patchProfilePic() throws IOException {
        //서비스를 테스트 : 새로운 파일이 올라오면 PK를 이용해 기존에 있던 폴더를 지우고 다시 만들어 파일 넣음
        long signedUserId1 = 500;
        final String ORIGIN_FILE_PATH = String.format("%stest/%s", uploadPath, "a.jpg");
        String midPath1 = String.format("%suser/%d", uploadPath, signedUserId1);
        File originFile = new File(ORIGIN_FILE_PATH);
        File copyFile1 = new File(midPath1, "a.jpg");

        customFileUtils.deleteFolder(midPath1);
        customFileUtils.makeFolders("user/" + signedUserId1);
        Files.copy(originFile.toPath(), copyFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);

//            File dicDefault = new File("D:/2024-1/download/greengram_ver3tdd/user/66/28bfba96-587b-4c0a-8e3d-dc1a473350ca.jpg");
//            File destFile = new File(dic1, dicDefault.getName());
//            Files.copy(dicDefault.toPath() ,destFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);

        UserProfilePatchReq p1 = new UserProfilePatchReq();//호출을 위한 파라미터 세팅
        p1.setSignedUserId(signedUserId1);

        MultipartFile fm1 = new MockMultipartFile("pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%stest/b.jpg", uploadPath)));
        p1.setPic(fm1);

        String fileNm1 = service.patchProfilePic(p1); //null이 아닌지만 체크 랜덤 파일명만 넘어옴
        assertNotNull(fileNm1, "1. 파일 명이 Null이 넘어왔음");
        //midPath1폴더 존재, 해당 폴더 파일 1개 존재, 그 파일의 파일 명이 fileNm1과 같아야 함

        // 1. midPath 폴더가 존재해야 됨
        File p = new File(midPath1); //D:\2024_BACK_JI\download\greengram_tdd\\user\500

        // 2. 해당 폴더에 파일이 1개만 존재
        assertEquals(true, p.exists(), "1. midPath1 폴더가 존재하지 않음");
        assertEquals(1, p.listFiles().length, "2. 해당 폴더에 파일이 1개가 아님");

        // 3. 그 파일의 파일명이 fileNM1과 같아야 함
        File file1 = p.listFiles()[0];
        assertEquals(fileNm1, file1.getName());

        verify(mapper).updProfilePic(p1);
    }

}
