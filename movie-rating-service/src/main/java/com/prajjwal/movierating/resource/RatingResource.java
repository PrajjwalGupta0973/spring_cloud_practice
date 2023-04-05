package com.prajjwal.movierating.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwal.movierating.domain.Rating;
import com.prajjwal.movierating.domain.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

	@GetMapping("{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		List<Rating> ratings = Arrays.asList(new Rating("100", 8), new Rating("200", 9));
		return new Rating(movieId, 10);
	}	

	@GetMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String movieId) {
		List<Rating> ratings = Arrays.asList(new Rating("100", 8), new Rating("200", 9));
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
