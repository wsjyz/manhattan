package com.manhattan.controller;

import com.manhattan.domain.Information;
import com.manhattan.domain.Question;
import com.manhattan.domain.User;
import com.manhattan.service.InformationService;
import com.manhattan.service.PlaceService;
import com.manhattan.service.QuestionService;
import com.manhattan.service.UserService;
import com.manhattan.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private ConfigurationFile confBean;

    @RequestMapping(value={"/","/index"})
	public ModelAndView index(HttpSession session) {
        ModelAndView view = new ModelAndView();
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId!=null) {
            User user = userService.findUserById(userId.toString());
            view.addObject("user", user);
        }
        view.setViewName("index");
        return view;
    }

    @RequestMapping(value={"/admin","/admin/login"})
    public ModelAndView adminLogin(HttpSession session) {
        ModelAndView view = new ModelAndView();
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId!=null) {
            User user = userService.findUserById(userId.toString());
            view.addObject("user", user);
            view.setViewName("views/admin/manage");
            return view;
        }
        view.setViewName("views/admin/login");
        return view;
    }

    @RequestMapping("/admin/adminlogin")
    public @ResponseBody JsonResult adminlogin(@ModelAttribute User user,HttpSession session) {
        String passwordMd5= DigestUtils.md5Hex(user.getPassword());
        User user1=userService.findUserIdByFilter(user.getMobile(), passwordMd5);
        boolean flag=user1!= null & user1.getType().equals("ADMIN");
        if (flag) {
            session.setAttribute(MhtConstant.SEESION_USER_ID,user1.getUserId());
        }
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(flag);
        jsonResult.setData(user);
        return jsonResult;
    }

    @RequestMapping("/admin/manage")
    public ModelAndView admin(HttpSession session) {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/manage");
        return view;
    }

    @RequestMapping("/tologin")
    public ModelAndView tologin() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/user/login");
        return view;
    }

    @RequestMapping("/toregister")
    public ModelAndView toregister() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/user/register");
        return view;
    }

    @RequestMapping("/relogin")
    public ModelAndView relogin(HttpSession session) {
        ModelAndView view = new ModelAndView();
        session.removeAttribute(MhtConstant.SEESION_USER_ID);
        view.addObject("tologin", "true");
        view.setViewName("index");
        return view;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody JsonResult login(@RequestParam String mobile,@RequestParam String password,HttpSession session) {
        String passwordMd5= DigestUtils.md5Hex(password);
        User user=userService.findUserIdByFilter(mobile, passwordMd5);
        if (user != null) {
            session.setAttribute(MhtConstant.SEESION_USER_ID,user.getUserId());
        }
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
        jsonResult.setData(user);
        return jsonResult;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody JsonResult register(@ModelAttribute User user){
        String passwordMd5= DigestUtils.md5Hex(user.getPassword());
        user.setPassword(passwordMd5);
        user = userService.save(user);
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
        jsonResult.setData(user.getUserId());
        return jsonResult;
    }

    @RequestMapping(value = "/main")
    public ModelAndView main(@RequestParam(required = false) String userId) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            view.addObject("user", userService.load(userId));
        }
        Pageable pageAble = new PageRequest(0, 7);
        Page<Information> page = informationService.getInformations(pageAble);
        view.addObject("page", page);
        view.setViewName("views/main");
        return view;
    }

    @RequestMapping("/postCourse")
    public ModelAndView postCourse() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/teacher/postCourse");
        return view;
    }

    @RequestMapping("/place/list")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/place/list");
        return view;
    }

    @RequestMapping("/place/placeList")
    public ModelAndView placeList(@ModelAttribute OpenPage page) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        Page respage=placeService.listByPage(pageAble);
        view.addObject("page", PageConvert.convert(respage));
        view.setViewName("views/place/placeList");
        return view;
    }

    @RequestMapping("/appoint")
    public ModelAndView appoint() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/appoint/appoint");
        return view;
    }

    @RequestMapping("/question/questions")
    public ModelAndView question() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/question/questions");
        return view;
    }

    @RequestMapping("/question/questionList")
    public ModelAndView questionList(@ModelAttribute OpenPage page) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        Page respage=questionService.findQuestionByPage("",pageAble);
        view.addObject("page", PageConvert.convert(respage));
        view.setViewName("views/question/questionList");
        return view;
    }

    @RequestMapping("/question/myQuestion")
    public ModelAndView myQuestion(@ModelAttribute OpenPage page,HttpSession session) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId != null) {
            questionService.findQuestionByPage(userId.toString(),pageAble);
        }
        view.setViewName("views/question/myQuestion");
        return view;
    }

    @RequestMapping("/question/saveQuestion")
    public @ResponseBody String saveQuestion(MultipartHttpServletRequest request) {
        Question question = new Question();
        String userId = request.getParameter("userId");
        String questionTitle = request.getParameter("questionTitle");
        String questionContent = request.getParameter("questionContent");

        String path = request.getSession().getServletContext().getRealPath("upload");
        MultipartFile file = request.getFile("questionPic");
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.indexOf("."), originalFilename.length());
        path = path + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        String picName = UUID.randomUUID().toString().replace("-", "") + fileSuffix;
        File targetFile = new File(path, picName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String picUrl = path + picName;
        try {
            file.transferTo(targetFile);
            question.setQuestionPic(picUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        question.setUserId(userId);
        question.setQuestionTitle(questionTitle);
        question.setQuestionContent(questionContent);

        questionService.saveQuestion(question);
        return "success";
    }
}