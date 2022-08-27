package org.asst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.asst.pagination.Page;
import org.asst.resp.Response;
import org.asst.resp.StatusCodeImpl;
import org.asst.service.GanttService;
import org.asst.vo.gantt.GanttVo;
import org.asst.vo.gantt.param.GanttAddVoParam;
import org.asst.vo.gantt.param.GanttModifyVoParam;
import org.asst.vo.gantt.param.GanttPageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiangqian
 * @date 15:26 2022/08/27
 */
@RestController
@RequestMapping("/gantt")
@Api(value = "gantt", tags = "甘特图信息管理")
public class GanttController {

    @Autowired
    private GanttService ganttService;

    @GetMapping("/page")
    @ApiOperation("甘特图信息分页查询")
    public Response<Page<GanttVo>> page(@Valid GanttPageVoParam voParam) throws Exception {
        return Response.<Page<GanttVo>>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(ganttService.queryForPage(voParam))
                .build();
    }

    @ApiOperation("根据id查询甘特图信息")
    @GetMapping("/queryById/{id}")
    @ApiImplicitParam(name = "id", value = "甘特图id", required = true)
    public Response<GanttVo> queryById(@PathVariable("id") String id) throws Exception {
        return Response.<GanttVo>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(ganttService.queryById(id))
                .build();
    }

    @ApiOperation("修改甘特图信息")
    @PutMapping("/updateById")
    public Response<Boolean> updateById(@RequestBody @Valid GanttModifyVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(ganttService.updateById(voParam))
                .build();
    }

    @ApiOperation("删除甘特图信息")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id", value = "甘特图id", required = true)
    public Response<Boolean> deleteById(@PathVariable("id") String id) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(ganttService.deleteById(id))
                .build();
    }

    @PostMapping("/save")
    @ApiOperation("新增甘特图信息")
    public Response<Boolean> add(@RequestBody @Valid GanttAddVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(ganttService.save(voParam))
                .build();
    }

}
