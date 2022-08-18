package org.calendar.dao;

import org.calendar.pagination.Page;
import org.calendar.pagination.PageRequest;
import org.calendar.po.NotePo;
import org.calendar.po.param.NotePoParam;

/**
 * @author xiangqian
 * @date 22:35 2022/08/16
 */
public interface NoteDao {

    Page<NotePo> queryForPage(PageRequest pageRequest, NotePoParam poParam);

    NotePo queryById(String id);

    Boolean updateById(NotePoParam poParam);

    Boolean deleteById(String id);

    Boolean save(NotePoParam poParam);

}
