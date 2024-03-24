package com.ravi.ratingdataservice.Resources;

import com.ravi.ratingdataservice.Models.Rating;
import com.ravi.ratingdataservice.Models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 5);
    }

    @GetMapping("user/{userid}")
    public UserRating rating(@PathVariable("userid") String userId) {
        List<Rating> rating = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5234", 5)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(rating);
        return userRating;
    }
}
