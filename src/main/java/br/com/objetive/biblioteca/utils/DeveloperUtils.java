package br.com.objetive.biblioteca.utils;


public class DeveloperUtils {

    public static boolean isDeveloperMode() {
        if (!"1".equals(System.getProperty("com.senior.developermode"))) {
            return false;
        }
        return true;
    }

}
