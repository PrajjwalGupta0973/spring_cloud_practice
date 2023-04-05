package com.prajjwal.moviecatalog.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.prajjwal.moviecatalog.domain.CatalogItem;
import com.prajjwal.moviecatalog.domain.Movie;
import com.prajjwal.moviecatalog.domain.Rating;
import com.prajjwal.moviecatalog.domain.UserRating;
import com.prajjwal.moviecatalog.feignclient.RatingServiceClient;

@Service
public class MovieCatalogService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	@Autowired
	private RatingServiceClient ratingServiceClient;

	public UserRating getUserRatings(String userId) {
		return ratingServiceClient.getUserRatings(userId);
	}

	public List<CatalogItem> getCatelog(String userId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuibreaker2");

		return circuitBreaker.run(() -> {
			List<Rating> ratings = restTemplate
					.getForObject("http://RATING-SERVICE/ratingsdata/users/" + userId, UserRating.class)
					.getUserRating();
			return ratings.stream().map(rating -> {
				Movie movie = webClientBuilder.build().get()
						.uri("http://MOVIE-INFO-SERVICE/movie/" + rating.getMovieId()).retrieve()
						.bodyToMono(Movie.class).block();
				return new CatalogItem(movie.getName(), "desc", rating.getRating());
			}).collect(Collectors.toList());
		});
	}

	private static List<CatalogItem> getCatelogFallback(Throwable throwable) {

		return Collections.singletonList(new CatalogItem("Default Movie", "Default Desc", 10));
	}

	public ResponseEntity<Movie> patchMovie(Long id, @RequestBody JsonPatch patch)
			throws IllegalArgumentException, JsonPatchException, JsonProcessingException {
		Movie targetMoive = new Movie("123", "Transformer");
		targetMoive.setOtherData("xyz");
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode patched = patch.apply(objectMapper.convertValue(targetMoive, JsonNode.class));
		return ResponseEntity.ok(objectMapper.treeToValue(patched, Movie.class));
//		JsonMergePatch u= new js
	}
}
