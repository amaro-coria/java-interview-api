package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getWidgetByName_expect_widgetReturned() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.findByName("Widgette Nielson")).thenReturn(Optional.of(widget));

    Optional<Widget> result = widgetService.getWidgetByName("Widgette Nielson");

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(widget);
  }

  @Test
  public void when_getWidgetByName_expect_emptyResult() throws Exception {
    when(widgetRepository.findByName("NonExistent")).thenReturn(Optional.empty());

    Optional<Widget> result = widgetService.getWidgetByName("NonExistent");

    assertThat(result).isNotPresent();
  }

  @Test
  public void when_createWidget_expect_widgetSaved() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    when(widgetRepository.save(widget)).thenReturn(widget);

    Widget result = widgetService.createWidget(widget);

    assertThat(result).isEqualTo(widget);
  }

  @Test
  public void when_updateWidget_expect_widgetUpdated() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").description("Old Description").price(10.0).build();
    when(widgetRepository.findByName("Widgette Nielson")).thenReturn(Optional.of(widget));
    when(widgetRepository.save(widget)).thenReturn(widget);

    Widget result = widgetService.updateWidget("Widgette Nielson", "New Description", 20.0);

    assertThat(result.getDescription()).isEqualTo("New Description");
    assertThat(result.getPrice()).isEqualTo(20.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void when_updateWidget_expect_exceptionThrown() throws Exception {
    when(widgetRepository.findByName("NonExistent")).thenReturn(Optional.empty());

    widgetService.updateWidget("NonExistent", "New Description", 20.0);
  }

  @Test
  public void when_deleteWidget_expect_widgetDeleted() throws Exception {
    widgetService.deleteWidget("Widgette Nielson");

    verify(widgetRepository).deleteById("Widgette Nielson");
  }

}
