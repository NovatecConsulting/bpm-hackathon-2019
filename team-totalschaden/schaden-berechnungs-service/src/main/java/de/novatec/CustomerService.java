package de.novatec;

import kong.unirest.Unirest;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CustomerService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String customerId = (String) delegateExecution.getVariable("customerId");
        Customer customer = getCustomer(customerId);
        delegateExecution.setVariable("wagenAlter", customer.getCar().getAge());
        delegateExecution.setVariable("wagenNeuwert", customer.getCar().getOriginalValue());
    }

    public Customer getCustomer(String customerId) {
        String response = Unirest.get("http://localhost:10000/customer/{customerId}").routeParam("customerId", customerId).header("accept", "application/json").toString();
        CustomerContext customerContext = CustomerContext.fromJson(response);
        return customerContext.getCustomer();
    }
}
