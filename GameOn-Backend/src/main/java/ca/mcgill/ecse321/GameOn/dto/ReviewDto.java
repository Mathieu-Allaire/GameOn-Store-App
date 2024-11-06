package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Review;

public class ReviewDto{
    private int id;
    private String description;
    private int stars;
    private int likes;
    private int dislikes;
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
}

