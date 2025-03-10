package hcmute.edu.vn.utils;

import hcmute.edu.vn.dto.request.EmailDetails;

import java.security.SecureRandom;
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
                + "Thank you!\n"
                +"Supported by: 4 con ong";
    }

    public static String generateRandomPassword(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        SecureRandom random = new SecureRandom();

        StringBuilder pass = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            pass.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return pass.toString();
    }

    public static String getResetPasswordBody(String name, String newPassword){
        return "Hello " + name + "\n"
                + "New your password is: " + newPassword+ "\n"
                + "Supported by: 4 con ong";
    }
}
