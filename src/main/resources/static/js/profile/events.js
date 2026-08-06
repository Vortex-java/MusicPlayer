export function bindProfileEvents () {

    document
        .getElementById("album-page-back-button")
        .onclick = () => {
        history.back();
    };

    document
        .getElementById("profile-page-logout")
        .onclick = () => {
            window.dispatchEvent(new CustomEvent("logout"));
            window.dispatchEvent(new CustomEvent("Navigate", {
                detail:{
                    route:"login"
                }
            }))
    };

    document.getElementById("profile-page-username").innerHTML = localStorage.getItem("username");
}