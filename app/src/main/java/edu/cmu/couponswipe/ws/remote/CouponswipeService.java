package edu.cmu.couponswipe.ws.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import edu.cmu.couponswipe.model.User;

/**
 * Created by lloyddsilva on 11/4/15.
 */
public class CouponswipeService extends Service {

    private static final String TAG = "CouponswipeService";

    public class CouponswipeServiceImpl extends ICouponswipeService.Stub
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
        return new CouponswipeServiceImpl();
    }
}
