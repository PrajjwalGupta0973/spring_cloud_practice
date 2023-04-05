package com.prajjwal.moviecatalog.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prajjwal.moviecatalog.domain.UserRating;

@FeignClient("RATING-SERVICE")
public interface RatingServiceClient {

	@RequestMapping(value = "/ratingsdata/users/{userId}", method = RequestMethod.GET)
	UserRating getUserRatings(@PathVariable("userId") String userId);
}
