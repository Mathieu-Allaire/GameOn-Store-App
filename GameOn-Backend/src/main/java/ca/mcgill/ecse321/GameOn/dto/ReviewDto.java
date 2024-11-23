package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Review;


public class ReviewDto {

    private String description;
    private int stars;
    private int likes;
    private int dislikes;
    private String reply;


    private Long customerId;


    private Long managerId;

    // Default constructor for deserialization
    public ReviewDto() {}

    // Full constructor
    public ReviewDto(String description, int stars, int likes, int dislikes, String reply, Long customerId, Long managerId) {
        this.description = description;
        this.stars = stars;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reply = reply;
        this.customerId = customerId;
        this.managerId = managerId;
    }

    public ReviewDto(Review review) {
        this.description = review.getDescription();
        this.stars = review.getStars();
        this.likes = review.getLikes();
        this.dislikes = review.getDislikes();
        this.reply = review.getReply();
        this.customerId = review.getReviewAuthor().getId();
        this.managerId = review.getManager().getId();
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "description='" + description + '\'' +
                ", stars=" + stars +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", reply='" + reply + '\'' +
                ", customerId=" + customerId +
                ", managerId=" + managerId +
                '}';
    }
}
