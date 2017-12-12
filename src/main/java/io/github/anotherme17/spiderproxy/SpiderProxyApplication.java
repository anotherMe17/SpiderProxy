package io.github.anotherme17.spiderproxy;

import io.github.anotherme17.spiderproxy.service.SpringBeanService;
import io.github.anotherme17.spiderproxy.service.schedule.ScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@ServletComponentScan
@SpringBootApplication
public class SpiderProxyApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpiderProxyApplication.class, args);

        //初始化定时器
        ScheduleService scheduleService = SpringBeanService.getBean("scheduleService", ScheduleService.class);
        scheduleService.initSchedule();
    }

}
