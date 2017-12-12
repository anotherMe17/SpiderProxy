package io.github.anotherme17.spiderproxy.controller;

import io.github.anotherme17.spiderproxy.dto.HttpResult;
import io.github.anotherme17.spiderproxy.dto.schedule.ScheduleInputDTO;
import io.github.anotherme17.spiderproxy.dto.schedule.ScheduleOutputDTO;
import io.github.anotherme17.spiderproxy.exception.ScheduleException;
import io.github.anotherme17.spiderproxy.service.schedule.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/11 下午8:39
 */
@Slf4j
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpResult<String> addSchedule(@ModelAttribute ScheduleInputDTO scheduleInputDTO) throws ScheduleException {
        scheduleService.addSchedule(scheduleInputDTO.convert2Schedule());
        return HttpResult.<String>of().setCode(0).setMsg("ok").setDate("添加成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpResult<String> delSchedule(@PathVariable Long id) throws ScheduleException {
        scheduleService.delSchedule(id);
        return HttpResult.<String>of().setCode(0).setMsg("ok").setDate("删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpResult<String> updateSchedule(@PathVariable Long id, @ModelAttribute ScheduleInputDTO scheduleInputDTO) throws ScheduleException {
        scheduleService.updateSchedule(id, scheduleInputDTO.convert2Schedule());
        return HttpResult.<String>of().setCode(0).setMsg("ok").setDate("修改成功");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpResult<List<ScheduleOutputDTO>> getAllSchedule() {
        return HttpResult.<List<ScheduleOutputDTO>>of().setCode(0).setMsg("ok").setDate(scheduleService.getAllSchedule());
    }
}
