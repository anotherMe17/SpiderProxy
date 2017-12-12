package io.github.anotherme17.spiderproxy.service.schedule;

import io.github.anotherme17.spiderproxy.dao.schedule.ScheduleDao;
import io.github.anotherme17.spiderproxy.dto.schedule.ScheduleOutputDTO;
import io.github.anotherme17.spiderproxy.exception.ScheduleException;
import io.github.anotherme17.spiderproxy.po.schedule.SchedulePo;
import io.github.anotherme17.spiderproxy.service.SpringBeanService;
import io.github.anotherme17.spiderproxy.task.base.BaseTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * @author lirenhao
 * date: 2017/12/11 上午11:10
 */
@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    private static Map<Long, ScheduledFuture<?>> mScheduleMap = new HashMap<>();

    public void initSchedule() {
        log.info("初始化定时任务");

        for (SchedulePo po : scheduleDao.getAllSchedule()) {
            BaseTask task = SpringBeanService.getBean(po.getScheduleName(), BaseTask.class);
            if (task == null)
                continue;

            addSchedule(po.getId(), task, new CronTrigger(po.getCron()));
        }
    }

    /**
     * 添加定时任务
     */
    public void addSchedule(SchedulePo po) throws ScheduleException {
        try {
            BaseTask task = SpringBeanService.getBean(po.getScheduleName(), BaseTask.class);

            Date nowDate = new Date();
            po.setGmtCreate(nowDate);
            po.setGmtModify(nowDate);

            scheduleDao.insertSchedule(po);

            addSchedule(po.getId(), task, new CronTrigger(po.getCron()));
        } catch (BeansException e) {
            throw new ScheduleException("未找到task");
        }
    }

    /**
     * 删除定时任务
     */
    public void delSchedule(Long id) throws ScheduleException {
        try {
            scheduleDao.deleteSchedule(id, null);
            removeSchedule(id);
        } catch (Exception e) {
            throw new ScheduleException("删除task失败");
        }
    }

    /**
     * 修改定时任务
     */
    public void updateSchedule(Long id, SchedulePo po) throws ScheduleException {
        try {
            BaseTask task = SpringBeanService.getBean(po.getScheduleName(), BaseTask.class);

            SchedulePo old = scheduleDao.getById(id);

            if (old == null)
                throw new ScheduleException("task不存在");


            po.setId(id);
            po.setGmtCreate(old.getGmtCreate());
            po.setGmtModify(new Date());
            scheduleDao.updateSchedule(po);

            removeSchedule(id);

            addSchedule(po.getId(), task, new CronTrigger(po.getCron()));
        } catch (BeansException e) {
            throw new ScheduleException("修改task失败");
        }
    }

    /**
     * 获取所有的task
     */
    public List<ScheduleOutputDTO> getAllSchedule() {
        List<SchedulePo> schedules = scheduleDao.getAllSchedule();

        List<ScheduleOutputDTO> outputDTOS = schedules
                .stream()
                .map(schedulePo -> new ScheduleOutputDTO().convertFor(schedulePo))
                .collect(Collectors.toList());

        return outputDTOS;
    }

    private void addSchedule(Long id, Runnable task, Trigger trigger) {
        ThreadPoolTaskScheduler scheduler = SpringBeanService
                .getBean("taskScheduler", ThreadPoolTaskScheduler.class);
        mScheduleMap.put(id, scheduler.schedule(task, trigger));
    }

    private boolean removeSchedule(Long id) {
        if (!mScheduleMap.containsKey(id))
            return false;

        ScheduledFuture<?> scheduledFuture = mScheduleMap.get(id);
        if (!scheduledFuture.isCancelled())
            scheduledFuture.cancel(true);

        return true;
    }
}
