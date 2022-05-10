package com.college.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像
    String upload(MultipartFile file);
}
