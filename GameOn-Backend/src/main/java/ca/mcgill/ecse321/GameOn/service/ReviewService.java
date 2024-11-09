package ca.mcgill.ecse321.GameOn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.GameOn.exception.reviewException;

import ca.mcgill.ecse321.GameOn.repository.ReviewRepository;
import ca.mcgill.ecse321.GameOn.model.Review;
import ca.mcgill.ecse321.GameOn.model.Game;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;
    private GameRepository gameRepo;

    /**
     * Posts a new review with the specified details and saves it to the repository.
     * Validates input parameters to ensure the review is properly structured.
     *
     * @param gameName      The game for which all reviews will be returned.
     * @return The list of reviews for the game specified.
     * @throws IllegalArgumentException if any parameters are invalid, or if the game does not exist.
     * @author Mathieu Allaire
     */
    public List<Review> getAllReviewsforGame(String gameName){
        if (gameName == null || gameName.trim().length() == 0) {
            throw new IllegalArgumentException("The name is invalid");
        }
        Game game = gameRepository.findGameByName(aName);

        if (game == null) {
            throw new IllegalArgumentException("Game does not exist");
        }
        return game.getReviews();

    }
    /**
     * Posts a new review with the specified details and saves it to the repository.
     *
     * @param aDescription  A description of the review.
     * @param aStars        The rating given in the review, between 0 and 5 stars.
     * @param aLikes        The initial number of likes for the review, must be non-negative.
     * @param aDislikes     The initial number of dislikes for the review, must be non-negative.
     * @param aReviewAuthor The customer who authored the review.
     * @param aManager      The manager responsible for approving or managing the review.
     * @return The newly created review object.
     * @throws IllegalArgumentException if any parameters are invalid.
     * @author Mathieu Allaire
     */
    public Review postReview(String aDescription, int aStars, int aLikes, int aDislikes, Customer aReviewAuthor, Manager aManager) {
        if (aDescription == null || aDescription.length() == 0) {
            IllegalArgumentException("The review has an empty description");
        }
        if (aStars < 0 || aStars > 5) {
            throw new IllegalArgumentException("The number of stars must be between 0 and 5");
        }
        if (aLikes < 0) {
            throw new IllegalArgumentException("The number of likes must be non-negative");
        }
        if (aDislikes < 0) {
            throw new IllegalArgumentException("The number of dislikes must be non-negative");
        }
        if (aReviewAuthor == null) {
            throw new IllegalArgumentException("The author is invalid");
        }
        if (aManager == null) {
            throw new IllegalArgumentException("The manager is invalid");
        }
        Review review = new Review(aDescription, aStars, aLikes, aDislikes, aReviewAuthor, aManager);

        reviewRepo.save(review);
        return review;
    }

    /**
     * Finds a review by its ID in the repository.
     *
     * @param id The ID of the review to find.
     * @return The review with the specified ID.
     * @throws IllegalArgumentException if no review with the given ID exists.
     * @author Mathieu Allaire
     */
    public Review findReviewById(int id) {
        Review existingReview = reviewRepo.findReviewById( int id);
        if (existingReview == null) {
            throw new IllegalArgumentException("There is no review with ID " + id + ".");
        }
        return existingReview;
    }

    /**
     * Increments the like count of a given review and saves the updated review.
     *
     * @param review The review to be liked.
     * @throws IllegalArgumentException if the review does not exist.
     * @author Mathieu Allaire
     */
    @Transactional
    public void likeReview(Review review) {
        Review review = findReviewById(review.getId());
        if (review == null) {
            throw new IllegalArgumentException("There is no review with ID " + id + ".");
        }
        review.setLikes(review.getLikes() + 1);
        reviewRepo.save(review);
    }

    /**
     * Adds a reply to a review and saves the updated review in the repository.
     *
     * @param review      The review to which the reply is being added.
     * @param description The description of the reply to be added.
     * @throws IllegalArgumentException if the reply description is empty or if the review does not exist.
     * @author Mathieu Allaire
     */
    @Transactional
    public void addReply(Review review, String description) {
        if (description == null || description.length() == 0) {
            IllegalArgumentException("The reply has an empty description");
        }
        Review review = findReviewById(review.getId());
        if (review == null) {
            throw new IllegalArgumentException("There is no review with ID " + id + ".");
        }
        review.setReply(description);
        reviewRepo.save(review);
    }

}
