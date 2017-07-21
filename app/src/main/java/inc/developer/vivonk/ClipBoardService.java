package inc.developer.vivonk;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by vivonk on 20-07-2017.
 */

public class ClipBoardService extends Service  {
    ClipboardManager clipBoardManager;
    ClipData clipdata;
    ClipDescription clipDescription;
    private ClipboardManager.OnPrimaryClipChangedListener listener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: SErvice started to determine change in text" );
        clipBoardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        clipBoardManager.addPrimaryClipChangedListener(listener);
        if(clipBoardManager.hasPrimaryClip()) {
            clipdata = clipBoardManager.getPrimaryClip();
            clipDescription = clipBoardManager.getPrimaryClipDescription();
            Log.e(TAG, "onPrimaryClipChanged:******************************** start service " + clipdata.getItemAt(0).getText().toString());
        }
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

   /* @Override
    public void onPrimaryClipChanged() {

            clipdata = clipBoardManager.getPrimaryClip();
            clipDescription = clipBoardManager.getPrimaryClipDescription();
            Log.e(TAG, "onPrimaryClipChanged:******************************** onPrimaryClipChanged "+clipdata);

    }*/

   void  performClipboardCheck(){
           clipdata = clipBoardManager.getPrimaryClip();
           clipDescription = clipBoardManager.getPrimaryClipDescription();
           Log.e(TAG, "onPrimaryClipChanged:******************************** performClipBoardCheck  "+clipdata.getItemAt(0).getText().toString());

   }
}
