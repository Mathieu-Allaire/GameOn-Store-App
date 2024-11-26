<template>
  <main class="layout-container">
    <!-- Top Section: Title, Picture, Description, Price, Category -->
    <div class="top-section">
      <!-- Left: Picture -->
      <div class="picture-container">
        <img
            v-if="gameDetails?.picture"
            :src="gameDetails.picture"
            :alt="gameDetails.name"
            class="game-image"
        />
      </div>

      <!-- Right: Title, Description, Price, Category -->
      <div class="details-container">
        <h1 class="game-title">{{ gameDetails ? gameDetails.name : "Game Not Found" }}</h1>
        <p v-if="gameDetails?.description" class="game-description">
          {{ gameDetails.description }}
        </p>
        <p v-if="gameDetails?.price" class="game-price">
          Price: ${{ gameDetails.price }}
        </p>
        <p v-if="gameDetails?.category" class="game-category">
          Category: {{ gameDetails.category }}
        </p>
      </div>
    </div>

    <!-- Middle Section: Buttons -->
    <div class="button-section">
      <button class="action-button">Add to Wishlist</button>
      <button class="action-button">Add to Cart</button>
    </div>

    <div class="add-review-section">
      <h2>Write a Review</h2>
      <form @submit.prevent="postReview" class="review-form">
        <div class="form-container">
          <!-- Left Column: Review Text -->
          <div class="review-column">
            <label for="review-description" class="review-label">Your Review</label>
            <textarea
                id="review-description"
                v-model="this.newReview.description"
                placeholder="Write your review here..."
                class="review-textarea"
                required
            ></textarea>
          </div>

          <!-- Right Column: Rating and Submit -->
          <div class="actions-column">
            <div class="rating-group">
              <label for="review-stars">Rating</label>
              <div class="star-rating">
            <span
                v-for="star in 5"
                :key="star"
                class="star"
                :class="{ selected: star <= this.newReview.stars }"
                @click="this.newReview.stars = star"
            >
              ★
            </span>
              </div>
            </div>
            <button type="submit" class="post-review-button">Post Review</button>
          </div>
        </div>
      </form>
    </div>
    <div>
      <h2>You must be a logged in customer to write a review.</h2>
      <div><RouterLink to="/login">Sign In</RouterLink></div>
      <div><RouterLink to="/register">Sign Up</RouterLink></div>
    </div>

    <!-- Reviews Section -->
    <div class="reviews-section">
      <table class="reviews-table">
        <thead>
        <tr>
          <th>Reviewer</th>
          <th>Review</th>
          <th>Stars</th>
          <th>Likes</th>
          <th>Dislikes</th>
          <th>Reply</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="review in reviews" :key="review.id">
          <td>{{ review.customerId}}</td>
          <td>{{ review.description }}</td>
          <td>{{ review.stars }}</td>
          <td>{{ review.likes }}</td>
          <td>{{ review.dislikes }}</td>
          <td>{{ review.reply }}</td>
        </tr>
        <tr v-if="reviews.length === 0">
          <td colspan="6" class="no-reviews">No reviews yet. Be the first to review this game!</td>
        </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>

<script>
import { state } from '../store/state'; // Ensure the correct path to the state file
import { Game } from "../dto/Game";
import { Review } from "../dto/Review"; // Import the Review class
import { v4 as uuidv4 } from 'uuid';

export default {
  computed: {
    state() {
      return state; // Make the global state reactive in this component
    }
  },
  name: "GameDetailsPage",
  data() {
    return {
      gameDetails: null, // To hold game details
      reviews: [], // To hold reviews for the game
      role: '',
      newReview: {
        description: '',
        stars: null,
        likes: 0, // Optional, you can add a default value
        dislikes: 0, // Optional, you can add a default value
        customerId: state.loggedInUserId || null, // Assume user is logged in
      },
    };
  },
  async created() {
    // Retrieve the role from sessionStorage
    this.role = state.loggedIn; // Update local role
    // Fetch game details by name from the route parameter
    const gameName = this.$route.params.gameNameNoSpace; // Assuming your route is configured as `/game/:name`
    try {
      // Fetch game details
      const gameResponse = await Game.findGameByName(gameName);
      if (gameResponse.error) {
        console.error(gameResponse.error);
      } else {
        this.gameDetails = gameResponse;
      }

      // Fetch reviews for the game
      //const reviewsResponse = await Review.getAllReviewsForGame(gameName);
      //this.reviews = reviewsResponse;
    } catch (error) {
      console.error("Error fetching data:", error.message);
    }
  },
  methods:{
    async postReview() {
      this.newReview.id = uuidv4();
      this.newReview.likes = 0;
      this.newReview.dislikes = 0;
      this.newReview.reply = null;
      //this.gameDetails.name; // Ensure the game name is available
      try {
        const response = await Review.createReview(this.newReview);
        if (response.error) {
          console.error(response.error);
        } else {
          // Add the new review to the list of reviews
          this.reviews.unshift(response);
          // Reset the form
          this.newReview.description = '';
          this.newReview.stars = null;
        }
      } catch (error) {
        console.error("Error posting review:", error.message);
      }
      }
    },
    async replyToReview(reviewId) {
      const replyText = prompt("Enter your reply:");

      if (replyText) {
        try {
          const response = await Review.replyToReview(reviewId, replyText);
          if (response.error) {
            console.error(response.error);
          } else {
            alert("Reply added successfully!");
            const updatedReview = this.reviews.find((review) => review.id === reviewId);
            if (updatedReview) {
              updatedReview.reply = replyText; // Update the reply in the review
            }
          }
        } catch (error) {
          console.error("Error replying to review:", error.message);
        }
      }
    }
};
</script>


<style>
html,
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center; /* Horizontal centering */
  align-items: center; /* Vertical centering */
  background-color: #f5f5f5; /* Optional: Light background */

}
/* Center Content in the Layout */
.layout-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* Center content horizontally */
  gap: 2em; /* Add spacing between sections */
  width: 100%;
  margin: 0 auto; /* Center layout in the viewport */
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* Top Section: Title, Picture, Description, Price, Category */
.top-section {
  display: flex;
  align-items: flex-start; /* Align items vertically */
  justify-content: space-between; /* Space out the picture and details */
  gap: 2em; /* Space between picture and details */
  width: 100%;
}

/* Picture Container */
.picture-container {
  flex: 2;
  display: flex;
  justify-content: center; /* Center the picture horizontally */
  align-items: center; /* Center the picture vertically */
}

.game-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 10px;
  object-fit: contain;
}

/* Details Container */
.details-container {
  padding:1em;
  border: 3px solid black;
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 1em; /* Space between details */
}

.game-title {
  font-size: 2em;
  font-weight: bold;
  text-align: left; /* Center the title */
}

.game-description {
  font-size: 1.2em;
}

.game-price,
.game-category {
  font-size: 1.1em;
  font-weight: bold;
}

/* Button Section */
.button-section {
  display: flex;
  justify-content: center; /* Center buttons horizontally */
  gap: 1em; /* Space between buttons */
  width: 100%;
  margin: 2em;
}
.reply-button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #ffcc00;
  color: black;
  font-size: 0.9em;
  cursor: pointer;
  transition: background-color 0.3s;
}

.reply-button:hover {
  background-color: #ffaa00;
}

.action-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s;
}


.action-button:hover {
  background-color: #0056b3;
}

/* Add Review Section */
.add-review-section {
  text-align: center;
  padding: 2em;
  width: 100%;
  border: 3px solid black;
  background: grey;
  margin-bottom: 2em;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: black;
}

.add-review-section h2 {
  font-size: 1.8em;
  margin-bottom: 1em;
}

.review-form {
  width: 100%;
}

.form-container {
  display: flex;
  gap: 1em; /* Space between the two columns */
  width: 100%;
}

.review-column {
  flex: 4; /* 80% width */
  display: flex;
  flex-direction: column;
  gap: 0.5em; /* Space between label and textarea */
  align-items: flex-start; /* Align items to the left */
}

.review-label {
  font-size: 1.2em;
  font-weight: bold;
}

.review-textarea {
  width: 100%;
  height: 120px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
  font-size: 1em;
  box-sizing: border-box;
}

.actions-column {
  flex: 1; /* 20% width */
  display: flex;
  flex-direction: column;
  gap: 1em; /* Space between rating and button */
  align-items: center; /* Center content horizontally */
}

.rating-group {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.5em; /* Space between label and stars */
  align-items: center; /* Center-align stars */
}

.star-rating {
  display: flex;
  gap: 5px; /* Space between stars */
  font-size: 1.5em; /* Make stars bigger */
  cursor: pointer;
}

.star {
  color: #ccc; /* Default star color */
  transition: color 0.3s;
}

.star.selected {
  color: #f5c518; /* Highlight selected stars */
}

.star:hover {
  color: #f5c518; /* Highlight star on hover */
}

.post-review-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.post-review-button:hover {
  background-color: #0056b3;
  transform: scale(1.05); /* Slight zoom effect on hover */
}

/* Reviews Section */
.reviews-section {
  width: 100%;
}

.reviews-table {
  width: 100%;
  border-collapse: collapse;
}

.reviews-table th,
.reviews-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

.reviews-table th {
  background-color: #f0f0f0;
}
</style>
