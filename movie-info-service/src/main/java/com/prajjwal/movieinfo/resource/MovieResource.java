package com.prajjwal.movieinfo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwal.movieinfo.domain.Movie;

@RestController
@RequestMapping("/movie")
@RefreshScope
public class MovieResource {

	@GetMapping("{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		return new Movie(movieId, myPropValue);
	}

	@Autowired
	Environment env;
	@Value("${myproperty}")
	private String myPropValue;

//	public String getMyPropValue() {
//		return myPropValue;
//	}
//
//	@RefreshScope
//	public void setMyPropValue(String myPropValue) {
//		this.myPropValue = myPropValue;
//	}

	@GetMapping("/env")
	private Environment getEnvironment() {
		System.out.println("hi");
		return env;
	}
}
