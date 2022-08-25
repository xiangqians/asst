package org.asst.dao;

import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.NotePo;
import org.asst.po.param.NotePoParam;

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
