package cn.hudp.androidnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        setAdapter(ChapterData.getDatas());
    }

    public void setAdapter(final List<ChapterData.Data> datas) {
        if (adapter == null) {
            rvContent.setLayoutManager(new LinearLayoutManager(this));
            rvContent.setItemAnimator(new DefaultItemAnimator());
            rvContent.setHasFixedSize(true);
            adapter = new BaseQuickAdapter<ChapterData.Data, BaseViewHolder>(R.layout.item_main, datas) {
                @Override
                protected void convert(BaseViewHolder helper, final ChapterData.Data item) {
                    final String name = item.getName();
                    final Class clz = item.getmClass();
                    helper.setText(R.id.tv_title, name);
                    helper.setOnClickListener(R.id.tv_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clz != null) {
                                startActivity(new Intent(MainActivity.this, clz));
                            } else {
                                Toast.makeText(mContext, "暂无" + name, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            };
            adapter.loadMoreEnd();
            rvContent.setAdapter(adapter);
        } else {
            adapter.setNewData(datas);
        }
    }
}
