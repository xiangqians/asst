package org.asst.po.addl.gantt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.constant.GanttCustomClass;
import org.asst.util.DateUtils;
import org.asst.util.JacksonUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xiangqian
 * @date 15:33 2022/08/27
 */
@Data
@Deprecated
@ApiModel(description = "甘特图值集合")
//@JsonSerialize(using = GanttValue.GanttValuesSerializer.class)
//@JsonDeserialize(using = GanttValue.GanttValuesDeserializer.class)
public class GanttContent {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "甘特图开始时间不能为空")
    @ApiModelProperty(value = "甘特图开始时间", required = true)
    private LocalDate from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "甘特图结束时间不能为空")
    @ApiModelProperty(value = "甘特图结束时间", required = true)
    private LocalDate to;

    @ApiModelProperty("甘特图标签")
    private String label;

    @ApiModelProperty("甘特图描述")
    private String desc;

    /**
     * {@link GanttCustomClass}
     */
    @ApiModelProperty("甘特图数据条的颜色")
    private Integer customClass;

    public static String serialize(List<GanttContent> values) {
        if (Objects.isNull(values)) {
            return null;
        }
        try {
            return JacksonUtils.toJson(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<GanttContent> deserialize(String input) {
        if (Objects.isNull(input)) {
            return null;
        }
        try {
            return JacksonUtils.toObject(input, new TypeReference<List<GanttContent>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class GanttValuesSerializer extends JsonSerializer<GanttContent> {

        @Override
        public void serialize(GanttContent ganttValues, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("from", DateUtils.dateToTimestamp(ganttValues.getFrom()));
            jsonGenerator.writeNumberField("to", DateUtils.dateToTimestamp(ganttValues.getTo()));
            jsonGenerator.writeStringField("label", ganttValues.getLabel());
            jsonGenerator.writeStringField("desc", ganttValues.getDesc());
            jsonGenerator.writeNumberField("customClass", ganttValues.getCustomClass());
            jsonGenerator.writeEndObject();
        }

    }

    public static class GanttValuesDeserializer extends JsonDeserializer<GanttContent> {
        @Override
        public GanttContent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
            GanttContent ganttValues = new GanttContent();

            Function<TreeNode, LocalDate> treeNodeToDateFunction = tn -> {
                if (Objects.isNull(tn)) {
                    return null;
                }

                if (tn instanceof LongNode) {
                    LongNode longNode = (LongNode) tn;
                    return DateUtils.timestampToLocalDate(longNode.longValue());
                }

                return null;
            };

            Function<TreeNode, String> treeNodeToTextFunction = tn -> {
                if (Objects.isNull(tn)) {
                    return null;
                }

                if (tn instanceof TextNode) {
                    TextNode textNode = (TextNode) tn;
                    return textNode.textValue();
                }
                return null;
            };

            Function<TreeNode, Integer> treeNodeToIntFunction = tn -> {
                if (Objects.isNull(tn)) {
                    return null;
                }

                if (tn instanceof NumericNode) {
                    NumericNode numericNode = (NumericNode) tn;
                    return numericNode.intValue();
                }
                return null;
            };
            ganttValues.setFrom(treeNodeToDateFunction.apply(treeNode.get("from")));
            ganttValues.setTo(treeNodeToDateFunction.apply(treeNode.get("to")));
            ganttValues.setLabel(treeNodeToTextFunction.apply(treeNode.get("label")));
            ganttValues.setDesc(treeNodeToTextFunction.apply(treeNode.get("desc")));
            ganttValues.setCustomClass(treeNodeToIntFunction.apply(treeNode.get("customClass")));
            return ganttValues;
        }
    }

}
