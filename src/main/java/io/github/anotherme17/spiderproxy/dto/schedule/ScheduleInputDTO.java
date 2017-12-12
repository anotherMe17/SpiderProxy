package io.github.anotherme17.spiderproxy.dto.schedule;

import com.google.common.base.Converter;
import io.github.anotherme17.spiderproxy.po.schedule.SchedulePo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author lirenhao
 * date: 2017/12/11 下午8:42
 */
@Data
public class ScheduleInputDTO {

    private String scheduleName;
    private String cron;
    private Boolean enable;

    public SchedulePo convert2Schedule() {
        ScheduleInputDTOConvert scheduleInputDTOConvert = new ScheduleInputDTOConvert();
        return scheduleInputDTOConvert.doForward(this);
    }

    public ScheduleInputDTO convertFor(SchedulePo po) {
        ScheduleInputDTOConvert scheduleInputDTOConvert = new ScheduleInputDTOConvert();
        return scheduleInputDTOConvert.reverse().convert(po);
    }

    private static class ScheduleInputDTOConvert extends Converter<ScheduleInputDTO, SchedulePo> {

        @Override
        protected SchedulePo doForward(ScheduleInputDTO scheduleInputDTO) {
            SchedulePo po = SchedulePo.of();
            BeanUtils.copyProperties(scheduleInputDTO, po);
            return po;
        }

        @Override
        protected ScheduleInputDTO doBackward(SchedulePo schedulePo) {
            throw new AssertionError("不支持逆向转化方法!");
        }
    }
}
