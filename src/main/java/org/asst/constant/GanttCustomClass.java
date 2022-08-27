package org.asst.constant;

import org.springframework.util.Assert;

import java.util.Set;

/**
 * 甘特图数据条的颜色
 *
 * @author xiangqian
 * @date 15:41 2022/08/27
 */
public interface GanttCustomClass {

    Integer RED = 1;
    Integer GREEN = 2;
    Integer ORANGE = 3;
    Integer BLUE = 4; // 默认

    Set<Integer> SET = Set.of(RED, GREEN, ORANGE, BLUE);

    static void check(Integer customClass) {
        Assert.notNull(customClass, "customClass不能为空");
        Assert.isTrue(SET.contains(customClass), String.format("customClass不合法: %s", customClass));
    }

}
