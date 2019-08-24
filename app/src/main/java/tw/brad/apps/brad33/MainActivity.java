package tw.brad.apps.brad33;
//玩ExpandableListView可以擴充view
//版面的展開跟縮小
//layout :5支 child,child2,cildx,parent(訂單結案畫面),activity
//drawable=>vector assit => 抓安卓內建的符號圖案
//Build =>gen =>api
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expList;  //展開view宣告
    private LinkedList<HashMap<String,String>> groups;//宣告資料有很多
    private LinkedList<HashMap<String,String>> items1;//一個裡面的子項目
    private LinkedList<HashMap<String,String>> items2;//一個裡面的子項目
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expList = findViewById(R.id.expList); //抓到ExpandableListView 的view
        init();//宣告初始化方法
    }
    //初始話設定
    private void init(){
        groups = new LinkedList<>();
        items1 = new LinkedList<>();
        items2 = new LinkedList<>();

        //設置2個項目上去
        HashMap<String,String> gitem1 = new HashMap<>(); //取得HashMap物件實體
        gitem1.put("group", "訂單");//掛上訂單屬性
        groups.add(gitem1);//設置這個資料結上去
        HashMap<String,String> gitem2 = new HashMap<>();
        gitem2.put("group", "結案");
        groups.add(gitem2);

        //創建40筆資料
        for (int i=0; i<40; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("title", "Item1:" + i);
            items1.add(row);
        }
        //創建50筆資料
        for (int i=100; i<150; i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("title", "Item2:" + i);
            items2.add(row);
        }

        //用調變器來處理
        adapter = new MyAdapter(); //宣告我自己寫的adapter
        expList.setAdapter(adapter);//設置調變器(這次用BaseExpandableListAdapter)
        expList.expandGroup(0);//展開第幾個grounp(第幾個)
        expList.expandGroup(1);

    }
    //用BaseExpandableListAdapter來放,處理抽象方法
    private class MyAdapter extends BaseExpandableListAdapter {

        //有幾個Group
        @Override
        public int getGroupCount() {
            return groups.size();
        }
        //小朋友幾群
        @Override
        public int getChildrenCount(int i) {
            if (i==0){ //如果i是0項目
                return  items1.size();
            }else if (i==1){
                return  items2.size(); //如果是第一項目
            }
            return 0;
        }
        //取得group
        @Override
        public Object getGroup(int i) {
            return groups.get(i);
        }

        @Override
        public Object getChild(int i, int j) {
            if (i==0){
                return items1.get(j);
            }else if (i==1){
                return items2.get(j);
            }
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i*1000 + i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
        //取得groupview爸爸畫面(1.你是第幾個group2)
        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {//(1.項目2.boolean3.畫面,畫面group)
            LayoutInflater inflater = getLayoutInflater(); //取得inflater物件
            View groupView = inflater.inflate(R.layout.item_parent,viewGroup,false); //叫出浮現畫面(1"畫面資料區",2.viewGroup 3.詳細介紹)

            TextView title = (TextView) groupView.findViewById(R.id.group_title);//取得文字畫面
            title.setText(groups.get(i).get("group"));//把groups畫面顯示出來

            return groupView;
        }
        //取得兒子畫面3個1.item 2.電話圖案 3.iem2資料庫紫色區
        @Override
        public View getChildView(int i, int j, boolean b, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater(); //取得 inflater物件實體
            View childView = null;
//            View childView = inflater.inflate(R.layout.item_child,viewGroup,false);
//            TextView title = (TextView) childView.findViewById(R.id.child_title);

            //如果到第5列用childx兒子畫面.item1的group
            if (i==0){ //如果是0巷目
                if (j % 5 == 0){ //除已5的部分加上childx_的電話圖案
                    childView = inflater.inflate(R.layout.item_childx, viewGroup, false);
                    TextView title = (TextView) childView.findViewById(R.id.childx_title); //抓到指定畫面id
                    title.setText(items1.get(j).get("title")); //顯示出來
             //其他用child畫面
                }else {
                    childView = inflater.inflate(R.layout.item_child, viewGroup, false);
                    TextView title = (TextView) childView.findViewById(R.id.child_title);
                    title.setText(items1.get(j).get("title"));
                }

                //剩下其他使用child2的iem2資料群
            }else{
                childView = inflater.inflate(R.layout.item_child2,viewGroup,false);

                TextView title = (TextView) childView.findViewById(R.id.child2_title);
                title.setText(items2.get(j).get("title"));
            }
            return childView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

}