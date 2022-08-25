package org.asst.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.ComPo;
import org.asst.util.DateUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xiangqian
 * @date 01:14 2022/07/21
 */
public interface DaoHelper {

    static <T extends ComPo> Boolean updateById(BaseMapper<T> baseMapper, T po) {
        po.setDelFlag(null);
        po.setCreateTime(null);
        po.setUpdateTime(DateUtils.dateToTimestamp(LocalDateTime.now()));
        return baseMapper.updateById(po) > 0;
    }

    static <T extends ComPo> Boolean save(BaseMapper<T> baseMapper, T po) {
        po.setId(UUID.randomUUID().toString().replace("-", ""));
        po.setDelFlag(null);
        po.setCreateTime(DateUtils.dateToTimestamp(LocalDateTime.now()));
        po.setUpdateTime(null);
        return baseMapper.insert(po) > 0;
    }

    static <T> Page<T> queryForPage(BaseMapper<T> baseMapper, PageRequest pageRequest, Wrapper<T> queryWrapper) {
        return convertMybatisPageToPage(baseMapper.selectPage(createMybatisPage(pageRequest), queryWrapper));
    }

    static <T> Page<T> convertMybatisPageToPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        Page<T> tPage = new Page<>();
        tPage.setCurrent(page.getCurrent());
        tPage.setSize(page.getSize());
        tPage.setPages(page.getPages());
        tPage.setTotal(page.getTotal());
        tPage.setData(Optional.ofNullable(page.getRecords()).orElse(Collections.emptyList()));
        return tPage;
    }

    static <T> com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> createMybatisPage(PageRequest pageRequest) {
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page(pageRequest.getCurrent(), pageRequest.getSize());
    }

}
