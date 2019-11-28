package info.novatec.igelcheckout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.igelcheckout.service.CheckoutService;
import info.novatec.messages.OrderPlacedEvent;

@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("v1/checkout")
    public void checkout(@RequestBody OrderPlacedEvent order) {
        checkoutService.checkout(order);
    }

}
