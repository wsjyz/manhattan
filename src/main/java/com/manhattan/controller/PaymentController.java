package com.manhattan.controller;

import com.alipay.util.UtilDate;
import com.manhattan.domain.Appointment;
import com.manhattan.domain.Course;
import com.manhattan.domain.Wallet;
import com.manhattan.service.AppointmentService;
import com.manhattan.service.CourseService;
import com.manhattan.service.WalletService;
import com.manhattan.util.MhtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by lk.zh on 2014/7/22.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("payment")
    public ModelAndView toPay(@ModelAttribute Appointment appointment,
                              @RequestParam String subject,
                              @RequestParam(required = false) String money) {
        String payNo=UtilDate.getOrderNum();
        if (MhtConstant.USER_ACTION_LISTEN_COURSE.equals(appointment.getResourceType())) {
            money = "50";
        }else if(MhtConstant.USER_ACTION_APPOINTMENT_COURSE.equals(appointment.getResourceType())){
            String courseId=appointment.getResourceId();
            Course course=courseService.load(courseId);
            money = course.getExpense()+"";
        }

        appointment.setAppointmentTime(new Date());
        appointment.setPayment("ONLINE");
        appointment.setStatus(MhtConstant.DRAFT);
        appointment=appointmentService.save(appointment);

        Wallet wallet=new Wallet();
        wallet.setMoney(new BigDecimal(money));
        wallet.setSubject(subject);
        wallet.setUserId(appointment.getUserId());
        wallet.setPayStatus(MhtConstant.PAY_STATUS_DRAFT);
        wallet.setPayNo(payNo);
        wallet.setResourceId(appointment.getAppointmentId());
        wallet.setOptTime(new Timestamp((new Date()).getTime()));
        walletService.saveWallet(wallet);

        ModelAndView view = new ModelAndView();
        view.addObject("WIDseller_email", MhtConstant.ALIPAY_SELLER_EMAIL);
        view.addObject("WIDout_trade_no", payNo);
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

    @RequestMapping("/toMerchantUrl")
    public ModelAndView toMerchantUrl(@RequestParam String out_trade_no) {
        Wallet wallet = walletService.getWalletByPayNo(out_trade_no);
        ModelAndView view = new ModelAndView();
        view.addObject("WIDsubject", wallet.getSubject());
        view.addObject("WIDtotal_fee", wallet.getMoney());
        view.setViewName("payment");
        return view;
    }
}
