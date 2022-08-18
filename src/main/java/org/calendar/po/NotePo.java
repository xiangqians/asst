package org.calendar.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.calendar.o.Vo;
import org.calendar.vo.note.NoteVo;

/**
 * 笔记信息表
 *
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Data
@TableName("note")
@ToString(callSuper = true)
public class NotePo extends ComPo {

    private static final long serialVersionUID = 1L;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记内容，TEXT值是一个文本字符串，使用数据库编码（UTF-8、UTF-16BE 或 UTF-16LE）存储
     */
    private String content;

    /**
     * 笔记权重，会优先根据权重排序
     */
    private Long weight;

    @Override
    public <T extends Vo> T convertToVo(Class<T> type) {

        if (type == NoteVo.class) {
            NoteVo vo = super.convertToVo(NoteVo.class);
            vo.setTitle(getTitle());
            vo.setContent(getContent());
            vo.setWeight(getWeight());
            return (T) vo;
        }

        return super.convertToVo(type);
    }


}
