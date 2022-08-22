package org.calendar.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiangqian
 * @date 15:54 2022/08/14
 */
@Slf4j
@Controller
@Api(value = "index", tags = "Index")
public class IndexController {

    @GetMapping({"/", "/index", "/index.html"})
    public String index() {
        return "forward:html/index.html";
    }

}
