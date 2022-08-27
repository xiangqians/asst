package org.asst.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.asst.o.Po;
import org.asst.o.Vo;
import org.asst.util.DateUtils;
import org.asst.vo.com.ComVo;

import java.util.Optional;

/**
 * @author xiangqian
 * @date 22:16 2022/08/16
 */
@Data
public abstract class ComPo implements Po {

    /**
     * id
     */
    private String id;

    /**
     * 删除标识，0-正常，1-删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建时间（时间戳）
     */
    private Long createTime;

    /**
     * 修改时间（时间戳）
     */
    private Long updateTime;

    @Override
    public <T extends Vo> T convertToVo(Class<T> type) {

        if (ComVo.class.isAssignableFrom(type)) {
            ComVo vo = null;
            try {
                vo = (ComVo) type.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            vo.setId(getId());
            vo.setCreateTime(Optional.ofNullable(getCreateTime()).filter(timestamp -> timestamp > 0L).map(DateUtils::timestampToLocalDateTime).orElse(null));
            vo.setUpdateTime(Optional.ofNullable(getUpdateTime()).filter(timestamp -> timestamp > 0L).map(DateUtils::timestampToLocalDateTime).orElse(null));
            return (T) vo;
        }

        return Po.super.convertToVo(type);
    }

}
