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

    private static Context dealShortlistActivityContext;

    private static Context dealStackActivityContext;

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

    public static Context getDealShortlistActivityContext() {
        return dealShortlistActivityContext;
    }

    public static void setDealShortlistActivityContext(Context dealShortlistActivityContext) {
        ApplicationContextProvider.dealShortlistActivityContext = dealShortlistActivityContext;
    }

    public static Context getDealStackActivityContext() {
        return dealStackActivityContext;
    }

    public static void setDealStackActivityContext(Context dealStackActivityContext) {
        ApplicationContextProvider.dealStackActivityContext = dealStackActivityContext;
    }
}