package Utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Date;
import java.util.Set;

public class CookieUtils {

    private static final String COOKIE_FILE_PATH = "cookies.data";

    // Save cookies to a file
    public static void saveCookies(WebDriver driver) {
        try (FileOutputStream fileOut = new FileOutputStream(COOKIE_FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            Set<Cookie> cookies = driver.manage().getCookies();
            objectOut.writeObject(cookies);
            System.out.println("Cookies saved successfully to: " + COOKIE_FILE_PATH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Load cookies back
    @SuppressWarnings("unchecked")
    public static void loadCookies(WebDriver driver) {
        File file = new File(COOKIE_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No cookies file found. You must login once manually.");
            return;
        }

        try (FileInputStream fileIn = new FileInputStream(COOKIE_FILE_PATH);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            Set<Cookie> cookies = (Set<Cookie>) objectIn.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
            System.out.println("Cookies loaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
