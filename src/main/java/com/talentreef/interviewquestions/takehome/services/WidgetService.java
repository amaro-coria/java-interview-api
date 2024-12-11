package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WidgetService {

  private final WidgetRepository widgetRepository;

  public WidgetService(WidgetRepository widgetRepository) {
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    return widgetRepository.findAll();
  }

  public Optional<Widget> getWidgetByName(String name) {
    return widgetRepository.findByName(name);
  }

  public Widget createWidget(Widget widget) {
    return widgetRepository.save(widget);
  }

  public Widget updateWidget(String name, String description, Double price) {
    Widget widget = widgetRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Widget not found"));
    widget.setDescription(description);
    widget.setPrice(price);
    return widgetRepository.save(widget);
  }

  public void deleteWidget(String name) {
    widgetRepository.deleteById(name);
  }
}