package edu.cmu.couponswipe.sessions;

/**
 * Created by sparshith on 5/1/15.
 */
public class Current {

    public static String firstName;
    public static String lastName;
    public static String email;
    public static String phone;
    public static int prefDist;
    public static String prefCat;

    public static boolean isLoggedIn(){
        if(email == null){
            return false;
        }
        return true;
    }

    public static void logout(){
        firstName = null;
        lastName = null;
        email = null;
        phone = null;
        prefDist =0;
        prefCat = null;
    }
}
