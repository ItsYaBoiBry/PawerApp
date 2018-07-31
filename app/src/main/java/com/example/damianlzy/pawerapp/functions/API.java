package com.example.damianlzy.pawerapp.functions;

public class API {
    public API() {
    }

    public String getLogin() {
        String login = "http://www.ehostingcentre.com/pawer/loginuser.php";
        return login;
    }

    public String getRegister() {
        String register = "http://www.ehostingcentre.com/pawer/pawer_Registration.php";
        return register;
    }

    public String updateProfile() {
        String update = "http://www.ehostingcentre.com/pawer/updateprofile.php";
        return update;
    }

}
