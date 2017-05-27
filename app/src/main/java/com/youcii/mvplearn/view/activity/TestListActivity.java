package com.youcii.mvplearn.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TestListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

	@Bind(R.id.lv_test)
	ListView lv_test;

	private List<String> testArray = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_list);
		ButterKnife.bind(this);

		initArray();

		View view1 = new View(this);
		view1.setLayoutParams(new AbsListView.LayoutParams(MATCH_PARENT, 50));
		view1.setBackgroundResource(R.color.black);
		lv_test.addHeaderView(view1, null, true);
		View view2 = new View(this);
		view2.setLayoutParams(new AbsListView.LayoutParams(MATCH_PARENT, 50));
		view2.setBackgroundResource(R.color.pink);
		lv_test.addHeaderView(view2, null, true);

		lv_test.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testArray));
		lv_test.setOnItemClickListener(this);
	}

	private void initArray() {
		testArray.add("列表刷新");
		testArray.add("接口压力测试");
		testArray.add("diyView测试");

		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");
		testArray.add("111");

	}

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, TestListActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case 0:
				ListRefreshActivity.startActivity(this);
				break;
			case 1:
				HttpTestActivity.startActivity(this);
				break;
			case 2:
				DiyViewActivity.startActivity(this);
				break;
			default:
				break;
		}
	}
}
