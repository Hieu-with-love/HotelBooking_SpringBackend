package hcmute.edu.vn.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtils {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L; // 5MB
    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern){
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file, String pattern){
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds the maximum limit of 5MB");
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, pattern)) {
            throw new IllegalArgumentException("Only permit jpg, png, gif, and jpeg files");
        }
    }

    public static String generateFileName(final String name){
        final DateFormat dateFormat = new java.text.SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, date, name);
    }
}
