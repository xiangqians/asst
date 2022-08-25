package org.asst.pagination;

import org.springframework.util.Assert;

import java.util.Set;

/**
 * 排序命令
 *
 * @author xiangqian
 * @date 23:15 2022/06/14
 */
public interface SortOrder {

    // 升序
    Integer ASCENDING = 1;

    // 降序
    Integer DESCENDING = 2;

    Set<Integer> SET = Set.of(1, 2);

    static void check(Integer sortOrder) {
        Assert.isTrue(SET.contains(sortOrder), String.format("排序命令不合法，%s", sortOrder));
    }

}
