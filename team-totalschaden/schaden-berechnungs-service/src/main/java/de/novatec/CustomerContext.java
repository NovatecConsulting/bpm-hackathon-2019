package de.novatec;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class CustomerContext {

  private String traceId;
  private Customer customer;

  public static CustomerContext fromJson(String json) {
    try {
      return new ObjectMapper().readValue(json, CustomerContext.class);
    } catch (Exception e) {
      throw new RuntimeException("Could not deserialize context from JSON: " + e.getMessage(), e);
    }
  }
  
  public String asJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not serialize context to JSON: " + e.getMessage(), e);
    }
  }

  public String getTraceId() {
    return traceId;
  }

  public CustomerContext setTraceId(String traceId) {
    this.traceId = traceId;
    return this;
  }

  public Customer getCustomer() {
    return customer;
  }

  public CustomerContext setCustomer(Customer customer) {
    this.customer = customer;
    return this;
  }
  
  
}
