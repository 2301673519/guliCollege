package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-19
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);
}
