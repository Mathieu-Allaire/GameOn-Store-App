package ca.mcgill.ecse321.GameOn.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public class ReviewRequestDto {

    //@NotBlank(message = "The description must not be empty")
    private String description;

    //@Min(value = 0, message = "The number of stars must be greater than or equal to 0")
    //@Max(value = 5, message = "The maximum number of stars allowed is 5")
    private int stars;

    //@Min(value = 0, message = "The number of likes must be positive")
    private int likes;

   // @Min(value = 0, message = "The number of dislikes must be positive")
    private int dislikes;

    private String reply;

    //@NotBlank(message = "The customer is invalid")
    private String customerEmail;

    //@NotBlank(message = "The manager is invalid")
    private int managerId;

    public ReviewRequestDto(){

    }

    public ReviewRequestDto(String description, int stars, int likes, int dislikes, String reply, String customerEmail, int managerId) {
        this.description = description;
        this.stars = stars;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reply = reply;
        this.customerEmail = customerEmail;
        this.managerId = managerId;

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

    public String getCustomerEmail() {
        return customerEmail;
    }
    public Integer getManager() {
        return managerId;
    }

}

