package com.college.getCode.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {

//    write
//    //设置表头名称
//    @ExcelProperty("学生编号")
//    private int sno;
//    //设置表头名称
//    @ExcelProperty("学生姓名")
//    private String sname;


//    read
    //设置表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;
    //设置表头名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
