package com.sky.android.common.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sky.android.common.app.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        mListAdapter = new ListAdapter(this);
        mListAdapter.setContents(buildList());
        listView.setAdapter(mListAdapter);

        mListAdapter.setOnItemSelectedListener(new ListAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {


                System.out.println(">>>>>>>>>>>>>>>>>> " + position);
            }

            @Override
            public void onNothingSelected() {

                System.out.println(">>>>>>>>>>>>>>>>>> onNothingSelected");
            }
        });

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListAdapter.notifyDataSetChanged();
            }
        });

        throw new NullPointerException("测试");
    }

    private List<String> buildList() {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("测试" + (i + 1));
        }
        return list;
    }
}
