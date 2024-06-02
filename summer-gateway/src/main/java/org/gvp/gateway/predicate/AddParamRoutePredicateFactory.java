package org.gvp.gateway.predicate;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

@Log4j2
@Component
public class AddParamRoutePredicateFactory extends AbstractRoutePredicateFactory<AddParamRoutePredicateFactory.Config> {
    public static final String PARAM_KEY = "paramKey";

    public AddParamRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of(PARAM_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {

            return true;
        };
    }


    public static class Config{
        private String paramKey;
    }
}
