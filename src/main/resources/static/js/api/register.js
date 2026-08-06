export async function register (data) {
    const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if(!response.ok){

        const error = await response.json();

        throw new Error(error.message);
    }

    return true;
}