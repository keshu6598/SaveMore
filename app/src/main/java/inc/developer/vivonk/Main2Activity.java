package inc.developer.vivonk;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

import inc.developer.vivonk.savemore.R;

import static android.content.ContentValues.TAG;

public class Main2Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int mCount;
    ListView listView;
    TextView tvDefault;
    ArrayList<String> arrayList;
    ClipboardManager clipBoardManager;
    ClipData clipdata;
    ArrayAdapter adapter;
    String string;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);*/
        setFinishOnTouchOutside(false);
        sharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
        mCount = sharedPreferences.getInt("count", 0);
        Log.e(TAG, "onCreate: mCount is .............."+mCount);
        listView = (ListView) findViewById(R.id.listView);
        tvDefault=(TextView)findViewById(R.id.textView);
        tvDefault.setVisibility(View.GONE);
        if(mCount==0){
            tvDefault.setVisibility(View.VISIBLE);
        } else{
            arrayList=new ArrayList<>();
            tvDefault.setVisibility(View.GONE);
            Vector<String> vector=new Vector<>();
            for(int i=mCount;i>0;i--){
                String string = sharedPreferences.getString(Integer.toString(i), "");
                Log.e(TAG, "performClipboardCheck: string "+i+" th is  --------->"+string);
                if(!vector.contains(string)&&!string.isEmpty()) {
                    arrayList.add(string);
                    vector.add(string);
                }
            }
            adapter=new ArrayAdapter<>(Main2Activity.this,R.layout.texxview,R.id.textView,arrayList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick( AdapterView<?> adapterView, View view, int i, long l) {
                    position=i;
                    string =adapterView.getItemAtPosition(i).toString();
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(Main2Activity.this);
                    alertDialog.setTitle("Action")
                            .setMessage("Choose Action")
                            .setCancelable(true)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Object toRemove = adapter.getItem(position);
                                    adapter.remove(toRemove);
                                    adapter.notifyDataSetChanged();
                                    mCount = sharedPreferences.getInt("count", 0);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    for(int size=sharedPreferences.getInt("count",0);size>0;size--){
                                        if(sharedPreferences.getString(Integer.toString(size),"null").contains(string)){
                                            Log.e(TAG, "onClick: mcount is "+mCount+ " size  string is "+ size+"--------"+string );
                                            editor.remove(Integer.toString(size));
                                        }
                                    }
                                    editor.apply();
                                    if(adapter.getCount()==0){
                                        tvDefault.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .setNegativeButton("Copy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    clipBoardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                                    clipdata=ClipData.newPlainText("clip",string);
                                    clipBoardManager.setPrimaryClip(clipdata);
                                    Toast.makeText(Main2Activity.this, " Copied ", Toast.LENGTH_SHORT).show();
                                    Object toRemove = adapter.getItem(position);
                                    adapter.remove(toRemove);
                                    adapter.notifyDataSetChanged();


                                        arrayList.add(0, clipdata.getItemAt(0).getText().toString());
                                        adapter.notifyDataSetChanged();
                                    if(adapter.getCount()==0){
                                        tvDefault.setVisibility(View.VISIBLE);
                                    }
                                }
                            }).create().show();
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    clipBoardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    clipdata=ClipData.newPlainText("clip",adapterView.getItemAtPosition(i).toString());
                    clipBoardManager.setPrimaryClip(clipdata);
                    Toast.makeText(Main2Activity.this, " Copied ", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onItemClick: item is at   "+i+" Item is looking  "+ adapterView.getItemAtPosition(i).toString());
                    Object toRemove = adapter.getItem(i);
                    adapter.remove(toRemove);
                    adapter.notifyDataSetChanged();
                    if(adapter.getCount()==0){
                        tvDefault.setVisibility(View.VISIBLE);
                    }
                    else {
                        arrayList.add(0, clipdata.getItemAt(0).getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }
}
