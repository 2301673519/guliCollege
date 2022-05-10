package com.college.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.GuliException;
import com.college.serviceedu.entity.EduSubject;
import com.college.serviceedu.entity.excel.ExcelSubjectData;
import com.college.serviceedu.entity.subject.OneSubject;
import com.college.serviceedu.entity.subject.TwoSubject;
import com.college.serviceedu.listener.SubjectExcelListener;
import com.college.serviceedu.mapper.EduSubjectMapper;
import com.college.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhouxiaodong
 * @since 2022-04-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //    添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream,ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception exception){
            exception.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

//    课程分类显示
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1查询出所有一级分类 parent_id=0
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);

        //2查询出所有二级分类 parent_id!=0
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        //3封装一级分类
        List<OneSubject> finnalList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            //new OneSubject设置值，add加入list
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //复制操作
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finnalList.add(oneSubject);
            //4封装二级分类
            //创建list集合封装每一个一级分类的二级分类
            ArrayList<TwoSubject> twoFinnalList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject eduSubject2 = twoSubjectList.get(j);
                if (eduSubject.getId().equals(eduSubject2.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //如过一级分类的id==二级分类的parent_id,进行封装
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoFinnalList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinnalList);
        }

        return finnalList;
    }
}
