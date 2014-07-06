package com.manhattan.controller;

import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.util.OpenPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherDetailService teacherDetailService;

    @RequestMapping("/list")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/teacher/list");
        return view;
    }

    @RequestMapping("/view")
    public ModelAndView view(@RequestParam String teacherId) {
        ModelAndView view = new ModelAndView();
        TeacherDetail teacherDetail = teacherDetailService.findTeacherDetail(teacherId);
        view.addObject("teacher", teacherDetail);
        view.setViewName("views/teacher/view");
        return view;
    }

    @RequestMapping("/teacherList")
    public ModelAndView teacherList(@ModelAttribute QueryParam queryParam) {
        ModelAndView view = new ModelAndView();
        OpenPage<TeacherDetail> page = new OpenPage<TeacherDetail>();
        page.setPageNo(1);
        page.setPageSize(10);
        page = teacherDetailService.findTeacherByPage(page,queryParam);
        view.addObject("teachers", page.getRows());
        view.setViewName("views/teacher/teacherList");
        return view;
    }
}
