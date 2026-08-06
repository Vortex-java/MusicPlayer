import {saveAuth} from "../api/storage.js";
import {authenticate} from "../api/login.js";

export function bindLoginEvents() {

    document
        .getElementById("go-register")
        .onclick = () => {

        window.dispatchEvent(
            new CustomEvent("Navigate", {
                detail:{
                    route:"register"
                }
            })
        );
    };

    document
        .getElementById("login-form")
        .onsubmit = async (e)=>{

        e.preventDefault();

        const login =
            document.getElementById("login-login").value;

        const password =
            document.getElementById("login-password").value;

        try {

            const data = await authenticate({
                login,
                password,
            });

            saveAuth(data);

            window.dispatchEvent(
                new CustomEvent("Navigate",{
                    detail:{
                        route:"home",
                        params:{
                            pushHistory:true
                        }
                    }
                })
            );


        } catch(error){
            alert(error.message);
        }
    };
}