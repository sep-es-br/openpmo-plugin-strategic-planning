package br.gov.es.pmo.strategic_planning.config;

import br.gov.es.pmo.strategic_planning.properties.PentahoStrategicPlanningProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("br.gov.es.pmo.strategic_planning")
@EnableConfigurationProperties(PentahoStrategicPlanningProperties.class)
@PropertySource("classpath:strategic-planning.properties")
public class StrategicPlanningAutoConfig {
}
