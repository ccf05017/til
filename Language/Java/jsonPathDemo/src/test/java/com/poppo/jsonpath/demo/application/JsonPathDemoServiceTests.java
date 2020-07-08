package com.poppo.jsonpath.demo.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonPathDemoServiceTests {
    private JsonPathDemoService jsonPathDemoService;

    String resource = "{\n" +
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
            "}";

    @BeforeEach
    public void setup() {
        jsonPathDemoService = new JsonPathDemoService();
    }

    @Test
    public void setupTest() {
        String result = jsonPathDemoService.parseJson(resource);

        assertThat(result).isEqualTo("Jayway Inc.");
    }
}
