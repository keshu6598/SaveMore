package inc.developer.vivonk;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import inc.developer.vivonk.savemore.R;

public class Main2Activity extends AppCompatActivity {
    AlertDialog.Builder dialogOfList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
