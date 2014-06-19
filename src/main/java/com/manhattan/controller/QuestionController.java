package com.manhattan.controller;

import com.manhattan.domain.Question;
import com.manhattan.service.QuestionService;
import com.manhattan.util.FastJson;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dam on 14-6-19.
 */
@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController{

    @Autowired
    private QuestionService questionService;
    /**
     * 提问
     * @param question
     * @return
     */
    @RequestMapping(value = "/askQuestion")
    public
    @ResponseBody
    Boolean askQuestion(@FastJson Question question,HttpServletResponse response) {
        Question question1=questionService.saveQuestion(question);
        if (question1 == null || StringUtils.isEmpty(question1.getQuestionId())) {
            setResponse("保存提问失败", response);
            return false;
        }
        return true;
    }

    /**
     * 回答问题
     * @param question
     * @return
     */
    @RequestMapping(value = "/answerQuestion")
    public
    @ResponseBody
    Boolean answerQuestion(@FastJson Question question,HttpServletResponse response) {
        Question question1=questionService.saveQuestion(question);
        if (question1 == null || StringUtils.isEmpty(question1.getQuestionId())) {
            setResponse("保存提问失败", response);
            return false;
        }
        return true;
    }

    /**
     * 删除问题
     * @param questionId
     */
    @RequestMapping(value = "/deleteQuestion/{questionId}")
    @ResponseBody
    public boolean deleteQuestion(@PathVariable String questionId) {
        boolean result = true;
        try{
            questionService.deleteQuestion(questionId);
        }catch(Exception e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取我的问题
     *
     * @param userId
     * @param openPage
     * @return
     */
    @RequestMapping(value = "/myQuestions/{userId}")
    public
    @ResponseBody
    OpenPage<Question> myQuestions(@PathVariable String userId,
                                   @RequestBody OpenPage<Question> openPage) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page result = questionService.findQuestionByPage(userId,pageAble);
        return PageConvert.convert(result);
    }

    /**
     * 获取我要回答列表
     *
     * @param userId
     * @param openPage
     * @param type 指定回答(ASSIGN)已回答(ANSWERED)未回答(UNANSWER)
     * @return page
     */
    @RequestMapping(value = "/needAnswerList/{userId}/{type}")
    @ResponseBody
    public OpenPage<Question> needAnswerList(@PathVariable String userId,
                                      @RequestBody OpenPage<Question> openPage,
                                      @PathVariable String type) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page result = questionService.findQuestionByPage(userId,type,pageAble);
        return PageConvert.convert(result);
    }
}
