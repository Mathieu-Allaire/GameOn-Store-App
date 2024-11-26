package ca.mcgill.ecse321.GameOn.service;
import java.util.List;

import ca.mcgill.ecse321.GameOn.exception.GameOnException;
import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
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
    @Transactional
    public List<Review> getAllReviewsforGame(String gameName){
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The name is invalid");
        }
        Game game = gameRepo.findGameByName(gameName);

        if (game == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Game does not exist");
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
     * @param customerEmail The customer who authored the review.
     * @param managerEmail      The manager responsible for approving or managing the review.
     * @return The newly created review object.
     * @throws IllegalArgumentException if any parameters are invalid.
     * @author Mathieu Allaire
     */
    @Transactional
    public Review postReview(String aDescription, int aStars, int aLikes, int aDislikes, String customerEmail, String managerEmail) {
        if (aDescription == null || aDescription.trim().isEmpty()) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review has an empty description");
        }
        if (aStars < 0 || aStars > 5) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of stars must be between 0 and 5");
        }
        if (aLikes < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of likes must be non-negative");
        }
        if (aDislikes < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of dislikes must be non-negative");
        }

        if (customerEmail == null || customerEmail.trim().length() == 0 || customerEmail.contains(" ") || customerEmail.contains("@") == false || customerEmail.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Customer email is invalid");
        }
        //search customer
        Person customer = personRepo.findPersonByEmail(customerEmail);
        if (customer == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Customer not found " + customerEmail);
        }
        //if the person is not a customer
        if(personRepo.findPersonByEmail(customerEmail).getRole(0).getClass() != Customer.class){
            throw new GameOnException(HttpStatus.NOT_FOUND, "No customer with this email");
        }


        if (managerEmail == null || managerEmail.trim().length() == 0 || managerEmail.contains(" ") || managerEmail.contains("@") == false || managerEmail.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Manager email is invalid");
        }
        //search manager
        Person manager = personRepo.findPersonByEmail(managerEmail);
        if (manager == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Manager not found");
        }
        //if the person is not a customer
        if(personRepo.findPersonByEmail(managerEmail).getRole(0).getClass() != Manager.class){
            throw new GameOnException(HttpStatus.NOT_FOUND, "No manager with this email");
        }


        Review review = new Review(aDescription, aStars, aLikes, aDislikes, (Customer) customer.getRole(0), (Manager) manager.getRole(0));


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
    @Transactional
    public Review findReviewById(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review existingReview = reviewRepo.findReviewById(id);
        if (existingReview == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND , "There is no review with ID " + id + ".");
        }
        return existingReview;
    }

    /**
     * Increments the like count of a given review and saves the updated review.
     *
     * @param id The id of the review to be liked.
     * @return The review with the specified ID with an additional like.
     * @throws IllegalArgumentException if the review does not exist.
     * @author Mathieu Allaire
     */
    @Transactional
    public Review likeReview(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        review.setLikes(review.getLikes() + 1);
        reviewRepo.save(review);
        return review;
    }
    @Transactional
    public Review dislikeReview(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        review.setLikes(review.getDislikes() + 1);
        reviewRepo.save(review);
        return review;
    }

    /**
     * Increments the like count of a given review and saves the updated review.
     *
     * @param id The id of the review to be liked.
     * @param likes The number of likes to set the review to.
     * @return The review with the specified ID with an additional like.
     * @throws IllegalArgumentException if the review does not exist.
     * @author Mathieu Allaire
     */
    @Transactional
    public Review setLikesReview(int id, int likes) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        if (likes < 0){
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of likes must be non-negative");
        }
        review.setLikes(likes);
        reviewRepo.save(review);
        return review;
    }

    @Transactional
    public Review setDislikesReview(int id, int dislikes) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        if (dislikes < 0){
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of dislikes must be non-negative");
        }
        review.setDislikes(dislikes);
        reviewRepo.save(review);
        return review;
    }

    @Transactional
    public Review setStarsReview(int id, int stars) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        if (stars < 0 || stars > 5) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The number of stars must be between 0 and 5");
        }
        review.setStars(stars);
        reviewRepo.save(review);
        return review;
    }

    /**
     * Adds a reply to a review and saves the updated review in the repository.
     *
     * @param id     The id of the review to which the reply is being added.
     * @param description The description of the reply to be added.
     * @return The review with the specified ID with an added reply.
     * @throws IllegalArgumentException if the reply description is empty or if the review does not exist.
     * @author Mathieu Allaire
     */
    @Transactional
    public Review addReply(int id, String description) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The review ID must be non-negative.");
        }
        if (description == null || description.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "The reply has an empty description");
        }
        Review review = findReviewById(id);
        if (review == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There is no review with ID " + id + ".");
        }
        review.setReply(description);
        reviewRepo.save(review);
        return review;
    }

}