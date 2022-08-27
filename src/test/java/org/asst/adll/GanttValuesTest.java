package org.asst.adll;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.asst.po.addl.gantt.GanttValue;
import org.asst.util.JacksonUtils;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author xiangqian
 * @date 16:26 2022/08/27
 */
@Slf4j
public class GanttValuesTest {

    @Test
    public void deserialize() throws IOException {
        String json = " {\"from\":1661529600000,\"to\":1661523600000,\"label\":\"标签\",\"desc\":\"描述\",\"customClass\":1}";
        GanttValue ganttValues = JacksonUtils.toObject(json, GanttValue.class);
        log.debug("{}", ganttValues);
    }

    @Test
    public void serialized() throws IOException {
        GanttValue ganttValues = new CustomGanttValues();
        ganttValues.setFrom(LocalDate.now());
        ganttValues.setTo(LocalDate.now());
        ganttValues.setLabel("标签");
        ganttValues.setDesc("描述");
        ganttValues.setCustomClass(1);
        String json = JacksonUtils.toJson(ganttValues);
        log.debug("{}", json);
    }

    @Data
    @JsonSerialize
    @JsonDeserialize
    public static class CustomGanttValues extends GanttValue {
    }

}
