package hcmute.edu.vn.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {


    public static String formatVND(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            return "0 VND";
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }

    public static String formatVNDWithText(BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            return "0 VND";
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedAmount = formatter.format(amount);
        return formattedAmount.replace("₫", "").trim() + " VND";
    }

}
