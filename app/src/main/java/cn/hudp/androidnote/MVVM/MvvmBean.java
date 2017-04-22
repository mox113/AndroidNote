package cn.hudp.androidnote.MVVM;

/**
 * Created by HuDP on 2017/4/18.
 */

public class MvvmBean {
    public String name;
    public String sex;

    public MvvmBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
