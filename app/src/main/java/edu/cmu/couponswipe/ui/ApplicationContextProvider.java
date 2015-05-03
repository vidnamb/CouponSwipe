package edu.cmu.couponswipe.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by vidya on 5/2/15.
 */
public class ApplicationContextProvider extends Application {

    /**
     * Keeps a reference of the application context
     */
    private static Context appContext;

    public static Context getDealShortlistActivityContext() {
        return dealShortlistActivityContext;
    }

    public static void setDealShortlistActivityContext(Context mainActivityContext) {
        ApplicationContextProvider.dealShortlistActivityContext = mainActivityContext;
    }

    private static Context dealShortlistActivityContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    /**
     * Returns the application context
     * @return application context
     */
    public static Context getAppContext() {
        return appContext;
    }



}