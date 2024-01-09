package com.yulkost.service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    public String saveImg(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException();
        }

            String uploadPath = "D:/Main/Zamok/YulkostAplications/Service/Service-cafe/src/main/resources/static/images";
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile+"."+file.getOriginalFilename();
            file.transferTo(new File(uploadPath +"/"+resultFileName));
            return resultFileName;
    }
}
