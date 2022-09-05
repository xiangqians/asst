package org.asst.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.asst.o.Vo;
import org.asst.vo.gantt.GanttVo;

/**
 * @author xiangqian
 * @date 15:26 2022/08/27
 */
@Data
@TableName("gantt")
public class GanttPo extends ComPo {

    private static final long serialVersionUID = 1L;

    /**
     * 甘特图名称
     */
    private String name;

    /**
     * 甘特图描述
     */
    @TableField("`desc`")
    private String desc;

    /**
     * 甘特图内容
     */
    private String content;

    /**
     * 甘特图权重，会优先根据权重排序
     */
    private Long weight;


    @Override
    public <T extends Vo> T convertToVo(Class<T> type) {

        if (type == GanttVo.class) {
            GanttVo vo = super.convertToVo(GanttVo.class);
            vo.setName(getName());
            vo.setDesc(getDesc());
            vo.setContent(getContent());
            vo.setWeight(getWeight());
            return (T) vo;
        }

        return super.convertToVo(type);
    }

}
