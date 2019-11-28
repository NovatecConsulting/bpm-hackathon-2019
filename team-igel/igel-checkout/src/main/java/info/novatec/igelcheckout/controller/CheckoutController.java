package info.novatec.igelcheckout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import info.novatec.igelcheckout.service.CheckoutService;

@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("v1/checkout/{id}")
    public void checkout(@PathVariable String id) {
        checkoutService.checkout(id);
    }

}
