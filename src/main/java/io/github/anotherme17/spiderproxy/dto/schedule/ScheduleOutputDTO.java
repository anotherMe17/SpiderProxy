package io.github.anotherme17.spiderproxy.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Converter;
import io.github.anotherme17.spiderproxy.po.schedule.SchedulePo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author lirenhao
 * date: 2017/12/11 下午8:59
 */
@Data
public class ScheduleOutputDTO {

    private String scheduleName;
    private String cron;
    private Boolean enable;


    public SchedulePo converter2Schedule() {
        ScheduleOutputConvert scheduleOutputConvert = new ScheduleOutputConvert();
        return scheduleOutputConvert.doForward(this);
    }

    public ScheduleOutputDTO convertFor(SchedulePo po) {
        ScheduleOutputConvert scheduleOutputConvert = new ScheduleOutputConvert();
        return scheduleOutputConvert.doBackward(po);
    }

    private static class ScheduleOutputConvert extends Converter<ScheduleOutputDTO, SchedulePo> {

        @Override
        protected SchedulePo doForward(ScheduleOutputDTO scheduleOutputDTO) {
            throw new AssertionError("不支持正向转化方法!");
        }

        @Override
        protected ScheduleOutputDTO doBackward(SchedulePo schedulePo) {
            ScheduleOutputDTO scheduleOutputDTO = new ScheduleOutputDTO();
            BeanUtils.copyProperties(schedulePo, scheduleOutputDTO);
            return scheduleOutputDTO;
        }
    }
}
