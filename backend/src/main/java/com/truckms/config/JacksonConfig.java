package com.truckms.config;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Xallinta Hibernate lazy-loading serialization errors (LazyInitializationException
// marka entity-yada leh @ManyToOne la soo celiyo sidii JSON)
@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate6Module hibernate6Module() {
        Hibernate6Module module = new Hibernate6Module();
        module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, true);
        return module;
    }
}
