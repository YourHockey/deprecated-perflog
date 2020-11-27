package ru.vakoom.yourhockey.perflog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PerformanceLoggerAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public PerformanceLoggerAspect performanceLoggerAspect() {
		return new PerformanceLoggerAspect();
	}
}
