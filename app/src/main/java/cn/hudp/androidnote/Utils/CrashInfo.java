package cn.hudp.androidnote.Utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuDP on 2017/1/19.
 */

public class CrashInfo  implements Parcelable {
    private int UserID;//用户ID
    private String OccurDate;// 发生时间
    private String UploadDate;// 上传时间
    private String AppPackage;// 应用包名
    private String AppLevel;// 应用版本
    private String UsingTime; // 使用时长
    private int AppState; // 前后台状态
    private String PhoneType; // 设备类型   // 手机品牌
    private String PhoneVersion;// 系统版本
    private String PhoneROM;
    private String PhoneCPU;
    private String AppError;// 应用错误详情
    private String AppField;//预留字段
    private String AvailMemory;
    private String CrashTrigger;//触发原因

    public CrashInfo() {
    }

    protected CrashInfo(Parcel in) {
        UserID = in.readInt();
        OccurDate = in.readString();
        UploadDate = in.readString();
        AppPackage = in.readString();
        AppLevel = in.readString();
        UsingTime = in.readString();
        AppState = in.readInt();
        PhoneType = in.readString();
        PhoneVersion = in.readString();
        PhoneROM = in.readString();
        PhoneCPU = in.readString();
        AppError = in.readString();
        AppField = in.readString();
        AvailMemory = in.readString();
        CrashTrigger = in.readString();
    }

    public static final Creator<CrashInfo> CREATOR = new Creator<CrashInfo>() {
        @Override
        public CrashInfo createFromParcel(Parcel in) {
            return new CrashInfo(in);
        }

        @Override
        public CrashInfo[] newArray(int size) {
            return new CrashInfo[size];
        }
    };

    @Override
    public String toString() {
        return "CrashInfo{" +
                "UserID=" + UserID +
                ", \nOccurDate='" + OccurDate + '\'' +
                ", \nUploadDate='" + UploadDate + '\'' +
                ", \nAppPackage='" + AppPackage + '\'' +
                ", \nAppLevel='" + AppLevel + '\'' +
                ", \nUsingTime='" + UsingTime + '\'' +
                ", \nAppState=" + AppState +
                ", \nPhoneType='" + PhoneType + '\'' +
                ", \nPhoneVersion='" + PhoneVersion + '\'' +
                ", \nPhoneROM='" + PhoneROM + '\'' +
                ", \nPhoneCPU='" + PhoneCPU + '\'' +
                ", \nAppError='" + AppError + '\'' +
                ", \nAppField='" + AppField + '\'' +
                ", \nAvailMemory='" + AvailMemory + '\'' +
                ", \nCrashTrigger='" + CrashTrigger + '\'' +
                '}';
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getOccurDate() {
        return OccurDate;
    }

    public void setOccurDate(String occurDate) {
        OccurDate = occurDate;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }

    public String getAppPackage() {
        return AppPackage;
    }

    public void setAppPackage(String appPackage) {
        AppPackage = appPackage;
    }

    public String getAppLevel() {
        return AppLevel;
    }

    public void setAppLevel(String appLevel) {
        AppLevel = appLevel;
    }

    public String getUsingTime() {
        return UsingTime;
    }

    public void setUsingTime(String usingTime) {
        UsingTime = usingTime;
    }

    public int getAppState() {
        return AppState;
    }

    public void setAppState(int appState) {
        AppState = appState;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }

    public String getPhoneVersion() {
        return PhoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        PhoneVersion = phoneVersion;
    }

    public String getPhoneROM() {
        return PhoneROM;
    }

    public void setPhoneROM(String phoneROM) {
        PhoneROM = phoneROM;
    }

    public String getPhoneCPU() {
        return PhoneCPU;
    }

    public void setPhoneCPU(String phoneCPU) {
        PhoneCPU = phoneCPU;
    }

    public String getAppError() {
        return AppError;
    }

    public void setAppError(String appError) {
        AppError = appError;
    }

    public String getAppField() {
        return AppField;
    }

    public void setAppField(String appField) {
        AppField = appField;
    }

    public String getAvailMemory() {
        return AvailMemory;
    }

    public void setAvailMemory(String availMemory) {
        AvailMemory = availMemory;
    }

    public String getCrashTrigger() {
        return CrashTrigger;
    }

    public void setCrashTrigger(String crashTrigger) {
        CrashTrigger = crashTrigger;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UserID);
        dest.writeString(OccurDate);
        dest.writeString(UploadDate);
        dest.writeString(AppPackage);
        dest.writeString(AppLevel);
        dest.writeString(UsingTime);
        dest.writeInt(AppState);
        dest.writeString(PhoneType);
        dest.writeString(PhoneVersion);
        dest.writeString(PhoneROM);
        dest.writeString(PhoneCPU);
        dest.writeString(AppError);
        dest.writeString(AppField);
        dest.writeString(AvailMemory);
        dest.writeString(CrashTrigger);
    }
}
