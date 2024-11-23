<template>
    <button @click="addCategory">add category</button>
    <button @click="addGame">add game</button>
    <button @click="deleteCategory">delete category</button>
    <button @click="deleteGame">delete game</button>
    <button @click="update">update</button>

    <main>{{ description_to_display }}</main>
</template>

<script>
import axios from "axios";
import { Game } from "../dto/Game";
import { Category } from "../dto/Category";
import { GameResponseDTO } from "../dto/GameResponseDTO";

//Example use without error handling
//Add category, add game, update. Then you can clear your database with the delete buttons

export default {
    data() {
        return {
            description_to_display: "tbd",
        };
    },
    methods: {
        addGame() {
            const game = new Game("url", "GTA", "Fun game", 30, 23, "Action"); //DTO
            game.createGame(); //Send to server
        },
        addCategory() {
            const category = new Category("Action"); //DTO
            category.createCategory(); //Send request to server
        },
        async update() {
            const response = await Game.findGameByName("GTA"); //Fetch from server
            const gameResponseDto = new GameResponseDTO(response); //Cast to an object (not necessary)
            this.description_to_display = gameResponseDto.description; //Get attributes from response
        },
        async deleteGame() {
            await Game.deleteGame("GTA");
        },
        async deleteCategory() {
            await Category.deleteCategory("Action");
        },
    },
};
</script>
