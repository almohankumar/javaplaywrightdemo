package com.mohan.playwrightdemo.steps;

import com.mohan.playwrightdemo.controller.TokenController;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenSteps {

    @Autowired
    TokenController tokenController;

    @Given("I generate the token")
    public void iGenerateTheToken() {
        tokenController.generateToken();
    }
}
