package org.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiangqian
 * @date 15:54 2022/08/14
 */
@Controller("/")
public class IndexController {

    @GetMapping
    public String tes() {
        return "index";
    }

}
