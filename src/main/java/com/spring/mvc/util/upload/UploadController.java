package com.spring.mvc.util.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
public class UploadController {
    //    첨부파일저장 루트 경로
    @Value("${file.upload.rootpath}")
    private String rootpath;

    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    @PostMapping("/upload-file")
    public String uploadForm(@RequestParam("thumbnail") MultipartFile file) {
        log.info("file-name:{}", file.getOriginalFilename());
        log.info("file-size : {}KB", (double) file.getSize());

//        첨부파일을 스토리지에 저장
//    1. 루트 디렉터리 생성
        File root = new File(rootpath);
        if (!root.exists()) root.mkdirs();

//    2. 파일을 해당 경로에 저장하는 코드 (rootpath, 파일명)
//        File uploadFile = new File(rootpath, file.getOriginalFilename());
//
//        try {
//            file.transferTo(uploadFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String filePath = FileUtil.uploadFile(file, rootpath);

        return "redirect:/upload-form";
    }
}
