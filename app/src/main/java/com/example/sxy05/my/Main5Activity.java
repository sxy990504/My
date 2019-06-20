package com.example.sxy05.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.student.R;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends Activity implements AdapterView.OnItemClickListener {
    List<String> data = new ArrayList<String>();
    private String TAG = "MyList";
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );

        ListView listView = (ListView) findViewById( R.id.mylist );
        //init data
        for (int i = 0; i < 6;i++ ) {
            if (i == 1) {
                data.add( "数据库考试时间：7月1日 10点" );
            } else if (i == 2) {
                data.add( "概率论考试时间：7月2日 5点" );
            } else if (i == 3) {
                data.add( "马原考试时间：7月5日 10点" );
            } else if (i == 4) {
                data.add( "霍金考试时间：7月7日 12点" );
            }else if(i == 5) {
                data.add( "沟通与写作考试时间：7月11日 15点" );
            }
        }
    adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener(this);
}


    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        Log.i(TAG,"OnItemClick:position"+position);
        Log.i(TAG,"OnItemClick:parent"+listv);
        adapter.remove(listv.getItemAtPosition(position));
        //adapter.notifyDataSetChanged();
    }
}
