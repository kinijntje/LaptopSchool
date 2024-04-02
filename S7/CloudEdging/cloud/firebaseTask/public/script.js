"use strict";

import { initializeApp } from "https://www.gstatic.com/firebasejs/9.0.2/firebase-app.js";
import { getFirestore, collection, addDoc, getDocs } from "https://www.gstatic.com/firebasejs/9.0.2/firebase-firestore.js";



// TODO: Replace the following with your app's Firebase project configuration
// See: https://support.google.com/firebase/answer/7015592
const firebaseConfig = {
    apiKey: "AIzaSyCQ92BjkZsN4A6tBLXEEIF0OQx7SMr4V7E",
    authDomain: "cloudtask-dea06.firebaseapp.com",
    projectId: "cloudtask-dea06",
    storageBucket: "cloudtask-dea06.appspot.com",
    messagingSenderId: "340742568099",
    appId: "1:340742568099:web:8f3c8c22a93afd8f95e716",
    measurementId: "G-ZRQ3NZ5XPT"
  };

const app = initializeApp(firebaseConfig);


 // Initialize Cloud Firestore and get a reference to the service
const db = getFirestore(app);

document.addEventListener("DOMContentLoaded", init);

function init() {
    var generateButton = document.getElementById("generate");
    var saveButton = document.getElementById("save");
    var savedQuotesContainer = document.querySelector(".saved");

    generateButton.addEventListener("click", generateFunction);
    saveButton.addEventListener("click", saveFunction);

    function generateFunction() {
        // Make a GET request to the API
        fetch("https://api.whatdoestrumpthink.com/api/v1/quotes/random")
            .then(response => response.json())
            .then(data => {
                // Display the quote in the console (you can modify this to display it in your HTML)
                console.log(data.message);
                document.querySelector(".quote").innerHTML = data.message
            })
            .catch(error => {
                console.error("Error fetching quote: ", error);
            });
    }

    async function saveFunction() {
        // Get the quote from the HTML element
        const quote = document.querySelector(".quote").textContent;

        // Save the quote to Firestore
        try {
            const quotesCollectionRef = collection(db, "quotes");
            await addDoc(quotesCollectionRef, {
                quote: quote
            });

            // Log success
            console.log("Quote saved to Firestore:", quote);
        } catch (error) {
            // Handle errors
            console.error("Error saving quote:", error);
        }

        displaySavedQuotes();
    }

    async function displaySavedQuotes() {
        document.querySelector(".saved").innerHTML = "";
        try {
            const querySnapshot = await getDocs(collection(db, "quotes"));
            querySnapshot.forEach(doc => {
                const quote = doc.data().quote;
                const quoteElement = document.createElement("p");
                quoteElement.textContent = quote;
                savedQuotesContainer.appendChild(quoteElement);
            });
        } catch (error) {
            // Handle errors
            console.error("Error fetching saved quotes:", error);
        }
    }

    displaySavedQuotes();
}
