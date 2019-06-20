package com.example.sxy05.my;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.asus.student.R;

import DBHelper.DBHelper;
import Student.StudentItem;
//import AddStudentActivity;
import Table.Table;

public class StudentListActivity extends ListActivity implements
        OnClickListener, OnItemClickListener, OnItemLongClickListener {

    private static final String TAG = "TestSQLite";
    private Button addStudent;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private List<Long> list;
    private Button kebiao;
    private Button kaoshi;
    private StudentDao dao;
    private StudentItem student;
    private Boolean isDeleteList = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );
        Log.e( TAG, "onCreate" );
        list = new ArrayList<Long>();
        student = new StudentItem();
        dao = new StudentDao( new DBHelper( this ) );
        addStudent = (Button) findViewById( R.id.btn_add_student );
        kebiao =(Button)findViewById( R.id.btn_kebiao_student ) ;
         kaoshi=(Button)findViewById( R.id.btn_kaoshi_student ) ;
        listView = getListView();

        // 为按键设置监听
        addStudent.setOnClickListener( this );
        kebiao.setOnClickListener( this );
        kaoshi.setOnClickListener( this );
        listView.setOnItemClickListener( this );
        listView.setOnItemLongClickListener( this );
        listView.setOnCreateContextMenuListener( this );

    }

    // 调用load()方法将数据库中的所有记录显示在当前页面
    @Override
    protected void onStart() {
        super.onStart();
        load();

    }

    public void onClick(View v) {
        // 跳转到添加信息的界面
        if (v == addStudent) {
            startActivity( new Intent( StudentListActivity.this, AddStudentActivity.class ) );
        }else if (v == kebiao){
                startActivity( new Intent( StudentListActivity.this, FragmeActivity.class ) );
        }else if (v ==kaoshi ){
            startActivity( new Intent( StudentListActivity.this, Main5Activity.class ) );
        }
    }

    // 创建菜单
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater( this ); //getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
    }

    // 对菜单中的按钮添加响应时间
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        student = (StudentItem) listView.getTag();
        Log.v( TAG, "TestSQLite++++student+" + listView.getTag() + "" );
        final long student_id = student.getId();
        Intent intent = new Intent();
        Log.v( TAG, "TestSQLite+++++++id" + student_id );
        switch (item_id) {
            /* 添加
            case R.id.add:
                startActivity(new Intent(this, AddStudentActivity.class));
                break;*/
            // 删除
            case R.id.delete:
                deleteStudentInformation( student_id );
                break;
            case R.id.look:
                // 查看学生信息
                Log.v( TAG, "TestSQLite+++++++look" + student + "" );
                intent.putExtra( "student", student );
                intent.setClass( this, ShowStudentActivity.class );
                this.startActivity( intent );
                break;
            case R.id.write:
                // 修改学生信息
                intent.putExtra( "student", student );
                intent.setClass( this, AddStudentActivity.class );
                this.startActivity( intent );
                break;
            default:
                break;
        }
        return super.onContextItemSelected( item );
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        StudentItem student = (StudentItem) dao.getStudentFromView( view, id );
        listView.setTag( student );
        registerForContextMenu( listView );
        return false;
    }

    // 点击一条记录是触发的事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isDeleteList) {
            student = dao.getStudentFromView( view, id );
            Log.e( TAG, "student*****" + dao.getStudentFromView( view, id ) );
            Intent intent = new Intent();
            intent.putExtra( "student", student );
            intent.setClass( this, ShowStudentActivity.class );
            this.startActivity( intent );
        } else {
            CheckBox box = (CheckBox) view.findViewById( R.id.cb_box );
            box.setChecked( !box.isChecked() );
            list.add( id );
        }
    }

    // 自定义一个加载数据库中的全部记录到当前页面的无参方法
    public void load() {
        DBHelper studentDBHelper = new DBHelper( StudentListActivity.this );
        SQLiteDatabase database = studentDBHelper.getWritableDatabase();
        cursor = database.query( Table.STUDENT_TABLE, null, null, null, null, null, Table.StudentColumns.MODIFY_TIME + " desc" );
        startManagingCursor( cursor );
        adapter = new SimpleCursorAdapter( this, R.layout.student_list_item, cursor, new String[]{Table.StudentColumns.ID, Table.StudentColumns.NAME, Table.StudentColumns.AGE, Table.StudentColumns.SEX, Table.StudentColumns.LIKES, Table.StudentColumns.PHONE_NUMBER, Table.StudentColumns.TRAIN_DATE}, new int[]{R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_stu_age, R.id.tv_stu_sex, R.id.tv_stu_likes, R.id.tv_stu_phone, R.id.tv_stu_traindate} );
        listView.setAdapter( adapter );
    }


    // 自定义一个利用对话框形式进行数据的删除

    private void deleteStudentInformation(final long delete_id) {
        // 利用对话框的形式删除数据
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "学员信息删除" ).setMessage( "确定删除所选记录?" ).setCancelable( false ).setPositiveButton( "确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int raws = dao.deleteStudentById( delete_id );
                isDeleteList = !isDeleteList;
                load();
                if (raws > 0) {
                    Toast.makeText( StudentListActivity.this, "删除成功!", Toast.LENGTH_LONG ).show();
                } else Toast.makeText( StudentListActivity.this, "删除失败!", Toast.LENGTH_LONG ).show();
            }
        } ).setNegativeButton( "取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        } );
        AlertDialog alert = builder.create();
        alert.show();
    }

}
