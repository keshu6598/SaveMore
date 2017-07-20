package inc.developer.vivonk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.ULocale;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Locale;

import inc.developer.vivonk.MainActivity;
import inc.developer.vivonk.savemore.R;

/**
 * Created by vivonk on 19-07-2017.
 */

public class NotificationService extends Service {
    private NotificationCompat.Builder mNotification;
    PendingIntent mPendingintent;
    public NotificationService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate: In Notification Service Service started from mainActivity");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("service",true);
//        C:\Users\Vivo Nk\Documents\GitHub\SaveMore2\app\src\main\java\inc\developer\vivonk\savemore
        editor.apply();
        Log.e("TAG", "onCreate: <<<<<<<<<<<<<<<<<<< before notification");
        Intent resultIntent = new Intent(this, Main2Activity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
// Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);*/
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification = new NotificationCompat.Builder(NotificationService.this);
        mNotification.setAutoCancel(false);

        mNotification.setOngoing(true);
        mNotification.setContentTitle("Notification");

        mNotification.setSmallIcon(R.mipmap.ic_launcher);
        mNotification.setContentText("Am I visible");

        mNotification.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, mNotification.build());
//        StatusBarNotification[] statusBarNotification = notificationManager.getActiveNotifications();
        Log.e("TAG", "onCreate: >>>>>>>>>>>>>>>>>>>>>>>> after notification");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }
}
