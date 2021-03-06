package com.ssm.maven.core.admin;

import com.ssm.maven.core.entity.SysUser;
import com.ssm.maven.core.service.SysyUserService;
import com.ssm.maven.core.util.MD5Util;
import com.ssm.maven.core.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 1754299625@qq.com
 * @project_name ssm-maven
 * @date 2017-3-1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysyUserService sysyUserService;

    private static final Logger log = Logger.getLogger(UserController.class);// 日志文件


    /**
     * 登录
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(SysUser user, HttpServletRequest request, ModelMap model) {
        try {
            //System.out.println(user.getAccount());
            String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
            user.setPassword(MD5pwd);
        } catch (Exception e) {
            user.setPassword("");
        }
        SysUser resultUser = sysyUserService.login(user);
        log.info("request: user/login , user: " + user.toString());
        if (resultUser == null) {
            request.setAttribute("user", user);
            request.setAttribute("errorMsg", "请认真核对账号、密码！");
            return "login";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", resultUser);
            MDC.put("Account", user.getAccount());
            return "redirect:/main.jsp";
        }
    }


//    /**
//     * 修改密码
//     *
//     * @param user
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/modifyPassword")
//    public String modifyPassword(SysUser user, HttpServletResponse response) throws Exception {
//        String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
//        user.setPassword(MD5pwd);
//        int resultTotal = userService.updateUser(user);
//        JSONObject result = new JSONObject();
//        if (resultTotal > 0) {
//            result.put("success", true);
//        } else {
//            result.put("success", false);
//        }
//        log.info("request: user/modifyPassword , user: " + user.toString());
//        ResponseUtil.write(response, result);
//        return null;
//    }

    /**
     * 退出系统
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        log.info("request: user/logout");
        return "redirect:/login.jsp";
    }


//    /**
//     * 添加或修改管理员
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/save")
//    public String save(User user, HttpServletResponse response) throws Exception {
//        int resultTotal = 0;
//        String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
//        user.setPassword(MD5pwd);
//        if (user.getId() == null) {
//            resultTotal = userService.addUser(user);
//        } else {
//            resultTotal = userService.updateUser(user);
//        }
//        JSONObject result = new JSONObject();
//        if (resultTotal > 0) {
//            result.put("success", true);
//        } else {
//            result.put("success", false);
//        }
//        log.info("request: user/save , user: " + user.toString());
//        ResponseUtil.write(response, result);
//        return null;
//    }

//    /**
//     * 删除管理员
//     *
//     * @param ids
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/delete")
//    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
//        JSONObject result = new JSONObject();
//        String[] idsStr = ids.split(",");
//        for (int i = 0; i < idsStr.length; i++) {
//            userService.deleteUser(Integer.parseInt(idsStr[i]));
//        }
//        result.put("success", true);
//        log.info("request: user/delete , ids: " + ids);
//        ResponseUtil.write(response, result);
//        return null;
//    }

    /**
     * 用户信息查询
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ModelAndView getUserInfo(SysUser user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        user = (SysUser) session.getAttribute("currentUser");
        SysUser sysUser = sysyUserService.getUserInfo(user.getAccount());
        modelAndView.addObject("currentUser", sysUser);
        modelAndView.setViewName("views/person");
        System.out.println("你哈哈哈哈哈哈");
        return modelAndView;
    }

    /**
     * 用户信息修改
     */
    @RequestMapping("personInfoSubmit")
    public ModelAndView personInfoSUbmit(SysUser currentUser, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        sysyUserService.updateUserInfo(currentUser);
        session.setAttribute("currentUser", currentUser);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.setViewName("views/person");
        return modelAndView;
    }
}
