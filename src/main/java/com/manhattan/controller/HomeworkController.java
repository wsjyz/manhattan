package com.manhattan.controller;

import com.manhattan.domain.HomeWork;
import com.manhattan.domain.HomeworkSubmit;
import com.manhattan.domain.Information;
import com.manhattan.service.HomeWorkService;
import com.manhattan.service.HomeworkSubmitService;
import com.manhattan.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by dam on 14-6-18.
 */
@Controller
@RequestMapping("/homeWork")
public class HomeworkController extends BaseController{
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private HomeworkSubmitService homeworkSubmitService;
    @Autowired
    private ConfigurationFile confBean;
    /**
     * 获取指定学生的作业列表
     * @return
     */
    @RequestMapping(value = "/getHomeworksByUser/{userId}")
    @ResponseBody
    public OpenPage<HomeWork> getHomeworksByUser(@RequestBody OpenPage<Information> openPage,
                                                 @PathVariable String userId,HttpServletResponse response) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<HomeWork> page = homeWorkService.getHomeworksByUser(pageAble,userId);
        return PageConvert.convert(page);
    }

    /**
     * 获取指定教师的作业列表
     * @return
     */
    @RequestMapping(value = "/getHomeworksByTeacher/{teacherId}")
    @ResponseBody
    public OpenPage<HomeWork> getHomeworksByTeacher(@RequestBody OpenPage<Information> openPage,
                                                    @PathVariable String teacherId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<HomeWork> page = homeWorkService.getHomeworksByTeacher(pageAble,teacherId);
        return PageConvert.convert(page);
    }

    /**
     * 发布一个新的作业
     * @param homeWork
     */
    @RequestMapping(value = "/postHomeWork")
    @ResponseBody
    public HomeWork postHomeWork(@FastJson HomeWork homeWork,HttpServletResponse response) {
        HomeWork homeWork1=homeWorkService.post(homeWork);
        if (homeWork1==null) {
            setResponse("作业发布失败", response);
        }
        return homeWork1;
    }

    /**
     * 提交一个新的作业
     * @param homeworkSubmit
     */
    @RequestMapping(value = "/submitHomeWork")
    @ResponseBody
    public HomeworkSubmit submitHomeWork(MultipartHttpServletRequest request,HttpServletResponse response){

        String path = confBean.getFileUploadPath()+request.getParameter("homeworkId");

        MultipartFile file = request.getFile("homeworkFile");
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
        File targetFile = new File(path, UUIDGen.genShortPK()+fileSuffix);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HomeworkSubmit hs = new HomeworkSubmit();
        hs.setUserId(request.getParameter("userId"));
        hs.setHomeworkId(request.getParameter("homeworkId"));
        hs.setHomeworkContent(request.getParameter("homeworkContent"));
        hs.setOriginalFileName(originalFilename);

        HomeworkSubmit homeworkSubmit1=homeworkSubmitService.submit(hs);
        if (homeworkSubmit1==null) {
            setResponse("作业提交失败", response);
        }
        return homeworkSubmit1;
    }


}
