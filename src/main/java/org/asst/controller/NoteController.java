package org.asst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.asst.pagination.Page;
import org.asst.resp.Response;
import org.asst.resp.StatusCodeImpl;
import org.asst.service.NoteService;
import org.asst.validation.groups.Add;
import org.asst.validation.groups.Modify;
import org.asst.vo.note.NoteVo;
import org.asst.vo.note.param.NoteAddVoParam;
import org.asst.vo.note.param.NoteModifyVoParam;
import org.asst.vo.note.param.NotePageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Controller
@RequestMapping("/note")
@Api(value = "note", tags = "笔记信息管理")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ResponseBody
    @GetMapping("/page")
    @ApiOperation("笔记信息分页查询")
    public Response<Page<NoteVo>> page(@Valid NotePageVoParam voParam) throws Exception {
        return Response.<Page<NoteVo>>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(noteService.queryForPage(voParam))
                .build();
    }

    @ResponseBody
    @ApiOperation("根据id查询笔记信息")
    @GetMapping("/queryById/{id}")
    @ApiImplicitParam(name = "id", value = "笔记id", required = true)
    public Response<NoteVo> queryById(@PathVariable("id") String id) throws Exception {
        return Response.<NoteVo>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(noteService.queryById(id))
                .build();
    }

    @ResponseBody
    @ApiOperation("修改笔记信息")
    @PutMapping("/updateById")
    public Response<Boolean> updateById(@RequestBody @Validated(Modify.class) NoteModifyVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(noteService.updateById(voParam))
                .build();
    }

    @ResponseBody
    @ApiOperation("删除笔记信息")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id", value = "笔记id", required = true)
    public Response<Boolean> deleteById(@PathVariable("id") String id) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(noteService.deleteById(id))
                .build();
    }

    @ResponseBody
    @PostMapping("/save")
    @ApiOperation("新增笔记信息")
    public Response<Boolean> add(@RequestBody @Validated(Add.class) NoteAddVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(noteService.save(voParam))
                .build();
    }

}
