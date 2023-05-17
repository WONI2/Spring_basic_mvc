package com.spring.mvc.util.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    /*
    * 1. 사용자가 파일을 업로드 했을 때
    * 중복이 없는 새로운 파일명을 생성해서 해당 파일명으로 업로드하는 메서드
    *
    * */

    /**
     *
     * @param file -사용자가 업로드한 파일 객체
     * @param rootpath - 서버에 파일업로드 루트 경로(ex: D:/spring-prj/upload/)
     * @return - 업로드가 완료된 파일의 위치 경로 (ex: /2023/05/16/qqqqq_cat.jpg)
     *
     */
    public static String uploadFile(
            MultipartFile file,
            String rootpath
    ) {
//        원본 파일명을 중복이 없는 랜덤 이름으로 변경
//        ex)cat.png -> qqqqq_cat.png
//        UUID : 랜덤 문자 만들어 줌. 회원정보를 보호하기 위함
        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        이 파일을 저장할 날짜별 폴더 생성
//        ex) D:/spring-prj/upload/2023/05/16/qqqqq_cat.png
        String newPath = makeDateFormatDirectory(rootpath);
//        파일 업로드
        try {
            file.transferTo(new File(newPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        저장된 파일의 풀 경로
        String fullPath = newPath + "/" +newFileName;

//        fullPath = d:/abc/upload/2023/05/16/fdf.png
//        rootpath = d:/abc/upload/

        return fullPath.substring(rootpath.length());
    }

    /**
     * 루트경로를 받아서 일자별로 폴더 생성 후 루트경로+날짜폴더 경로를 리턴
     * @param rootpath - 파일 업로드 루트 경로
     * @return - 날짜 폴더가 포함된 새로운 업로드 경로
     */
    private static String makeDateFormatDirectory(String rootpath) {

//        오늘날짜 정보 가져오기
        LocalDateTime now = LocalDateTime.now();
        int y = now.getYear();
        int m = now.getMonthValue();
        int d = now.getDayOfMonth();

        List<String> dateInfo = List.of(String.valueOf(y),
                                        len2(m), len2(d));
        String directoryPath = rootpath;
        for (String s : dateInfo) {
            directoryPath += "/" + s;
            File f = new File(directoryPath); //"D:/ABC/DEF" 를 만들도록 하는 경로
            if(!f.exists()) f.mkdirs();
        }

//
        return directoryPath;
    }

    private static String len2(int n) {

        return new DecimalFormat("00").format(n);
    }


}
