package dev.coll.ctf;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Test;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDiffTest {
  private static final Path SHARED_OPENAPI_PATH = Paths.get("../shared/openapi/openapi.json");
  private static final Path BACKUP_OPENAPI_PATH = Paths.get("../shared/openapi/openapi.json.bak");

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void compareSharedOpenApiSpecWithCurrentSpec() throws Exception {
    // GIVEN
    assertThat(Files.exists(SHARED_OPENAPI_PATH)).isTrue();
    String sharedOpenApiSpec = Files.readString(SHARED_OPENAPI_PATH);

    // WHEN
    String currentOpenApiSpec = mockMvc
        .perform(MockMvcRequestBuilders.get("/docs").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // THEN
    boolean different = true;
    try {
      ChangedOpenApi diff = OpenApiCompare.fromContents(sharedOpenApiSpec, currentOpenApiSpec);
      different = diff.isDifferent();

      if (different)
        fail(new ConsoleRender().render(diff));
    } finally {
      if (different) {
        Files.copy(SHARED_OPENAPI_PATH, BACKUP_OPENAPI_PATH, StandardCopyOption.REPLACE_EXISTING);
        Files.writeString(SHARED_OPENAPI_PATH, beautifyJson(currentOpenApiSpec));
      }
    }
  }

  private String beautifyJson(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(json, Object.class));
  }
}
