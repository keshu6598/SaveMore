package inc.developer.vivonk;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import inc.developer.vivonk.savemore.R;

public class Main2Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int mCount;
    ListView listView;
    TextView tvDefault;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
        mCount = sharedPreferences.getInt("count", 0);
        listView = (ListView) findViewById(R.id.listView);
        tvDefault=(TextView)findViewById(R.id.textView);
        if(mCount==0){
            tvDefault.setVisibility(View.VISIBLE);
        } else{
            tvDefault.setVisibility(View.GONE);
        }
    }
}
