package org.calendar.service.impl;

import org.calendar.dao.NoteDao;
import org.calendar.pagination.Page;
import org.calendar.po.NotePo;
import org.calendar.po.param.NotePoParam;
import org.calendar.service.NoteService;
import org.calendar.vo.note.NoteVo;
import org.calendar.vo.note.param.NoteAddVoParam;
import org.calendar.vo.note.param.NoteModifyVoParam;
import org.calendar.vo.note.param.NotePageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;

    private Function<NotePo, NoteVo> poToVoFunction = po -> po.convertToVo(NoteVo.class);

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public Page<NoteVo> queryForPage(NotePageVoParam voParam) {
        return noteDao.queryForPage(voParam, voParam.convertToPoParam(NotePoParam.class)).convert(poToVoFunction);
    }

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public NoteVo queryById(String id) {
        NotePo po = checkNoteId(id);
        return poToVoFunction.apply(po);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean updateById(NoteModifyVoParam voParam) {
        checkNoteId(voParam.getId());
        return noteDao.updateById(voParam.convertToPoParam(NotePoParam.class));
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean deleteById(String id) {
        checkNoteId(id);
        return noteDao.deleteById(id);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean save(NoteAddVoParam voParam) {
        return noteDao.save(voParam.convertToPoParam(NotePoParam.class));
    }

    private NotePo checkNoteId(String noteId) {
        Assert.notNull(noteId, "笔记id不能为空");
        NotePo po = null;
        Assert.isTrue(Objects.nonNull(po = noteDao.queryById(noteId)), String.format("笔记信息（id=%s）不存在", noteId));
        return po;
    }

}
