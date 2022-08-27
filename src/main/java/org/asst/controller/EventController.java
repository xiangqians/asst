package org.asst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.asst.pagination.Page;
import org.asst.resp.Response;
import org.asst.resp.StatusCodeImpl;
import org.asst.service.EventService;
import org.asst.vo.event.EventVo;
import org.asst.vo.event.param.EventAddVoParam;
import org.asst.vo.event.param.EventModifyVoParam;
import org.asst.vo.event.param.EventPageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@RestController
@RequestMapping("/event")
@Api(value = "event", tags = "事件信息管理")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/page")
    @ApiOperation("事件信息分页查询")
    public Response<Page<EventVo>> page(@Valid EventPageVoParam voParam) throws Exception {
        return Response.<Page<EventVo>>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(eventService.queryForPage(voParam))
                .build();
    }

    @ApiOperation("根据id查询事件信息")
    @GetMapping("/queryById/{id}")
    @ApiImplicitParam(name = "id", value = "事件id", required = true)
    public Response<EventVo> queryById(@PathVariable("id") String id) throws Exception {
        return Response.<EventVo>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(eventService.queryById(id))
                .build();
    }

    @ApiOperation("修改事件信息")
    @PutMapping("/updateById")
    public Response<Boolean> updateById(@RequestBody @Valid EventModifyVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(eventService.updateById(voParam))
                .build();
    }

    @ApiOperation("删除事件信息")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id", value = "事件id", required = true)
    public Response<Boolean> deleteById(@PathVariable("id") String id) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(eventService.deleteById(id))
                .build();
    }

    @PostMapping("/save")
    @ApiOperation("新增事件信息")
    public Response<Boolean> add(@RequestBody @Valid EventAddVoParam voParam) throws Exception {
        return Response.<Boolean>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(eventService.save(voParam))
                .build();
    }

}
