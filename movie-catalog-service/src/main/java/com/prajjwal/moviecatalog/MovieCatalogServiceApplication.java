package com.prajjwal.moviecatalog;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class MovieCatalogServiceApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebCLientBuilder() {
		return WebClient.builder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
		var circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
				.waitDurationInOpenState(Duration.ofMillis(10000)).slidingWindowSize(2).build();
		var timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build();
		// the circuitBreakerConfig and timeLimiterConfig objects

		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(timeLimiterConfig).circuitBreakerConfig(circuitBreakerConfig).build());
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration() {
		var circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
				.waitDurationInOpenState(Duration.ofMillis(10000)).slidingWindowSize(5).build();
		var timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build();
		// the circuitBreakerConfig and timeLimiterConfig objects

		return factory -> factory.configure(builder -> builder.timeLimiterConfig(timeLimiterConfig)
				.circuitBreakerConfig(circuitBreakerConfig).build(), "circuibreaker2");
	}

}
