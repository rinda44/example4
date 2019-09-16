package example.myapplication21;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    final String TAG = "测试";
    MyListAdapter listAdapter;
    ArrayList<MyListItem> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Listview
        listAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.id_main_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                MyListItem it = (MyListItem) listAdapter.getItem(position);
                OnItemLongClickListener(view, it);
                return false;
            }
        });
        //演示数据
        demo();
    }

    public class MyListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return listData.size();
        }

        @Override
        public Object getItem(int position)
        {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.main_list_layout, parent, false);
            }
            //获取/显示数据
            MyListItem it = (MyListItem) getItem(position);
            TextView textView = (TextView) convertView.findViewById(R.id.id_textView2);
            textView.setText(it.text);

            return convertView;
        }
    }

    public static class MyListItem
    {
        public String text;
    }

    public void OnItemLongClickListener(View view, MyListItem it)
    {
        RdContextMenu menu = new RdContextMenu();
        menu.addMenuItem("发送个给朋友", "sendto");
        menu.addMenuItem("收藏", "favorite");
        menu.addMenuItem("删除", "remove");
        menu.addMenuItem("更多", "more");

        menu.show(this, view, view.getWidth() / 2, -view.getHeight() / 2);
        menu.listener = new RdContextMenu.OnMenuItemClickedListener()
        {
            @Override
            public void onMenuItemClicked(String option, String value)
            {
                Log.d(TAG, "点中了菜单项：" + value);
                OnClicked(option);
            }

        };

    }

    private void demo()
    {
        MyListItem it;
        it = new MyListItem();
        it.text = "坚持练习，保持热爱，时间会报答你的努力。";
        listData.add(it);

        it = new MyListItem();
        it.text = "编程是一门职业技术，不要以混过考试的目的来学习编程。";
        listData.add(it);

        it = new MyListItem();
        it.text = "必须坚持每天都学都练，三天打渔两天晒网是学不会的。";
        listData.add(it);

        listAdapter.notifyDataSetChanged();
    }
    public void OnClicked(String option)
    {
        Toast.makeText(this,"点中了:" + option, Toast.LENGTH_SHORT).show();
    }
}
