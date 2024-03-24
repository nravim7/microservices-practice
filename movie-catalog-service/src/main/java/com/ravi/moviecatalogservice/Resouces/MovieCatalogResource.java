package com.ravi.moviecatalogservice.Resouces;

import com.ravi.moviecatalogservice.models.CatalogItem;
import com.ravi.moviecatalogservice.models.Movie;
import com.ravi.moviecatalogservice.models.Rating;
import com.ravi.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogForUserId(@PathVariable("userId") String userId) {


        UserRating ratings =restTemplate.getForObject("http://rating-data-service/ratingdata/user/" + userId , UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {

                    //For each movieId, call movie info service and get details
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    //Put all together
                    return new CatalogItem(movie.getName(), "nice", rating.getRating());

                }).
                collect(Collectors.toList());
    }
}


/*Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();*/

