package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>(){});
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  @Test
  public void when_getWidgetByName_expect_widgetReturned() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetService.getWidgetByName("Widgette Nielson")).thenReturn(Optional.of(widget));

    MvcResult result = mockMvc.perform(get("/v1/widgets/Widgette Nielson"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_getWidgetByName_expect_notFound() throws Exception {
    when(widgetService.getWidgetByName("NonExistent")).thenReturn(Optional.empty());

    mockMvc.perform(get("/v1/widgets/NonExistent"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void when_createWidget_expect_widgetCreated() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").description("Some description").price(1.0d).build();
    when(widgetService.createWidget(any(Widget.class))).thenReturn(widget);

    MvcResult result = mockMvc.perform(post("/v1/widgets")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(widget)))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(widget);
  }

  @Test
  public void when_updateWidget_expect_widgetUpdated() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").description("Old Description").price(10.0).build();
    Widget updatedWidget = Widget.builder().name("Widgette Nielson").description("New Description").price(20.0).build();
    when(widgetService.updateWidget("Widgette Nielson", "New Description", 20.0)).thenReturn(updatedWidget);

    MvcResult result = mockMvc.perform(put("/v1/widgets/Widgette Nielson")
            .param("description", "New Description")
            .param("price", "20.0"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(), Widget.class);
    assertThat(parsedResult).isEqualTo(updatedWidget);
  }

  @Test
  public void when_deleteWidget_expect_widgetDeleted() throws Exception {
    mockMvc.perform(delete("/v1/widgets/Widgette Nielson"))
        .andExpect(status().isNoContent())
        .andDo(print());

    verify(widgetService).deleteWidget("Widgette Nielson");
  }

}
