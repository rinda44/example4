package example.myapplication21;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/9/16 0016.
 */

public class RdContextMenu
{
    final String TAG = "RdContextMenu";

    //PopupWindow
    View contentview;
    PopupWindow popupWindow;

    //ListView
    LayoutInflater layoutInflater;
    MyListAdapter listAdapter;
    ArrayList<MyListItem> listData = new ArrayList<>();

    public RdContextMenu()
    {

    }
    public PopupWindow getPopupWindow()
    {
        return popupWindow;
    }

    public void addMenuItem(String option, String value)
    {
        MyListItem it = new MyListItem();
        it.option = option;
        it.value = value;
        listData.add(it);
    }

    public static class MyListItem
    {
        public String option;
        public String value;

    }

    //context ,所在的Activity
    //anchor， 点中的view
    //xOff，yOff，偏移量
    public  void show(Context context, View anchor, int xOff, int yOff)
    {
        layoutInflater = LayoutInflater.from(context);
        contentview = layoutInflater.inflate(R.layout.popup_test, null);

        //初始化窗口的内容
        initContentView();

        //创建popupwindow ,宽度恒定， 高度自适应
        popupWindow = new PopupWindow(contentview, 400, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor, xOff, yOff);
    }
    //初始化
    public void initContentView()
    {
        listAdapter = new MyListAdapter();
        ListView listView = (ListView)contentview.findViewById(R.id.id_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                MyListItem it = (MyListItem)listAdapter.getItem(position);
                OnItemClicked(view, it);
            }
        });
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
            if(convertView ==null)
            {
                convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            }
            //获取/显示数据
            MyListItem it = (MyListItem)getItem(position);
            ((TextView)convertView.findViewById(R.id.textView)).setText(it.option);

            return  convertView;
        }
    }
    public void OnItemClicked(View view, MyListItem it)
    {
        //关闭窗口
        popupWindow.dismiss();

        //回调
        if(listener != null)
        {
            listener.onMenuItemClicked(it.option, it.value);
        }
    }
    //回调
    public interface OnMenuItemClickedListener
    {
        public void onMenuItemClicked(String option, String value);
    }
    public OnMenuItemClickedListener listener;

}
