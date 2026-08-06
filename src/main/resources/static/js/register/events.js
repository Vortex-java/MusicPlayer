import {register} from "../api/register.js";

export function bindRegisterEvents(){

    document
        .getElementById("go-login")
        .onclick = ()=> {

        window.dispatchEvent(
            new CustomEvent("Navigate",{
                detail:{
                    route:"login"
                }
            })
        );
    };

    document
        .getElementById("register-form")
        .onsubmit = async (e)=>{

        e.preventDefault();

        const username =
            document.getElementById("register-username").value;

        const login =
            document.getElementById("register-login").value;

        const password =
            document.getElementById("register-password").value;

        try {

            await register({
                username,
                login,
                password
            });

            window.dispatchEvent(
                new CustomEvent("Navigate",{
                    detail:{
                        route:"login"
                    }
                })
            );

        } catch (error) {
            alert(error.message)
        }
    };
}