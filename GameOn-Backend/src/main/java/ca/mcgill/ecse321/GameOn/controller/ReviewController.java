package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GameOn.dto.ReviewDto;
import ca.mcgill.ecse321.GameOn.service.ReviewService;
import ca.mcgill.ecse321.GameOn.model.Review;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ReviewController{
    @Autowired
    private ReviewService gameService;

    @PostMapping("/reviews")
    public ResponseEntity<?> postReview(@Valid @RequestBody ReviewDto reviewDto) {
        try {
            Review review = reviewService.postReview(
                    reviewDto.getDescription(),
                    reviewDto.getStars(),
                    reviewDto.getLikes(),
                    reviewDto.getDislikes(),
                    reviewDto.getReviewAuthor(),
                    reviewDto.getManager(),
                    reviewDto.getReply()
            );
            ReviewDto response = new ReviewDto(review);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> findReviewById

    likeReview

            addReply
}
