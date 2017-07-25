package inc.developer.vivonk;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import inc.developer.vivonk.savemore.*;

/**
 * Created by vivonk on 24-07-2017.
 */

public class FloatBubbleService extends Service {
    private WindowManager windowManager;
    private ImageView iconBubble;
    private boolean check=false;
    private int prevX,prevY;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,START_FLAG_REDELIVERY,startId);
        check=false;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        iconBubble = new ImageView(this);

        iconBubble.setImageResource(R.drawable.icon);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                (int)getResources().getDimension(R.dimen.icon_width),
                (int)getResources().getDimension(R.dimen.icon_height),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 200;
        prevX = 0;
        prevY = 200;
        windowManager.addView(iconBubble, params);

        try {
            iconBubble.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // Get current time in nano seconds.
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            check=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            check=true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(iconBubble, paramsF);
                            check=true;
                            break;
                    }

                    return false;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        iconBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    check=false;
                    startActivity(new Intent(FloatBubbleService.this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        startService(new Intent(FloatBubbleService.this,FloatBubbleService.class));
    }
}
