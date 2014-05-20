package com.manhattan.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Created by lk.zh on 2014/5/20.
 */
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-config.xml"
})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testFindUserById() throws Exception {
        userService.findUserById("1");
    }

    @Test
    public void testFindUserByFilter() throws Exception {
        userService.findUserByFilter("1","000000");
    }
}
