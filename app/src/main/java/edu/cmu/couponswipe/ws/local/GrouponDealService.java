package edu.cmu.couponswipe.ws.local;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import edu.cmu.couponswipe.ws.remote.ICouponswipeService;

/**
 * Created by lloyddsilva on 11/4/15.
 */
public class GrouponDealService extends Service {
    private static final String TAG = "GrouponDealService";

    public class GrouponDealServiceImpl extends IDealService.Stub
    {
//        @Override
//        public User authenticateUser(String ticker) throws RemoteException
//        {
//            Log.v(TAG, "getQuote() called for " + ticker);
//            return new User();
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate() called");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy() called");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.v(TAG, "onStart() called");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.v(TAG, "onBind() called");
        return new GrouponDealServiceImpl();
    }
}
