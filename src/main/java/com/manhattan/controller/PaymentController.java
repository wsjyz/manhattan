package com.manhattan.controller;

import com.alipay.util.UtilDate;
import com.manhattan.domain.Appointment;
import com.manhattan.domain.Wallet;
import com.manhattan.util.MhtConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lk.zh on 2014/7/22.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @RequestMapping("payment")
    public ModelAndView toPay(@ModelAttribute Appointment appointment,
                              @RequestParam String subject,
                              @RequestParam String money) {
        ModelAndView view = new ModelAndView();
        view.addObject("WIDseller_email", MhtConstant.ALIPAY_SELLER_EMAIL);
        view.addObject("WIDout_trade_no", UtilDate.getOrderNum());
        view.addObject("WIDsubject", subject);
        view.addObject("WIDtotal_fee", money);
        view.setViewName("payment");
        return view;
    }
    @RequestMapping("/toAlipayApi")
    public ModelAndView toAlipayApi() {
        ModelAndView view = new ModelAndView();
        view.setViewName("alipayapi");
        return view;
    }
    @RequestMapping("/toNotify")
    public ModelAndView toNotify() {
        ModelAndView view = new ModelAndView();
        view.setViewName("notify_url");
        return view;
    }

    @RequestMapping("/toCallbackUrl")
    public ModelAndView toCallbackUrl() {
        ModelAndView view = new ModelAndView();
        view.setViewName("call_back_url");
        return view;
    }
}
