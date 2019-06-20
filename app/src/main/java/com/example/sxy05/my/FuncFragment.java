package com.example.sxy05.my;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.student.R;

public class FuncFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_func,container);
    }

    @Override
    public  void  onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        TextView tv =(TextView)getView().findViewById(R.id.funcTextView1);
        tv.setText("备忘录");
    }
}
