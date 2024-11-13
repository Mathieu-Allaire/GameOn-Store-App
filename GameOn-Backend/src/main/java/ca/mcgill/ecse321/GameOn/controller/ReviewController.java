package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.GameOn.dto.ReviewRequestDto;
import ca.mcgill.ecse321.GameOn.dto.ReviewResponseDto;
import ca.mcgill.ecse321.GameOn.service.ReviewService;
import ca.mcgill.ecse321.GameOn.model.Review;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import jakarta.validation.Valid;

/**
 * This class represents the Review Controller, which handles the HTTP
 * requests related to reviews.
 * It provides endpoints for posting, retrieving and liking reviews,
 * as well as adding replies to reviews.
 *
 * @author Mathieu Allaire
 */
@RestController
public class ReviewController{
    @Autowired
    private ReviewService reviewService;

    /**
     * Posts a new review.
     *
     * @param reviewDto The review data transfer object.
     * @return The created review DTO.
     * @author Mathieu Allaire
     */
    @PostMapping("/reviews")
    public ResponseEntity<?> postReview(@Valid @RequestBody ReviewRequestDto reviewDto) {
        try {
            Review review = reviewService.postReview(
                    reviewDto.getDescription(),
                    reviewDto.getStars(),
                    reviewDto.getLikes(),
                    reviewDto.getDislikes(),
                    reviewDto.getCustomerEmail(),
                    reviewDto.getManager(
            )
            );
            ReviewResponseDto response = new ReviewResponseDto(review);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    


}
