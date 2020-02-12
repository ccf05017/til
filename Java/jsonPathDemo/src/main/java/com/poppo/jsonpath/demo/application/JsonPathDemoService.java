package com.poppo.jsonpath.demo.application;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

@Service
public class JsonPathDemoService {
    String jsonpathCreatorNamePath = "$['tool']['jsonpath']['creator']['name']";

    public String parseJson(String resource) {
        DocumentContext context = JsonPath.parse(resource);

        return context.read(jsonpathCreatorNamePath);
    }
}
