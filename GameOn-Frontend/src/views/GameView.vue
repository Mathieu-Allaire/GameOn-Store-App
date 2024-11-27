
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
      <button @click="addToWishlist" class="add-wishlist-button">Add to Wishlist</button>
      <div v-if="wishlistSuccess" class="success-message">Game added to wishlist successfully!</div>
      <div v-if="wishlistError" class="error-message">{{ wishlistError }}</div>
      <button @click="addToCart" class="action-button">Add to Cart</button>
      <div v-if="cartSuccess" class="success-message">Game added to cart successfully!</div>
      <div v-if="cartError" class="error-message">{{ cartError }}</div>
    </div>

    <div v-if="['1'].includes(state.loggedIn)" class="add-review-section">
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
    <div v-else>
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
        <tr v-for="review in reviewsOfGame" :key="review.id">
          <td>{{ review.customerEmail || "Anonymous" }}</td>
          <td>{{ review.description }}</td>
          <td>{{ review.stars }}</td>
          <td>
            <div v-if="['1'].includes(state.loggedIn)" class="like-dislike-container">
              <button @click="likeReview(review.id)" class="like-button">👍 {{ review.likes }}</button>
              <button @click="dislikeReview(review.id)" class="dislike-button">👎 {{ review.dislikes }}</button>
            </div>
            <div v-else>Likes: {{ review.likes }}</div>
          </td>
          <td>
            <div v-if="['3'].includes(state.loggedIn)" class="reply-container">
              <button @click="replyToReview(review.id)" class="reply-button">Reply</button>
              <p>{{ review.reply || "No reply yet" }}</p>
            </div>
          </td>
        </tr>
        <tr v-if="reviewsOfGame.length === 0">
          <td colspan="6" class="no-reviews">No reviews yet. Be the first to review this game!</td>
        </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  // NOTE: it's baseURL, not baseUrl
  baseURL: "http://localhost:8087"
});
import { state } from '../store/state'; // Ensure the correct path to the state file
import { Game } from "../dto/Game";
import { Review } from "../dto/Review"; // Import the Review class
import { GameWishlist } from "../dto/GameWishlist";
import { Cart } from "../dto/Cart"; // Import the Cart class


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
      reviewsOfGame: [], // To hold reviews for the game
      role: '',
      newReview: {
        description: '',
        stars: 0,
        likes: 0, // Optional, you can add a default value
        dislikes: 0, // Optional, you can add a default value
        reply: '',
        customerEmail: '',
        managerEmail: 'manager@manager.com'
      },
      wishlistSuccess: false, // Tracks success of wishlist addition
      wishlistError: null, // Tracks error message, if any
      cartSuccess: false, // Tracks success of adding to cart
      cartError: null, // Tracks error message, if any
    };

  },
  watch: {
    gameDetails: {
      immediate: true, // Trigger immediately when the watcher is registered
      handler(newVal) {
        if (newVal && newVal.name) {
          //this.fetchReviewsForGame(newVal.name);
        }
      }
    }
  },
  async created() {
    // Retrieve the role from sessionStorage
    this.role = state.loggedIn; // Update local role
    const storedEmail = sessionStorage.getItem('Email')
    const storedName = sessionStorage.getItem('Name')


    if (storedEmail){
      this.newReview.customerEmail = storedEmail;
    }
    // Fetch game details by name from the route parameter

    try {
      const gameName = this.$route.params.gameNameNoSpace;
      const gameResponse = await Game.findGameByName(gameName);
      console.log("Game page of : ")
      console.log(gameName)

      if (gameResponse.error) {
        console.error(gameResponse.error);
      } else {
        this.gameDetails = gameResponse; // This will trigger the watcher
      }
    } catch (error) {
      console.error("Error fetching game details:", error);
    }

    this.fetchReviewsForGame(this.gameDetails.name);
  },
  methods: {
    async addToWishlist() {
      try {
        const gameWishlist = new GameWishlist(this.gameDetails.name, sessionStorage.getItem('Email'));
        const response = await gameWishlist.addGameToWishlist();
        if (response.error) {
          throw new Error(response.error);
        }
        this.wishlistSuccess = true;
        this.wishlistError = null;
      } catch (error) {
        this.wishlistError = `Failed to add to wishlist: ${error.message}`;
        this.wishlistSuccess = false;
      }
    },
    async addToCart() {
      try {
        const cart = new Cart(sessionStorage.getItem("customerId"), this.gameDetails.name);
        const response = await cart.addGameToCart();
        if (response.error) {
          throw new Error(response.error);
        }
        this.cartSuccess = true;
        this.cartError = null;
      } catch (error) {
        this.cartError = `Failed to add to cart: ${error.message}`;
        this.cartSuccess = false;
      }
    },
    async fetchReviewsForGame(gameName) {
      try {
        const reviewsResponse = await Game.getReviews(gameName);
        if (reviewsResponse.error) {
          console.error(reviewsResponse.error);
        } else {
          this.reviewsOfGame = reviewsResponse;
        }
      } catch (error) {
        console.error("Error fetching reviews:", error);
      }
    },
    async postReview() {
      try {
        // Post review

        await axios.post("/reviews", {
          description: this.newReview.description,
          stars: this.newReview.stars,
          likes: this.newReview.likes,
          dislikes: this.newReview.dislikes,
          reply: "",
          customerEmail: sessionStorage.getItem('Email'),
          managerEmail: "manager@manager.com"
        });
        //console.log("Review added successfully:", game);
        // Update reviews and reset form
        this.newReview.description = '';
        this.newReview.stars = null;

      }catch (error){
        console.error("Error adding review:", error);
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
            const updatedReview = this.reviewsOfGame.find((review) => review.id === reviewId);
            if (updatedReview) {
              updatedReview.reply = replyText; // Update the reply in the review
            }
          }
        } catch (error) {
          console.error("Error replying to review:", error.message);
        }
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
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center; /* Center content horizontally */
  gap: 2em; /* Add spacing between sections */
  width: 100%;
  margin: 0 auto; /* Center layout in the viewport */
  padding: 20px;
  background-color: #2e2e2e;

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
  background-color: #2e2e2e;
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
  background-color: #2e2e2e;
}
.add-wishlist-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.add-wishlist-button:hover {
  background-color: #45a049;
}

.success-message {
  color: green;
  margin-top: 10px;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
