package com.ssm.maven.core.util;

import com.ssm.maven.core.entity.Scenicspot;
import com.ssm.maven.core.entity.Tourist;
import com.ssm.maven.core.service.MenuService;
import com.ssm.maven.core.service.ScenicService;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author wkr
 * @Date 2016-11-18
 * 工具类读取Excel类中内容
 */

public class ReadExcelTourist {

    private ScenicService scenicService;

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;

    //构造方法
//    public ReadExcelTourist() {
//    }

    //获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    //获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    //获取错误信息-暂时未用到暂时留着
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取客户信息集合
     *
     * @param /fielName
     * @return
     */
    public List<Tourist> getExcelInfo(MultipartFile Mfile, ScenicService scenicService) {
        this.scenicService = scenicService;
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf = (CommonsMultipartFile) Mfile; //获取本地存储路径
        File file = new File("D:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) file.mkdirs();
        //新建一个文件
        File file1 = new File("D:\\fileupload\\" + new Date().getTime() + ".xls");
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化客户信息的集合
        List<Tourist> touristList = new ArrayList<Tourist>();
        //初始化输入流
        FileInputStream is = null;
        Workbook wb = null;
        try {
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息

            //当excel是2003时
            wb = new HSSFWorkbook(is);
            //当excel是2007时
            //wb = new XSSFWorkbook(is);

            //读取Excel里面客户的信息
            touristList = readExcelValue(wb);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return touristList;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    private List<Tourist> readExcelValue(Workbook wb) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("散客", 1);
        map.put("团体", 2);
        map.put("电商", 3);
        List<Scenicspot> list = scenicService.getScenicspotAll(new Scenicspot());
        Map<String, Integer> map1 = list.stream().collect(Collectors.toMap(Scenicspot::getScenicname, Scenicspot::getId));

        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {//判断行数大于一，并且第一行必须有标题（这里有bug若文件第一行没值就完了）
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        } else {
            return null;
        }

        List<Tourist> touristList = new ArrayList<Tourist>();//声明一个对象集合
        Tourist tourist;//声明一个对象

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            tourist = new Tourist();

            //循环Excel的列  tourist.setTourist_type(Integer.parseInt(getValue(cell)));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM-dd");
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 1) {
                        tourist.setSex(getValue(cell).equals("0")?0: 1);// 性别
                    }else if (c == 2) {
                        tourist.setAge(Integer.parseInt(getValue(cell)));// 年龄
                    }else if (c == 3) {
                        tourist.setRegione(getValue(cell));// 所属地区
                    }else if (c == 4) {
                        tourist.setTourist_type(map.get(getValue(cell)));// 游客类型
                    }else if (c == 5) {
                        tourist.setEnter_day(simpleDateFormat_2.parse(getValue(cell)));// 日期
                    } else if (c == 6) {
                        tourist.setScience_id(map1.get(getValue(cell)));//景区名称
                    } else if (c == 7) {
                        tourist.setEnter_time(simpleDateFormat.parse(getValue(cell)));// 入园时间
                    } else if (c == 8) {
                        tourist.setLeave_time(simpleDateFormat.parse(getValue(cell)));// 离开时间
                    }
                }
            }
            //添加对象到集合中
            touristList.add(tourist);
        }
        return touristList;
    }

    /**
     * 得到Excel表中的值
     *
     * @param cell Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    @SuppressWarnings({"static-access", "unused"})
    private String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
            String cellFormatted = dataFormatter.formatCellValue(cell);
            return cellFormatted.trim();
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }

}