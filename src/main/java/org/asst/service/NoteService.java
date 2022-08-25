package org.asst.service;

import org.asst.pagination.Page;
import org.asst.vo.note.NoteVo;
import org.asst.vo.note.param.NoteAddVoParam;
import org.asst.vo.note.param.NoteModifyVoParam;
import org.asst.vo.note.param.NotePageVoParam;

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
