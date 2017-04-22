package cn.hudp.androidnote;

import java.util.ArrayList;
import java.util.List;

import cn.hudp.androidnote.MVVM.MvvmActivity;
import cn.hudp.androidnote.StateView.StateViewActivity;
import cn.hudp.androidnote.ViewTouch1.ViewTouchActivity;

/**
 * 章节
 * Created by HuDP on 2017/4/13.
 */

public class ChapterData {
    public static List<Data> getDatas() {
        List<Data> datas = new ArrayList<>();
        datas.add(new Data("Android Button ImageView 多种背景", StateViewActivity.class));
        datas.add(new Data("Android StatusBus", null));
        datas.add(new Data("Android MVVM", MvvmActivity.class));
        datas.add(new Data("1.Android触摸事件传递机制", ViewTouchActivity.class));
        datas.add(new Data("2.Android View绘制流程", ViewTouchActivity.class));
        datas.add(new Data("3.Android 动画机制", ViewTouchActivity.class));
        datas.add(new Data("4.Support Annotation Library 使用详解", ViewTouchActivity.class));
        return datas;
    }

    public static class Data {
        String name;
        Class mClass;

        public Data(String name, Class mClass) {
            this.name = name;
            this.mClass = mClass;
        }

        public String getName() {
            return name;
        }

        public Class getmClass() {
            return mClass;
        }
    }
}
