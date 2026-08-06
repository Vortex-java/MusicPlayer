export function saveAuth(data){

    localStorage.setItem(
        "accessToken",
        data.accessToken
    );

    localStorage.setItem(
        "username",
        data.username
    );

    localStorage.setItem(
        "role",
        data.role
    );
}


function logout(){
    localStorage.removeItem("accessToken");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
}

window.addEventListener("logout", async () => {
    logout();
})