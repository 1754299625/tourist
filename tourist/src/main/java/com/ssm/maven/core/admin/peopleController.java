package com.ssm.maven.core.admin;


import com.ssm.maven.core.entity.*;
import com.ssm.maven.core.service.ScenicService;
import com.ssm.maven.core.service.TouristService;
import com.ssm.maven.core.util.DateUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/people")
@Controller
public class peopleController {

    @Resource
    private Tourist tourist;
    @Resource
    private TouristCustom touristCustom;
    @Resource
    private ScenicspotCustom scenicspotCustom;
    @Resource
    private Scenicspot scenicspot;
    @Resource
    private ScenicService scenicService;
    @Resource
    private TouristService touristService;

    private static final Logger log = Logger.getLogger(peopleController.class);// 日志文件

    @RequestMapping("getScenicpeopleAll")
    public @ResponseBody
    PageBean<Scenicspot> getPeopleAll(Integer pageIndex, Integer pageSize, String name) throws Exception {
        //System.out.println("页数"+pageIndex+pageSize);
        List list = scenicService.getScenicspotAndDay(pageIndex, pageSize);
        PageBean<Scenicspot> pb = new PageBean<Scenicspot>(list);
        //System.out.println(name);
        //System.out.println(pb.getCount());
        return pb;
    }

    @RequestMapping("getpeopleMenu")
    public ModelAndView getpeopleMenu(ModelMap model) throws Exception {
        // System.out.println("进入客流");
        ModelAndView mv = new ModelAndView();
        List<Scenicspot> list = scenicService.getScenicspotAll(scenicspot);
        // System.out.println(list.isEmpty());
        model.put("list", list);
        mv.setViewName("views/people");
        return mv;
    }

    /**
     * 游客信息修改页面，游客信息获取
     * @param model
     * @param tourist_code
     * @return
     * @throws Exception
     */
    @RequestMapping("getTouristedit")
    public ModelAndView getTouristedit(ModelMap model, String tourist_code) throws Exception {
        ModelAndView mv = new ModelAndView();
        TouristCustom touristCustom = new TouristCustom();
        touristCustom.setTourist_code(tourist_code);
        List<TouristCustom> list = touristService.searchTouristInfor(1, 1, touristCustom);

        if (!list.isEmpty()) {
            TouristCustom tourist = list.get(0);
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String enter_time = simpleDateFormat.format(tourist.getEnter_time());
                String leave_time = simpleDateFormat.format(tourist.getLeave_time());
                model.put("enter_time", enter_time);
                model.put("leave_time", leave_time);
                model.put("tourist", tourist);
            }catch (Exception e){
                e.printStackTrace();
            }


//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            simpleDateFormat.format(list.get(0).getEnter_time());
//            String leave_time = simpleDateFormat.format(list.get(0).getLeave_time());

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String enter_time = simpleDateFormat.format(list.get(0).getEnter_time());
//            String leave_time = simpleDateFormat.format(list.get(0).getLeave_time());
//            model.put("tourist_type", list.get(0).getTourist_type());
//            model.put("enter_time", enter_time);
//            model.put("leave_time", leave_time);
//            model.put("tourist_code", tourist_code);
        }
        mv.setViewName("views/tourist/tourist_edit");
        return mv;
    }

    /**
     * 游客信息修改
     * @param tourist_code
     * @param enter_time
     * @param leave_time
     * @param tourist_type
     * @return
     */
    @RequestMapping("updateByTouristCode")
    public @ResponseBody
    String updateByTouristCode(String tourist_code, String enter_time, String leave_time, Integer tourist_type, Integer sex, Integer age, String regione) throws Exception {
//        System.out.println(tourist_code + "进入更新！" + tourist_type);
        Tourist tourist = new TouristCustom();
        String msg = "更新成功！";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat s2 = new SimpleDateFormat("HH");
            tourist.setTourist_code(tourist_code);
            tourist.setTourist_type(tourist_type);
            tourist.setSex(sex);
            tourist.setAge(age);
            tourist.setRegione(regione);
//            tourist.setTime_hour(Integer.parseInt(s2.format(enter_time)));
            tourist.setEnter_time(simpleDateFormat.parse(enter_time));
            tourist.setLeave_time(simpleDateFormat.parse(leave_time));
            touristService.updateTouristInfor(tourist);
        } catch (ParseException e) {
            e.printStackTrace();
            msg = "更新失败!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "更新失败!";
        }
        return msg;
    }



    @RequestMapping("getpeopleInfor")
    public @ResponseBody
    PageBean<ScenicspotCustom> getpeopleInfor(Integer pageIndex, Integer pageSize, String code, String startday, String endday) throws Exception {
        //System.out.println("页数"+pageIndex+pageSize);
//        System.out.println(code + " " + startday + " " + endday);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date a1 = null;
        Date a2 = null;
        if (startday != null) {
            a1 = simpleDateFormat.parse(startday);
        }
        if (endday != null) {
            a2 = simpleDateFormat.parse(endday);
        }
        scenicspotCustom.setCode(code);
        scenicspotCustom.setEnter_day(a1);
        scenicspotCustom.setEnd_day(a2);
        List<ScenicspotCustom> list = scenicService.getpeopleInfor(pageIndex, pageSize, scenicspotCustom);
        PageBean<ScenicspotCustom> pb = new PageBean<ScenicspotCustom>(list);
        //System.out.println(code);
        //System.out.println(pb.getCount());
        return pb;
    }


    @RequestMapping("deleteTouristByCode")
    public @ResponseBody
    String deleteScenicByCode(String code, String enter_day) {
        String msg = "删除成功！";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date a1 = simpleDateFormat.parse(enter_day);
            scenicspotCustom.setCode(code);
            scenicspotCustom.setEnter_day(a1);
            touristService.deleteTouristByCode(scenicspotCustom);

        } catch (ParseException e) {
            e.printStackTrace();
            msg = "删除失败!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "删除失败!";
        }
        return msg;
    }

    @RequestMapping("deleteByTouristCode")
    public @ResponseBody
    String deleteByTouristCode(String tourist_code) {
//        System.out.println(tourist_code + "进入删除！");
        String msg = "删除成功！";
        try {
            TouristCustom touristCustom = new TouristCustom();
            touristCustom.setTourist_code(tourist_code);
            touristService.deleteByTouristCode(touristCustom);
        } catch (ParseException e) {
            e.printStackTrace();
            msg = "删除失败!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "删除失败!";
        }
        return msg;
    }

    /**
     * 客流详情
     * @param model
     * @param code
     * @param day
     * @return
     * @throws Exception
     */
    @RequestMapping("getTouristInfor")
    public ModelAndView getTouristInfor(ModelMap model, String code, String day) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date a1 = simpleDateFormat.parse(day);
        TouristCustom touristCustom = new TouristCustom();
        touristCustom.setCode(code);
        touristCustom.setEnter_day(a1);
        List<TouristCustom> list = touristService.getTouristList(touristCustom);
        model.put("list", list);
        model.put("code", code);
        model.put("day", day);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/tourist/touristInfor");
        return mv;
    }

    /**
     * 查询某景区某天的游客信息
     * @param pageIndex
     * @param pageSize
     * @param code
     * @param day
     * @return
     * @throws Exception
     */
    @RequestMapping("getAllTouristInfor")
    public @ResponseBody
    PageBean<TouristCustom> getTouristInfor(Integer pageIndex, Integer pageSize, String code, String day) throws Exception {
        //System.out.println("页数"+pageIndex+pageSize);
        TouristCustom touristCustom = new TouristCustom();
//        System.out.println("日期：" + day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date a1 = simpleDateFormat.parse(day);
        touristCustom.setCode(code);
        touristCustom.setEnter_day(a1);
        List<TouristCustom> list = touristService.getTouristList(pageIndex, pageSize, touristCustom);
        PageBean<TouristCustom> pb = new PageBean<TouristCustom>(list);
        //System.out.println(code);
        //System.out.println(pb.getCount());
        return pb;
    }

    /**
     * 游客列表检索
     * @param pageIndex
     * @param pageSize
     * @param enter_time
     * @param leave_time
     * @param science_id
     * @param enter_day
     * @return
     * @throws Exception
     */
    @RequestMapping("searchTouristInforTime")
    public @ResponseBody
    PageBean<TouristCustom> searchTouristInforTime(Integer pageIndex, Integer pageSize, String enter_time,
                                                   String leave_time, Integer science_id, String enter_day) throws Exception {
        log.info("进入游客信息检索界面");
        TouristCustom touristCustom = new TouristCustom();
        touristCustom.setScience_id(science_id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (enter_time != null) {
            Date a1 = simpleDateFormat.parse(enter_time);
            touristCustom.setEnter_time(a1);
        }
        if (leave_time != null) {
            Date a2 = simpleDateFormat.parse(leave_time);
            touristCustom.setLeave_time(a2);
        }
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        if (enter_day != null && enter_time == null && leave_time == null) {
            Date a3 = s2.parse(enter_day);
            touristCustom.setEnter_day(a3);
        }
        List<TouristCustom> list = touristService.searchTouristInfor(pageIndex, pageSize, touristCustom);
        PageBean<TouristCustom> pb = new PageBean<TouristCustom>(list);
        return pb;
    }

    @RequestMapping("searchTouristInforAll")
    public @ResponseBody
    PageBean<TouristCustom> searchTouristInforAll(Integer pageIndex, Integer pageSize, Integer science_id, String enter_day) throws Exception {
        //System.out.println("页数"+pageIndex+pageSize);
        TouristCustom touristCustom = new TouristCustom();
        touristCustom.setScience_id(science_id);
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        if (enter_day != null) {
            Date a3 = s2.parse(enter_day);
            touristCustom.setEnter_day(a3);
        }

        List<TouristCustom> list = touristService.searchTouristInfor(pageIndex, pageSize, touristCustom);
//        for (TouristCustom tc : list) {
//            System.out.println(tc.toString());
//        }
        PageBean<TouristCustom> pb = new PageBean<TouristCustom>(list);
        return pb;
    }

    @RequestMapping("searchTouristInfor")
    public @ResponseBody
    PageBean<TouristCustom> searchTouristInfor(Integer pageIndex, Integer pageSize, Integer science_id, String tourist_code) throws Exception {
        //System.out.println("页数"+pageIndex+pageSize);
        TouristCustom touristCustom = new TouristCustom();
        touristCustom.setScience_id(science_id);
        touristCustom.setTourist_code(tourist_code);
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        List<TouristCustom> list = touristService.searchTouristInfor(pageIndex, pageSize, touristCustom);
//        for (TouristCustom tc : list) {
//            System.out.println(tc.toString());
//        }
        PageBean<TouristCustom> pb = new PageBean<TouristCustom>(list);
        return pb;
    }



}
