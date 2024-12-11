package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/widgets")
@Tag(name = "Widget API", description = "CRUD operations for Widgets")
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping
  @Operation(summary = "Get all widgets", description = "Retrieve a list of all widgets")
  public ResponseEntity<List<Widget>> getAllWidgets() {
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping("/{name}")
  @Operation(summary = "Get widget by name", description = "Retrieve a widget by its name")
  public ResponseEntity<Widget> getWidgetByName(@PathVariable String name) {
    return ResponseEntity.of(widgetService.getWidgetByName(name));
  }

  @PostMapping
  @Operation(summary = "Create a new widget", description = "Create a new widget with the given details")
  public ResponseEntity<Widget> createWidget(@Valid @RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.createWidget(widget));
  }

  @PutMapping("/{name}")
  @Operation(summary = "Update a widget", description = "Update the description or price of an existing widget")
  public ResponseEntity<Widget> updateWidget(
      @PathVariable String name,
      @RequestParam String description,
      @RequestParam Double price) {
    return ResponseEntity.ok(widgetService.updateWidget(name, description, price));
  }

  @DeleteMapping("/{name}")
  @Operation(summary = "Delete a widget", description = "Delete a widget by its name")
  public ResponseEntity<Void> deleteWidget(@PathVariable String name) {
    widgetService.deleteWidget(name);
    return ResponseEntity.noContent().build();
  }
}