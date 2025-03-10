package hcmute.edu.vn.utils;

import java.util.UUID;

public class EmailUtils {

    public static String generateVerificationCode(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getVerifyAccountEmailBody(String name, String to, String token){
        return "Hello " + name + "\n\n"
                + "Please verify your account by clicking the link below: \n"
                + "http://localhost:8080/api/auth/verify-account?token=" + token + "\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Thank you!\n\n"
                +"Supported by: 4 con ong";
    }
}
