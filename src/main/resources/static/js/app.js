import {bindMusicPlayerEvents} from "./player/events.js";
import {handleRoute} from "./router.js";

async function loadPlayer(){

    const player = document.getElementById("app-player");

    const response = await fetch("/pages/player.html");

    player.innerHTML = await response.text();

    /**/

    const player2 = document.getElementById("down-player");

    const response2 = await fetch("/components/down-player.html");

    player2.innerHTML = await response2.text();

    bindMusicPlayerEvents();
}

await loadPlayer();

await handleRoute();