package de.novatec.schadenbuchen.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)

public class BankDetailsContext {

    private String traceId;


    private BankDetails bankdetails;

    public static BankDetailsContext fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, BankDetailsContext.class);
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

    public String getTraceId() { return traceId; }

    public BankDetailsContext setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public BankDetails getBankdetails() {
        return bankdetails;
    }

    public void setBankdetails(BankDetails bankdetails) {
        this.bankdetails = bankdetails;
    }

}
