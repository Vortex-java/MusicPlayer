import {API_URL} from "./config.js";

export async function apiFetch(url, options = {}) {
    const token = localStorage.getItem("accessToken");

    const headers = {
        "Content-Type": "application/json",
        ...options
    };

    if (token) {
        headers.Authorization = `Bearer ${token}`
    }

    const response = await fetch (
        `${API_URL}${url}`,
        {
            ...options,
            headers
        }
    );

    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("accessToken");

        window.dispatchEvent(
            new CustomEvent("Navigate", {
                detail: {
                    route: "login"
                }
            })
        );

        throw new Error("Unauthorized");
    }

    if (!response.ok) {
        const error = await response.json();

        throw new Error(error.message);
    }

    return response.json();
}