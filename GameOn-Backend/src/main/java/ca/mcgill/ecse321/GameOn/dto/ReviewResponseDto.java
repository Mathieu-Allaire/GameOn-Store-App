package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Review;

public class ReviewResponseDto {
    private String description;
    private int stars;
    private int likes;
    private int dislikes;
    private String reply;

    protected ReviewResponseDto() {
    }

    public ReviewResponseDto(Review review) {
        this.description = review.getDescription();
        this.stars = review.getStars();
        this.likes = review.getLikes();
        this.dislikes = review.getDislikes();
        this.reply = review.getReply();
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

}
