package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Manager;
import ca.mcgill.ecse321.GameOn.model.Review;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public class ReviewDto {

    //@NotBlank(message = "The description must not be empty")
    private String description;

    //@Min(value = 0, message = "The number of stars must be greater than or equal to 0")
    //@Max(value = 5, message = "The maximum number of stars allowed is 5")
    private int stars;

    //Min(value = 0, message = "The number of likes must be positive")
    private int likes;

    //@Min(value = 0, message = "The number of dislikes must be positive")
    private int dislikes;

    private String reply;

    //@NotBlank(message = "The customer is invalid")
    private Customer customer;

    //@NotBlank(message = "The manager is invalid")
    private Manager manager;

    public ReviewDto(){}

    public ReviewDto(Review model) {
        this.description = model.getDescription();
        this.stars = model.getStars();
        this.likes = model.getLikes();
        this.dislikes = model.getDislikes();
        this.reply = model.getReply();
    }

    public ReviewDto(String description, int stars, int likes, int dislikes, String reply, Customer customer, Manager manager) {
        this.description = description;
        this.stars = stars;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reply = reply;
        this.customer = customer;
        this.manager = manager;

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

    public Customer getCustomer() {
        return customer;
    }
    public Manager getManager() {
        return manager;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

}

