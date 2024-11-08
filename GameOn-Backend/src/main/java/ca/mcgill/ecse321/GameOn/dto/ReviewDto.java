package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Review;

public class ReviewDto{

    private int id;
    @NotBlank(message = "The description must not be empty")
    private String description;
    @Min(value = 0, message = "The number of stars must be greater than or equal to 0");
    @Max(value = 5, message = "The maximum number of stars allowed is 5");
    private int stars;
    @Min(value = 0, message = "The number of likes must be positive");
    private int likes;
    @Min(value = 0, message = "The number of dislikes must be positive");
    private int dislikes;
    @NotBlank(message = "The review must have an author");
    private Customer reviewAuthor;
    @NotBlank(message = "The review must have a manager");
    private Manager manager;
    private String reply;


    @SuppressWarnings("unused")
    private ReviewDto(){
    }

    public ReviewDto(Review model){
        this.id = model.getID();
        this.description = model.getDescription();
        this.stars = model.getStars();
        this.likes = model.getLikes();
        this.dislikes = model.getDislikes();
        this.reply = model.getReply();
        this.reviewAuthor = model.getReviewAuthor();
        this.manager = model.getManager();
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public int getStars() {
        return stars;
    }
    public int getLikes() {
        return likes;
    }
    public int getDislikes() {
        return dislikes;
    }
    public String getReply() {
        return reply;
    }
    public Customer getReviewAuthor() {
        return reviewAuthor;
    }
    public Manager getManager() {
        return manager;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
    public void setReviewAuthor(Customer reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }
}

