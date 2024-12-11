package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "widgets")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder=true)
public class Widget {

  @Id
  @Column(unique = true, nullable = false)
  @NotBlank
  @Size(min = 3, max = 100)
  private String name;

  @Column(nullable = false)
  @NotBlank
  @Size(min = 5, max = 1000)
  private String description;

  @Column(nullable = false, precision = 10, scale = 2)
  @DecimalMin(value = "1.00")
  @DecimalMax(value = "20000.00")
  private Double price;
}