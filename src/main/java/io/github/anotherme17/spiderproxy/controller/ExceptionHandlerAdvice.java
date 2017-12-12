package io.github.anotherme17.spiderproxy.controller;

import io.github.anotherme17.spiderproxy.dto.HttpResult;
import io.github.anotherme17.spiderproxy.exception.ScheduleException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lirenhao
 * date: 2017/12/12 上午11:24
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ExceptionHandler(ScheduleException.class)
    public HttpResult handleScheduleException(HttpServletRequest req, ScheduleException e) {
        return HttpResult.of().setCode(1).setMsg(e.getMessage());
    }
}
