package org.calendar.service;

import org.calendar.pagination.Page;
import org.calendar.vo.note.NoteVo;
import org.calendar.vo.note.param.NoteAddVoParam;
import org.calendar.vo.note.param.NoteModifyVoParam;
import org.calendar.vo.note.param.NotePageVoParam;

/**
 * 笔记信息服务类
 *
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
public interface NoteService {

    Page<NoteVo> queryForPage(NotePageVoParam voParam);

    NoteVo queryById(String id);

    Boolean updateById(NoteModifyVoParam voParam);

    Boolean deleteById(String id);

    Boolean save(NoteAddVoParam voParam);

}
