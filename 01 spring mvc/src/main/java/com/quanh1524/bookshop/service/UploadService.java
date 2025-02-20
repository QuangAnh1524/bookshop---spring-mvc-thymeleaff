package com.quanh1524.bookshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadService {
    public String handleSaveFile(MultipartFile file, String targetFolder) throws IOException {
        //don't upload file
        if (file.isEmpty()) {
            return "";
        }
        // Lấy dữ liệu file
        byte[] bytes = file.getBytes();
        String rootPath = "D:/bookshop/01 spring mvc/src/main/resources/images/";
        File dir = new File(rootPath + File.separator + targetFolder);
        if (!dir.exists()) dir.mkdirs();  // Tạo thư mục nếu chưa có

        // Tạo file ảnh và lưu vào thư mục
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();  // Tạo tên file với thời gian để tránh trùng lặp
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

        // Lưu file vào server
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        return fileName;
    }
}
