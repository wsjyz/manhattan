package com.manhattan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lk.zh on 2014/7/22.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @RequestMapping("payment")
    public ModelAndView toPay() {
        ModelAndView view = new ModelAndView();
        view.setViewName("payment");
        return view;
    }

}
