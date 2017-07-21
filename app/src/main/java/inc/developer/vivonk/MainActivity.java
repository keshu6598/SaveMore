package inc.developer.vivonk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import inc.developer.vivonk.savemore.R;

public class MainActivity extends AppCompatActivity {
    private boolean mService=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        mService = sharedPreferences.getBoolean("service", false);
        this.startService(new Intent(this, ClipBoardService.class));
        this.startService(new Intent(this, NotificationService.class));

    }
}
