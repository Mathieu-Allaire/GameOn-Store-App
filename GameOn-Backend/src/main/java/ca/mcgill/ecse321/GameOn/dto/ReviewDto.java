package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Review;


public class ReviewDto {
    private int id;
    private String description;
    private int stars;
    private int likes;
    private int dislikes;
    private String reply;



    private String customerEmail;


    private String managerEmail;

    // Default constructor for deserialization
    public ReviewDto() {}

    // Full constructor
    public ReviewDto(int id,String description, int stars, int likes, int dislikes, String reply, String customerEmail, String managerEmail) {
        this.id = id;
        this.description = description;
        this.stars = stars;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reply = reply;
        this.customerEmail = customerEmail;
        this.managerEmail = managerEmail;
    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.stars = review.getStars();
        this.likes = review.getLikes();
        this.dislikes = review.getDislikes();
        this.reply = review.getReply();
    }

    // Getters and setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                "description='" + description + '\'' +
                ", stars=" + stars +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", reply='" + reply + '\'' +
                ", customerId=" + customerEmail +
                ", managerId=" + managerEmail +
                '}';
    }
}
