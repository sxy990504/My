package com.example.sxy05.my;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.asus.student.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public  class MainActivity extends Activity {



    private EditText sxy_name;//用户名
    private EditText sxy_pwd;//密码
    private Button sxy_log;//登陆按钮
    private Button sxy_bos;//取消按钮
    private Button sxy_cxk;//花里胡哨按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //利用布局资源文件设置用户界面
        setContentView( R.layout.activity_main );

        //通过资源标识获得控件实例
        sxy_name = (EditText) findViewById( R.id.sxy_name );
        sxy_pwd = (EditText) findViewById( R.id.sxy_pwd );
        sxy_log = (Button) findViewById( R.id.sxy_log );
        sxy_bos = (Button) findViewById( R.id.sxy_bos );
        sxy_cxk = (Button) findViewById( R.id.sxy_cxk );

        //给登录按钮注册监听器，实现监听器接口，编写事件
        sxy_log.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入的数据
                String strUsername = sxy_name.getText().toString();
                String strPassword = sxy_pwd.getText().toString();

                //判断用户名和密码是否正确（为可以进行测试，将用户名和密码都定义为admin）
                if (strUsername.equals( "123" ) && strPassword.equals( "admin" )) {
                    Toast.makeText( MainActivity.this, "用户名和密码正确！", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent();
                    //(当前Activity，目标Activity)
                    intent.setClass( MainActivity.this, StudentListActivity.class );
                    startActivity( intent );
                } else {
                    Toast.makeText( MainActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT ).show();
                }

            }
        } );
        //给取消按钮注册监听器，实现监听器接口，编写事件
        sxy_bos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        sxy_cxk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //(当前Activity，目标Activity)
                intent.setClass( MainActivity.this, Main2Activity.class );
                startActivity( intent );
            }
        } );


    }
}