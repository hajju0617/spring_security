package com.green.greengram.common;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CustomFileUtilsTest {

    @Test
    void deleteFolder2() {
        String delFolderPath = "D:/2024-1/download/delFoler2";
        File delFolder = new File(delFolderPath);
        delFolder.delete();

    }

    @Test
    void deleteFolder() {
        CustomFileUtils customFileUtils = new CustomFileUtils("");

        String delFolderPath = "D:/2024-1/download/delFolder";
        File delFolder = new File(delFolderPath);
        delFolder.delete(); // 삭제가 안됨 (폴더 안에 파일, 디렉토리 하나라도 있으면 삭제가 안됨.)

    }

    @Test
    void deleteFolder3() {
        CustomFileUtils customFileUtils = new CustomFileUtils("");

        String delFolderPath = "D:/2024-1/download/delFolder";
        customFileUtils.deleteFolder(delFolderPath);


    }
}