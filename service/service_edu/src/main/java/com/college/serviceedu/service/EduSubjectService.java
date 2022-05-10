package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-17
 */
public interface EduSubjectService extends IService<EduSubject> {

//    添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

//    课程分类显示
    List<OneSubject> getAllOneTwoSubject();
}
