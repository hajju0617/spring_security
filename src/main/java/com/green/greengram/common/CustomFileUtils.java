package com.green.greengram.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
@Getter
public class CustomFileUtils {
    @Value("${file.directory}")

    public final String uploadPath;
    public CustomFileUtils(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String makeFolders(String path) {
        File folder = new File(uploadPath, path);

        folder.mkdirs();
        return folder.getAbsolutePath();    // getAbsolutePath() : 절대 경로 (절대 주소값)
    }   // folder 객체는 uploadPath 아래 path에 해당하는 파일 또는 디렉토리를 가리키게 됨


    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }   // 랜덤 문자열 얻음

    public String getExt(String fileName) {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(idx);
    }   // 파일명에서 확장자 추출
    // 이 코드는 fileName 에서 마지막 점(.)의 위치를 찾고 그 위치부터 문자열의 끝까지를 반환합니다. 이는 파일의 확장자를 추출하는 데 유용

    public String makeRandomFileName(String fileName) {
        return makeRandomFileName() + getExt(fileName);
    }   // 랜덤 파일명+확장자

    public String makeRandomFileName(MultipartFile mf) {
        return mf == null ? null : makeRandomFileName(mf.getOriginalFilename());
    }   // MultipartFile 객체를 매개변수로 받아서 그 파일의 원본 이름을 기반으로 랜덤 파일 이름을 생성하는 메서드

    public void transferTo(MultipartFile mf, String target) throws Exception {
        File savefile = new File(uploadPath, target);
        mf.transferTo(savefile);        // throws Exception 을 지우면 transferTo 레드라인
    }   // 파일 저장


    public void deleteFolder(String absoluteFolderPath) {       // absoluteFolderPath : 절대 주소
        File folder = new File(absoluteFolderPath); // Ex)D:\2024-1\download\greengram_ver3/user/pk값

        if(folder.exists() && folder.isDirectory()) {   // exists() : 폴더나 디렉토리가 존재한다면 true, 존재하지 않는다면 false 를 반환 (경로에 파일이나 디렉토리가 실제로 존재하는지 확인)
                                                        // isDirectory() : 폴더 였다면 true, 파일이였다면 false (지정된 경로가 디렉토리인지 확인합니다.)

            File[] files = folder.listFiles();  // listFiles() : 파일객체로 객체화 해서 배열 형태로 리턴해줌 (여러개일 수 있으므로)
                                                // folder 디렉토리 안의 모든 파일과 디렉토리를 File 객체 배열로 반환 (이 배열은 디렉토리 내부의 각 항목을 나타냄)

            for(File f : files) {
                if(f.isDirectory()) {
                    deleteFolder(f.getAbsolutePath());  // 재귀호출 --> public void deleteFolder(String absoluteFolderPath) 호출
                } else {
                    f.delete();     // f가 디렉토리가 아니라 파일일 경우 f.delete()를 호출하여 파일을 삭제
                }
            }
            folder.delete();
        }
    }   // 폴더 삭제 (이 메소드는 재귀 호출을 통해 주어진 폴더와 그 하위의 모든 폴더 및 파일을 삭제 함)
}
