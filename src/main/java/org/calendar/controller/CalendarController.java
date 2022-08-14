package org.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xiangqian
 * @date 16:37 2022/08/14
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @GetMapping
    public String tes() {
        return "calendar";
    }

    @ResponseBody
    @GetMapping("/timeZones")
    public List<String> timeZones() {
        return List.of("local", "UTC");
    }

}
