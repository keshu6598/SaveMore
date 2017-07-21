package inc.developer.vivonk;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;
    String clip;
    int count;
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
        clipBoardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        clipBoardManager.addPrimaryClipChangedListener(listener);
        if(clipBoardManager.hasPrimaryClip()) {

            clipdata = clipBoardManager.getPrimaryClip();
            clipDescription = clipBoardManager.getPrimaryClipDescription();

            clip=clipdata.getItemAt(0).getText().toString();
            sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            count = sharedPreferences.getInt("count", 0);
            if(!sharedPreferences.getString(Integer.toString(count),"").contains(clip)){
                count++;
                editor.putString(Integer.toString(count),clip);
                editor.putInt("count",count);
            }
            editor.apply();
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



    void  performClipboardCheck(){
       clipdata = clipBoardManager.getPrimaryClip();
       clipDescription = clipBoardManager.getPrimaryClipDescription();
       clip=clipdata.getItemAt(0).getText().toString();
       sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);

       SharedPreferences.Editor editor = sharedPreferences.edit();
       count = sharedPreferences.getInt("count", 0);
       if(!sharedPreferences.getString(Integer.toString(count),"").contains(clip)){
           count++;
           editor.putString(Integer.toString(count),clip);
           editor.putInt("count",count);
       }

       editor.apply();

    }
}
