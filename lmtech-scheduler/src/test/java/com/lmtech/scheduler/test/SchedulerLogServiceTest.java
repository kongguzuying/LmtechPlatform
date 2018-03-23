/*
package com.lmtech.scheduler.test;

import com.lmtech.scheduler.Application;
import com.lmtech.scheduler.model.SchedulerLog;
import com.lmtech.scheduler.service.SchedulerLogService;
import com.lmtech.util.IdWorkerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class SchedulerLogServiceTest {

    @Autowired
    private SchedulerLogService schedulerLogService;


    @Test
    public void testAdd() {
        SchedulerLog log = new SchedulerLog();
        log.setId(IdWorkerUtil.generateStringId());
        log.setJobName("test");
        schedulerLogService.add(log);
    }
}
*/
