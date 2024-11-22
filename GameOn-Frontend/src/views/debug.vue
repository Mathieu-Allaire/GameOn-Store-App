<template>
    <button @click="addCategory">add category</button>
    <button @click="addGame">add game</button>
    <button @click="addGame2">add game 2</button>
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
            const game = new Game("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.digiseller.ru%2Fpreview%2F944309%2Fp1_2905629_0f1a73c3.jpg&f=1&nofb=1&ipt=a43825dd9752903257fd1f51adcaad0c24f0bbec040c84fd2da819426c8cf69f&ipo=images",
              "GTA", "Fun game", 30, 23, "Action"); //DTO
            game.createGame(); //Send to server
        },
        addGame2() {
            const game = new Game("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.giantbomb.com%2Fa%2Fuploads%2Fscale_medium%2F1%2F16944%2F2918030-half-life-logo.jpg&f=1&nofb=1&ipt=917a06520cb38a89f283396fe67a90ea364dee8600322995f22d9584e2b340a7&ipo=images",
              "Half-life", "Old game", 5, 10, "Action"); //DTO
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
        async deleteGames() {
            await Game.deleteGame("GTA");
            await Game.deleteGame("Half-life");
        },
        async deleteCategory() {
            await Category.deleteCategory("Action");
        },
    },
};
</script>
