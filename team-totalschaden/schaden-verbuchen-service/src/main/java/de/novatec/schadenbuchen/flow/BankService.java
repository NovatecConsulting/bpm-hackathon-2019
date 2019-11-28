package de.novatec.schadenbuchen.flow;

import kong.unirest.Unirest;

public class BankService {

    public BankDetails getBankDetails(String customerId) {
        String response = Unirest.get("htto://localhost:10000/bank/{customerId}")
                .routeParam("customerId", customerId)
                .header("accept", "application/json").asString().getBody();
        BankDetailsContext bankDetailsContext = BankDetailsContext.fromJson(response);
        return bankDetailsContext.getBankdetails();
    }
}
