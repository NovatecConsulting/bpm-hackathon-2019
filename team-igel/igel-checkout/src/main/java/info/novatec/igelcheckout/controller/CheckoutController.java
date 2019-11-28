package info.novatec.igelcheckout.controller;

import info.novatec.igelcheckout.service.CheckoutService;
import info.novatec.messages.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.UUID.randomUUID;

@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("v1/checkout")
    public String checkout(@RequestBody OrderPlacedEvent order) {
        order.setOrderId(randomUUID().toString());
        checkoutService.checkout(order);
        return order.getOrderId();
    }

}
