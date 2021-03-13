package com.ssm.maven.core.admin;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.ssm.maven.core.entity.*;
import com.ssm.maven.core.service.CarService;
import com.ssm.maven.core.service.TemperatureService;
import com.ssm.maven.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("FileTwo")
public class FileTwoController {
    private static Log log = LogFactory.getLog(FileController.class);
    @Resource
    private CarService carService;
    @Resource
    private TemperatureService temperatureService;

    /**
     * 导入车辆信息
     * @param file
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("importfileCar")
    public @ResponseBody
    String importfileCar(MultipartFile file,
                         HttpServletRequest request, HttpServletResponse response, HttpSession session, String science_id) throws IOException {
        log.info("车辆信息导入 start");
        SysUser sysUser = (SysUser) session.getAttribute("currentUser");
        String Msg3 = null;
        boolean b = false;
        //判断文件是否为空
        if (file == null) {
            Msg3 = "文件是为空！";
            request.getSession().setAttribute("Msg3", Msg3);
            return "views/car";
        }

        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）验证文件名是否合格
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0 && !WDWUtil.validateExcel(name)) {
            Msg3 = "文件格式不正确！请使用.xls或.xlsx后缀文档。";
            request.getSession().setAttribute("Msg3", Msg3);
            return "views/car";
        }

        //创建处理EXCEL
        ReadExcelParkingCar readExcelParkingCar = new ReadExcelParkingCar();
        //解析excel，获取客户信息集合。
        List<ParkingCar> parkingCarList = readExcelParkingCar.getExcelCarInfo(file);
        if (parkingCarList != null && !parkingCarList.toString().equals("[]") && parkingCarList.size() >= 1) {
            b = true;
        }

        if (b) {
            //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加）
            for (ParkingCar ts : parkingCarList) {
                //这里可以做添加数据库的功能
                ts.setDel_flag(1);
                ts.setScience_id(Integer.parseInt(science_id));
            }
            try {
                carService.insertCarBatch(parkingCarList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Msg3 = "批量导入EXCEL成功！";
            request.getSession().setAttribute("Msg3", Msg3);
        } else {
            Msg3 = "批量导入EXCEL失败！";
            request.getSession().setAttribute("Msg3", Msg3);
        }
        return Msg3;
    }

    /**
     * 导出车辆详情
     */
    @RequestMapping("exportCarExcelAll")
    public void exportCarExcelAll(HttpServletResponse response) {
        Map<String, Object> conditionMap = null;
        List<CarCustom> dataSet = null;
        LeadingOutExcel leadingOutExcel = null;    //工具类

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "临停车");
        map.put(2, "月租车");
        map.put(3, "员工车");
        map.put(4, "免费车");

        //配置信息
        String format = "yyyy-MM-dd HH:mm:ss";
        String format1 = "yyyy-MM-dd";
        String fileName = "车辆信息-" + DateUtil.formatDate(new Date(), format1);

        String title = "车辆信息";
        String[] rowName = {"日期", "车牌号", "类型","停车位编号", "进入时间", "离开时间" ,};
        try {
            dataSet = carService.getAllCar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < dataSet.size(); i++) {
            CarCustom sc = dataSet.get(i);
            objs = new Object[rowName.length];
            objs[0] = DateUtil.formatDate(sc.getDay(), format1);
            objs[1] = sc.getLicense_car();// 车牌号
            objs[2] = map.get(sc.getCar_type());// 类型
            objs[3] = sc.getPark_id();// 停车位编号
            //日期类型处理
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = sc.getEnter_time();
            String dateStr = "";
            if (date != null) {
                dateStr = df.format(date);
            }
            objs[4] = dateStr;// 进入时间
            Date date1 = sc.getEnter_time();
            String dateStr1 = "";
            if (date1 != null) {
                dateStr1 = df.format(date1);
            }
            objs[5] = dateStr1;// 离开时间

            dataList.add(objs);
        }
        leadingOutExcel = new LeadingOutExcel(fileName, title, rowName, dataList, response);
        try {
            leadingOutExcel.export();
        } catch (Exception e) {
            log.error("写入Excle出错！", e);
        }
    }

    /**
     * 导出车辆汇总
     */
    @RequestMapping("exportCarExcel")
    public void exportCarExcel(HttpServletResponse response) {
        Map<String, Object> conditionMap = null;
        List<CarCustom> dataSet = null;
        LeadingOutExcel leadingOutExcel = null;    //工具类
        String format = "yyyy-MM-dd";
        //配置信息
        String fileName = "停车场信息列表-" + DateUtil.formatDate(new Date(), format);
        String title = "停车场信息列表";
        String[] rowName = {"编号", "景区名称", "景区地址", "日期", "车辆最大承载量", "当前车流量"};
        try {
            dataSet = carService.getAllSpotCar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < dataSet.size(); i++) {
            CarCustom sc = dataSet.get(i);
            objs = new Object[rowName.length];
            objs[0] = sc.getCode();
            objs[1] = sc.getScenicname();
            objs[2] = sc.getAddress();
            //日期类型处理
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = sc.getDay();
            String dateStr = "";
            if (date != null) {
                dateStr = df.format(date);
            }
            objs[3] = dateStr;
            objs[4] = sc.getMax_car();
            objs[5] = sc.getCarCount();
            dataList.add(objs);
        }
        leadingOutExcel = new LeadingOutExcel(fileName, title, rowName, dataList, response);
        try {
            leadingOutExcel.export();
        } catch (Exception e) {
            log.error("写入Excle出错！", e);
        }
    }


    /**
     * 天气 导入
     */
    @RequestMapping("importfileWeather")
    public @ResponseBody
    String importfileWeather(MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        log.info("ClientController ..batchimport() start");
        SysUser sysUser = (SysUser) session.getAttribute("currentUser");
        String Msg = null;
        boolean b = false;
        //判断文件是否为空
        if (file == null) {
            Msg = "文件是为空！";
            request.getSession().setAttribute("Msg", Msg);
            return "views/car";
        }

        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）验证文件名是否合格
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0 && !WDWUtil.validateExcel(name)) {
            Msg = "文件格式不正确！请使用.xls或.xlsx后缀文档。";
            request.getSession().setAttribute("msg", Msg);
            return "views/car";
        }

        //创建处理EXCEL
        ReadExcelWeather readExcelWeather = new ReadExcelWeather();
        //解析excel，获取客户信息集合。
        List<DiTable> diTableList = readExcelWeather.getExcelWeatherInfo(file);
        System.out.println(diTableList.isEmpty());
        if (diTableList != null && !diTableList.toString().equals("[]") && diTableList.size() >= 1) {
            System.out.println("22222222222222");
            b = true;
        }

        if (b) {
            //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加）
            for (DiTable ts : diTableList) {
                //这里可以做添加数据库的功能
                ts.setDel_flag(1);
            }
            try {
                temperatureService.insertWeatherBatch(diTableList);
                System.out.println("插入！");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Msg = "批量导入EXCEL成功！";
            request.getSession().setAttribute("Msg", Msg);
        } else {
            Msg = "批量导入EXCEL失败！";
            request.getSession().setAttribute("Msg", Msg);
        }
        return "success";
    }

    /**
     * 导出详情
     */
    @RequestMapping("exportWeatherExcelAll")
    public void exportWeatherExcelAll(HttpServletResponse response) {
        Map<String, Object> conditionMap = null;
        List<DiCustom> dataSet = null;
        LeadingOutExcel leadingOutExcel = null;    //工具类

        //配置信息
        String fileName = "天气信息" + new Date();
        String format = "yyyy-MM-dd";
        String title = "天气信息";
        String[] rowName = {"景区编号", "景区名称", "景区地址", "日期", "小时", "温度", "相对湿度"};
        try {
            dataSet = temperatureService.getAllDI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < dataSet.size(); i++) {
            DiCustom sc = dataSet.get(i);
            objs = new Object[rowName.length];
            objs[0] = sc.getScenic_id();
            objs[1] = sc.getScenicname();
            objs[2] = sc.getAddress();
            //日期类型处理
            Date date = sc.getTime_date();
            String dateStr = "";
            if (date != null) {
                SimpleDateFormat df = new SimpleDateFormat(format);
                dateStr = df.format(date);
            }
            objs[3] = dateStr;
            objs[4] = sc.getTime_hour();
            objs[5] = sc.getCelsius();
            objs[6] = sc.getRelative_humidity();
            dataList.add(objs);
        }
        System.out.println("天气信息文件导出");
        leadingOutExcel = new LeadingOutExcel(fileName, title, rowName, dataList, response);
        try {
            leadingOutExcel.export();
        } catch (Exception e) {
            log.error("写入Excle出错！", e);
        }
    }

    /**
     * 导出汇总
     */
    @RequestMapping("exportWeatherExcel")
    public void exportWeatherExcel(HttpServletResponse response) {
        Map<String, Object> conditionMap = null;
        List<DiCustom> dataSet = null;
        LeadingOutExcel leadingOutExcel = null;    //工具类

        //配置信息
        String fileName = "天气汇总" + new Date();
        String format = "yyyy-MM-dd";
        String title = "天气汇总";
        String[] rowName = {"景区编号", "景区名称", "景区地址", "日期"};
        try {
            dataSet = temperatureService.getAllDI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < dataSet.size(); i++) {
            DiCustom sc = dataSet.get(i);
            objs = new Object[rowName.length];
            objs[0] = sc.getScenic_id();
            objs[1] = sc.getScenicname();
            objs[2] = sc.getAddress();
            //日期类型处理
            Date date = sc.getTime_date();
            String dateStr = "";
            if (date != null) {
                SimpleDateFormat df = new SimpleDateFormat(format);
                dateStr = df.format(date);
            }
            objs[3] = dateStr;
            dataList.add(objs);
        }
        System.out.println("天气信息文件导出");
        leadingOutExcel = new LeadingOutExcel(fileName, title, rowName, dataList, response);
        try {
            leadingOutExcel.export();
        } catch (Exception e) {
            log.error("写入Excle出错！", e);
        }
    }

}
