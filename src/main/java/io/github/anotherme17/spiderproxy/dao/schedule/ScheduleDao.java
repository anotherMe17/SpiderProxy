package io.github.anotherme17.spiderproxy.dao.schedule;

import io.github.anotherme17.spiderproxy.po.schedule.SchedulePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/11 下午5:59
 */
@Mapper
@Repository
public interface ScheduleDao {

    void insertSchedule(SchedulePo po);

    void deleteSchedule(@Param("id") Long id, @Param("scheduleName") String scheduleName);

    void updateSchedule(SchedulePo po);

    List<SchedulePo> getAllSchedule();

    SchedulePo getById(@Param("id") Long id);
}
