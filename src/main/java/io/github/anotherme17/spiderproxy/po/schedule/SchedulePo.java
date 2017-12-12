package io.github.anotherme17.spiderproxy.po.schedule;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author lirenhao
 * date: 2017/12/11 上午11:46
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class SchedulePo {

    private long id;
    private String scheduleName;
    private String cron;
    private Boolean enable;

    private Date gmtCreate;
    private Date gmtModify;
}
