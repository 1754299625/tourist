package com.ssm.maven.core.admin;


import com.ssm.maven.core.entity.*;
import com.ssm.maven.core.service.CarService;
import com.ssm.maven.core.service.DI_TableService;
import com.ssm.maven.core.service.ScenicService;
import com.ssm.maven.core.service.TouristService;
import com.ssm.maven.core.util.DIUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Controller
@RequestMapping("Data")
public class DataanalysisController {

    @Resource
    private ScenicService scenicService;

    @Resource
    Scenicspot scenicspot;

    @Resource
    private DI_TableService di_tableService;

    @Resource
    private TouristService touristService;

    @Resource
    private CarService carService;



    @RequestMapping("getDITable")
    public ModelAndView getDITable(ModelMap model, String scenic_id, String enter_time, String scenic_name) throws Exception {
        System.out.println("进入页面");
        System.out.println("获得数据" + scenic_id + " " + enter_time + " " + scenic_name);
        ModelAndView mv = new ModelAndView();
        model.put("scenic_id", scenic_id);
        model.put("enter_day", enter_time);
        model.put("scenic_name", scenic_name);
        mv.setViewName("views/table/DITable");

        return mv;
    }

    /**
     * 实时客流承载量
     * @param model
     * @param scenic_id
     * @param enter_time
     * @param scenic_name
     * @return
     * @throws Exception
     */
    @RequestMapping("getHLCTable")
    @ResponseBody
    public ModelAndView getHLCTable(ModelMap model, String scenic_id, String enter_time, String scenic_name) throws Exception {
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_people = scenicspot.getMax_people();
        ModelAndView mv = new ModelAndView();
        model.put("scenic_id", scenic_id);
        model.put("enter_day", enter_time);
        model.put("scenic_name", scenic_name);
        model.put("max_people", max_people);
        mv.setViewName("views/table/HLCTable");
        return mv;
    }


    /**
     * 入园人数分析
     * @param model
     * @param scenic_id
     * @param type
     * @param scenic_name
     * @return
     * @throws Exception
     */
    @RequestMapping("getRETPTable")
    @ResponseBody
    public ModelAndView getRETPTable(ModelMap model, String scenic_id, String type, String scenic_name) throws Exception {
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_people = scenicspot.getMax_people();
        ModelAndView mv = new ModelAndView();
        model.put("scenic_id", scenic_id);
        model.put("type", type);
        model.put("scenic_name", scenic_name);
        model.put("max_people", max_people);
        mv.setViewName("views/table/RETPTable");
        return mv;
    }

    /**
     * 实时车流承载量
     * @param model
     * @param scenic_id
     * @param enter_time
     * @param scenic_name
     * @return
     * @throws Exception
     */
    @RequestMapping("getRTTFTable")
    @ResponseBody
    public ModelAndView getRTTFTable(ModelMap model, String scenic_id, String enter_time, String scenic_name) throws Exception {
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_car = scenicspot.getMax_car();
        ModelAndView mv = new ModelAndView();
        model.put("scenic_id", scenic_id);
        model.put("enter_day", enter_time);
        model.put("scenic_name", scenic_name);
        model.put("max_car", max_car);
        mv.setViewName("views/table/carHLCTable");
        return mv;
    }

    /**
     * 入园车辆数目分析
     * @param model
     * @param scenic_id
     * @param type
     * @param scenic_name
     * @return
     * @throws Exception
     */
    @RequestMapping("getVETPTable")
    @ResponseBody
    public ModelAndView getVETPTable(ModelMap model, String scenic_id, String type, String scenic_name) throws Exception {
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_car = scenicspot.getMax_car();
        ModelAndView mv = new ModelAndView();
        model.put("scenic_id", scenic_id);
        model.put("type", type);
        model.put("scenic_name", scenic_name);
        model.put("max_car", max_car);
        mv.setViewName("views/table/carRETPTable");
        return mv;
    }







    @RequestMapping(value = "getDiInfor")
    public @ResponseBody
    List<DiTable> getDi(String enter_time, String scenic_id) {
//        System.out.println("进入di" + enter_time + " " + scenic_id);
        List<DiTable> list = new ArrayList<>();
        DiTable diTable = new DiTable();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        try {
            if (enter_time != null) {
                d1 = simpleDateFormat.parse(enter_time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        diTable.setTime_date(d1);
        diTable.setScenic_id(Integer.valueOf(scenic_id));
        try {
            list = di_tableService.selectByScenic(diTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (DiTable d2 : list) {
            Double di = DIUtil.returnDI(d2.getCelsius(), d2.getRelative_humidity());
            d2.setDi(di);
        }
        return list;
    }

    /**
     * 实时客流量分析
     * @param enter_time
     * @param scenic_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getHLCInfor")
    public @ResponseBody
    List<HLCTable> getHLCInfor(String enter_time, String scenic_id) throws Exception {
        Date d1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<HLCTable> list = new ArrayList<>();
        List<Integer> TouristCountList = new ArrayList<>();
        TouristCustom touristCustom = new TouristCustom();

        // 获取景区最大客流承载量
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_people = scenicspot.getMax_people();

        // 设置查询条件
        try {
            d1 = simpleDateFormat.parse(enter_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        touristCustom.setScience_id(Integer.parseInt(scenic_id));// 设置 景区id 条件
        touristCustom.setEnter_day(d1);// 设置 日期 条件

        // 查询游客数目
        try {
            TouristCountList = touristService.searchHLCTouristInfor(touristCustom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(null == TouristCountList || TouristCountList.size() == 0)
            return null;

        // 遍历游客信息
        for(int i=0,j=8;i<13;i++,j++){
            Integer hf = TouristCountList.get(i);// 实时客流量
            HLCTable hlcTable = new HLCTable(hf, (float)hf/max_people, j);
            list.add(hlcTable);
        }

        return list;
    }


    /**
     * 入园人数分析
     * @param type
     * @param scenic_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getREPTInfor")
    public @ResponseBody
    List<HLCTable> getREPTInfor(String type, String scenic_id) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = "2017-08-10";
        Date d1 = null, nowDate = simpleDateFormat.parse(str);;
        List<HLCTable> list = new ArrayList<>();
        TouristCustom touristCustom = new TouristCustom();
        Calendar calendar = Calendar.getInstance();

        int index = 7;
        if(type.equals("2")){
            index = 15;
        }
        if(type.equals("3")){
            index = 30;
        }
        for(int i=index-1; i>=0;i--){
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) - i);//让日期减i
            touristCustom.setEnter_day(calendar.getTime());
            touristCustom.setScience_id(Integer.parseInt(scenic_id));
            HLCTable hlcTable = new HLCTable();
            hlcTable.setHf(touristService.getCount(touristCustom));// 人数
            hlcTable.setDay(simpleDateFormat.format(calendar.getTime()));
            list.add(hlcTable);
        }
        return list;
    }


    /**
     * 实时车流量分析
     * @param enter_time
     * @param scenic_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCarHLCInfor")
    public @ResponseBody
    List<HLCTable> getCarHLCInfor(String enter_time, String scenic_id) throws Exception {
        Date d1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<HLCTable> list = new ArrayList<>();
        List<Integer> carCountList = new ArrayList<>();
        CarCustom carCustom = new CarCustom();

        // 获取景区最大客流承载量
        Scenicspot scenicspot = scenicService.getScenicspotById(Integer.parseInt(scenic_id));
        int max_car = scenicspot.getMax_car();

        // 设置查询条件
        try {
            d1 = simpleDateFormat.parse(enter_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        carCustom.setScience_id(Integer.parseInt(scenic_id));// 设置 景区id 条件
        carCustom.setEnd_day(d1);// 设置 日期 条件

        // 查询车流量
        try {
            carCountList = carService.searchCarHLCInfor(carCustom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(null == carCountList || carCountList.size() == 0)
            return null;

        // 遍历车流量
        for(int i=0,j=8;i<13;i++,j++){
            Integer hf = carCountList.get(i);// 实时车流量
            HLCTable hlcTable = new HLCTable(hf, (float)hf/max_car, j);
            list.add(hlcTable);
        }

        return list;
    }



    /**
     * 入园车辆数分析
     * @param type
     * @param scenic_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCarREPTInfor")
    public @ResponseBody
    List<HLCTable> getCarREPTInfor(String type, String scenic_id) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = "2017-07-23";
        Date d1 = null, nowDate = simpleDateFormat.parse(str);;
        List<HLCTable> list = new ArrayList<>();
        CarCustom carCustom = new CarCustom();
        Calendar calendar = Calendar.getInstance();

        int index = 7;
        if(type.equals("2")){
            index = 15;
        }
        if(type.equals("3")){
            index = 30;
        }
        for(int i=index-1; i>=0;i--){
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) - i);//让日期减i
            carCustom.setEnd_day(calendar.getTime());
            carCustom.setScience_id(Integer.parseInt(scenic_id));
            HLCTable hlcTable = new HLCTable();
            hlcTable.setHf(carService.getCount(carCustom));// 人数
            hlcTable.setDay(simpleDateFormat.format(calendar.getTime()));
            list.add(hlcTable);
        }
        return list;
    }



    // 跳转到客流数据分析，从数据库menu中
    @RequestMapping("getDataanalysis")
    public ModelAndView getDataanalysis(Model model) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Scenicspot> list = scenicService.getScenicspotAll(scenicspot);
        model.addAttribute("list", list);
        mv.setViewName("views/Dataanalysis");
        return mv;
    }

    // 跳转到停车场数据分析，从数据库menu中
    @RequestMapping("getParkingDataanalysis")
    public ModelAndView getParkingDataanalysis(Model model) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Scenicspot> list = scenicService.getScenicspotAll(scenicspot);
        model.addAttribute("list", list);
        mv.setViewName("views/ParkingDataanalysis");
        return mv;
    }

    // 跳转到收入数据分析，从数据库menu中
    @RequestMapping("getRevenueDataanalysis")
    public ModelAndView getRevenueDataanalysis(Model model) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Scenicspot> list = scenicService.getScenicspotAll(scenicspot);
        model.addAttribute("list", list);
        mv.setViewName("views/RevenueDataanalysis");
        return mv;
    }

    // 跳转到游客数据分析，从数据库menu中
    @RequestMapping("getTouristDataanalysis")
    public ModelAndView getTouristDataanalysis(Model model) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Scenicspot> list = scenicService.getScenicspotAll(scenicspot);
        model.addAttribute("list", list);
        mv.setViewName("views/TouristDataanalysis");
        return mv;
    }


}
