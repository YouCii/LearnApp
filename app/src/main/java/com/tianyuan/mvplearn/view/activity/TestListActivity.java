package com.tianyuan.mvplearn.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tianyuan.mvplearn.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_test)
    ListView lv_test;

    private List<String> testArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        ButterKnife.bind(this);

        initArray();
        lv_test.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testArray));
        lv_test.setOnItemClickListener(this);
    }

    private void initArray() {
        testArray.add("列表刷新");
        testArray.add("接口压力测试");
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, TestListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ListRefreshActivity.openActivity(this);
                break;
            case 1:
                HttpTestActivity.openActivity(this);
                break;
            default:
                break;
        }
    }
}
