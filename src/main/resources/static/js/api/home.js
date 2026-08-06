import {apiFetch} from "./apiFetch.js";

export async function getHomePage () {
    return await apiFetch("/api/home");
}