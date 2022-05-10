package com.college.oss.controller;

import com.college.oss.service.OssService;
import com.college.serviceutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public R uploadOssFile(MultipartFile file){
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }
}
