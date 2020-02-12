package com.poppo.jsonpath.demo.controller;

import com.poppo.jsonpath.demo.application.JsonPathDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JsonPathDemoController {

    private final JsonPathDemoService jsonPathDemoService;

    @PostMapping("/example")
    public String jsonPathParse(@RequestBody String resource) {

        return jsonPathDemoService.parseJson(resource);
    }
}
