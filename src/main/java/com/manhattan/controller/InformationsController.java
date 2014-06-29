package com.manhattan.controller;

import com.manhattan.domain.Information;
import com.manhattan.service.InformationService;
import com.manhattan.util.FastJson;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dam on 14-6-19.
 */
@Controller
@RequestMapping("/info")
public class InformationsController {
    @Autowired
    private InformationService informationService;
    /**
     * 获取资讯列表
     * @return
     */
    @RequestMapping(value = "/getInformations")
    @ResponseBody
    public OpenPage<Information> getInformations(@FastJson OpenPage<Information> openPage,HttpServletResponse response) {
        Pageable pageAble= new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Information> page=informationService.getInformations(pageAble);
        return PageConvert.convert(page);
    }

    /**
     *根据id查找
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/findInfoById")
    @ResponseBody
    public Information findInfoById(@RequestParam String infoId){
        Information information = informationService.findInformationByInformationId(infoId);
        return information;
    }

    /**
     * 查看资讯详情
     * @return
     */
    @RequestMapping(value = "/viewDetail")
    public ModelAndView viewDetail(@RequestParam String informationId) {
        ModelAndView mv = new ModelAndView();
        Information information = informationService.findInformationByInformationId(informationId);
        mv.addObject("information", information);
        mv.setViewName("views/info/infoDetail");
        return mv;
    }
}
