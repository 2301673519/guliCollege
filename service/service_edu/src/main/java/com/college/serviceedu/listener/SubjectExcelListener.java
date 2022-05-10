package com.college.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.GuliException;
import com.college.serviceedu.entity.EduSubject;
import com.college.serviceedu.entity.excel.ExcelSubjectData;
import com.college.serviceedu.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData == null) {
            throw new GuliException(20001,"添加失败");
        }
//        添加一级分类
        EduSubject existOneSubject =
                this.existOneSubject(excelSubjectData.getOneSubjectName(),eduSubjectService);
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }
//        添加二级分类
        String pid =existOneSubject.getId();
        EduSubject existTwoSubject =
                this.existTwoSubject(excelSubjectData.getOneSubjectName(),eduSubjectService,pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


//    判断一级分类是否重复
    private EduSubject existOneSubject(String name,EduSubjectService eduSubjectService){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }

//    判断二级分类是否重复
    private EduSubject existTwoSubject(String name,EduSubjectService eduSubjectService,String pid){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }
}
