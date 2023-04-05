package com.prajjwal.moviecatalog.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonMergePatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import com.prajjwal.moviecatalog.service.MovieCatalogService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private MovieCatalogService movieCatalogService;

	@GetMapping("/{userId}")
	List<CatalogItem> getCatelog(@PathVariable("userId") String userId) {
		return movieCatalogService.getCatelog(userId);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Movie> patchMovie(@PathVariable("id") Long id, @RequestBody JsonPatch patch)
			throws IllegalArgumentException, JsonPatchException, JsonProcessingException {
		return movieCatalogService.patchMovie(id, patch);
	}

	@GetMapping("/users/{userId}/ratings")
	public UserRating getUserRatings(@PathVariable("userId") String userId) {
		return movieCatalogService.getUserRatings(userId);
	}
}
