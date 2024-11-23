import axios from "axios";

export class Review {
    constructor(response) {
        this.id = response.id; // Assuming the backend includes an ID in the response
        this.description = response.description;
        this.stars = response.stars;
        this.likes = response.likes;
        this.dislikes = response.dislikes;
        this.reply = response.reply;
        this.customerId = response.customerId;
        this.managerId = response.managerId;
    }

    /**
     * Create a new review by posting it to the server.
     * @returns {Promise<Review>} The created review.
     * @author Mathieu Allaire
     */
    async createReview() {
        try {
            const response = await axios.post("/reviews", this);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Fetch all reviews for a specific game.
     * @param {string} name - The name of the game.
     * @returns {Promise<Review[]>} A list of reviews for the game.
     * @author Mathieu Allaire
     */
    static async getAllReviewsForGame(name) {
        try {
            const response = await axios.get(`/game/${name}/reviews`);
            return response.data.map((reviewData) => new Review(reviewData));
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Find a review by its ID.
     * @param {number} id - The review ID.
     * @returns {Promise<Review>} The review with the specified ID.
     * @author Mathieu Allaire
     */
    static async findReviewById(id) {
        try {
            const response = await axios.get(`/reviews/${id}`);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Add a reply to the current review.
     * @param {string} reply - The reply content.
     * @returns {Promise<Review>} The updated review with the reply added.
     * @author Mathieu Allaire
     */
    async addReply(reply) {
        try {
            const response = await axios.post(`/reviews/${this.id}/reply`, reply);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Like the current review.
     * @returns {Promise<Review>} The updated review with incremented likes.
     * @author Mathieu Allaire
     */
    async likeReview() {
        try {
            const response = await axios.post(`/reviews/${this.id}/addLike`);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Dislike the current review.
     * @returns {Promise<Review>} The updated review with incremented dislikes.
     * @author Mathieu Allaire
     */
    async dislikeReview() {
        try {
            const response = await axios.post(`/reviews/${this.id}/addDislike`);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Update the likes count for the current review.
     * @param {number} likes - The new likes count.
     * @returns {Promise<Review>} The updated review.
     * @author Mathieu Allaire
     */
    async updateLikes(likes) {
        try {
            const response = await axios.post(`/reviews/${this.id}/updateLikes`, likes);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Update the dislikes count for the current review.
     * @param {number} dislikes - The new dislikes count.
     * @returns {Promise<Review>} The updated review.
     * @author Mathieu Allaire
     */
    async updateDislikes(dislikes) {
        try {
            const response = await axios.post(
                `/reviews/${this.id}/updateDislikes`,
                dislikes
            );
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }

    /**
     * Update the stars count for the current review.
     * @param {number} stars - The new stars count.
     * @returns {Promise<Review>} The updated review.
     * @author Mathieu Allaire
     */
    async updateStars(stars) {
        try {
            const response = await axios.post(`/reviews/${this.id}/updateStars`, stars);
            return new Review(response.data);
        } catch (error) {
            throw new Error(error.response?.data || error.message);
        }
    }
}
