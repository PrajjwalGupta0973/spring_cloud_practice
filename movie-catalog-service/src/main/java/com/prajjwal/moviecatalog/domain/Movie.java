package com.prajjwal.moviecatalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	private String movieId;
	private String name;
	private String otherData;

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public Movie() {
		super();
	}

	public Movie(String movieId, String name) {
		this.movieId = movieId;
		this.name = name;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
