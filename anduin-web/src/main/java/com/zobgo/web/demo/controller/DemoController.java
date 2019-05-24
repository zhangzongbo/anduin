package com.zobgo.web.demo.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zobgo.web.demo.dto.DemoReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhangzongbo
 * @date 18-11-21 下午8:19
 */

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping(value = "/health/{name}")
    @ResponseBody
    public String health(@PathVariable("name") String name) {
        return "hello " + name + " service alive!";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    @ResponseBody
    public String welcome(@RequestBody @Valid DemoReqDto dto) {

        return String.format("Welcome: %s ,your Id is: %s， age: %s E-mail: %s",
                dto.getName(), dto.getId(), dto.getAge(), dto.getMail());

    }

    @PostMapping(value = "/test")
    @ResponseBody
    public String test(@RequestParam("name") String name, @RequestParam("id") Long id) {
        return String.format("Hello %s ,your id is: %s", name, id);
    }
}
