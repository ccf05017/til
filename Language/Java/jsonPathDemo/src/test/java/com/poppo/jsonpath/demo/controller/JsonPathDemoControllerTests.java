package com.poppo.jsonpath.demo.controller;

import com.poppo.jsonpath.demo.application.JsonPathDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class JsonPathDemoControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JsonPathDemoService jsonPathDemoService;

    @Test
    public void jsonPathDemo() throws Exception {
        mockMvc.perform(post("/example")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tool\":\n" +
                        "  {\n" +
                        "    \"jsonpath\":\n" +
                        "    {\n" +
                        "      \"creator\":\n" +
                        "      {\n" +
                        "        \"name\": \"Jayway Inc.\",\n" +
                        "        \"location\":\n" +
                        "        [\n" +
                        "          \"Malmo\",\n" +
                        "          \"San Francisco\",\n" +
                        "          \"Helsingborg\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    }\n" +
                        "  },\n" +
                        "\n" +
                        "  \"book\":\n" +
                        "  [\n" +
                        "    {\n" +
                        "      \"title\": \"Beginning JSON\",\n" +
                        "      \"price\": 49.99\n" +
                        "    },\n" +
                        "\n" +
                        "    {\n" +
                        "      \"title\": \"JSON at Work\",\n" +
                        "      \"price\": 29.99\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"))
                .andExpect(status().isOk())
        ;
    }
}
