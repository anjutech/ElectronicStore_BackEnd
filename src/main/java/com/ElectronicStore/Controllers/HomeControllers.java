package com.ElectronicStore.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")

public class HomeControllers {
	Logger logger = LoggerFactory.getLogger(HomeControllers.class);

    public String test() {
        this.logger.warn("This is working message");
        return "Testing message";
    }
}
