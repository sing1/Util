package sing.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sing.util.SelectorUtil;
import sing.utils.demo.ActAnimationUtil;
import sing.utils.demo.ActAppUtil;
import sing.utils.demo.ActDateTimeUtil;
import sing.utils.demo.ActKeyboardUtil;
import sing.utils.demo.ActLogUtil;
import sing.utils.demo.ActMatchUtil;
import sing.utils.demo.ActMobileUtil;
import sing.utils.demo.ActRandomTextView;
import sing.utils.demo.ActRoundImageView;
import sing.utils.demo.ActRoundProgressBar;
import sing.utils.demo.ActSelectUtil;
import sing.utils.demo.ActSharedPreferencesUtil;
import sing.utils.demo.ActSwipeBackLayout;
import sing.utils.demo.ActToastUtil;

public class MainActivity extends AppCompatActivity {

    private Activity context;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new MyAdapter(getList(), new OnItemClickListener() {
            @Override
            public void click(Class functionClass) {
                startActivity(new Intent(context,functionClass));
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    private List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("DateTimeUtil",ActDateTimeUtil.class));
        list.add(new MainBean("ToastUtil",ActToastUtil.class));
        list.add(new MainBean("LogUtil",ActLogUtil.class));
        list.add(new MainBean("SharedPreferencesUtil",ActSharedPreferencesUtil.class));
        list.add(new MainBean("AnimationUtil",ActAnimationUtil.class));
        list.add(new MainBean("SelectUtil",ActSelectUtil.class));
        list.add(new MainBean("RandomTextView",ActRandomTextView.class));
        list.add(new MainBean("RoundProgressBar",ActRoundProgressBar.class));
        list.add(new MainBean("SwipeBackLayout",ActSwipeBackLayout.class));
        list.add(new MainBean("RoundImageView",ActRoundImageView.class));
        list.add(new MainBean("AppUtil",ActAppUtil.class));
        list.add(new MainBean("KeyboardUtil",ActKeyboardUtil.class));
        list.add(new MainBean("MatchUtil",ActMatchUtil.class));
        list.add(new MainBean("MobileUtil",ActMobileUtil.class));
        return list;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<MainBean> list;
        private OnItemClickListener listener;
        public MyAdapter(List<MainBean> list, OnItemClickListener listener) {
            this.list = list;
            this.listener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(list.get(position).description);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView text;

            public MyViewHolder(final View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.text);

                StateListDrawable bg4 = SelectorUtil.getBgPressRadius(context,"#FFFFFF","#FFFFFF","#E5E5E5",new float[] {
                        0, 0,
                        0, 0,
                        0, 0,
                        0, 0
                });
                itemView.setBackground(bg4);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.click(list.get(getLayoutPosition()).functionClass);
                        }
                    }
                });
            }
        }
    }

    interface OnItemClickListener{
        void click(Class functionClass);
    }

    public class MainBean implements Serializable{

        public String description;
        public Class functionClass;

        MainBean(String description, Class functionClass) {
            this.description = description;
            this.functionClass = functionClass;
        }
    }
}
