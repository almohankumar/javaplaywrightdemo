package com.mohan.playwrightdemo.annotations;


import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Lazy
@ScenarioScope
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LazyScenarioComponent {
}
