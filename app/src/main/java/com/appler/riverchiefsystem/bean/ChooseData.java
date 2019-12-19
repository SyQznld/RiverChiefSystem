package com.appler.riverchiefsystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ChooseData implements Parcelable {
    public static final Creator<ChooseData> CREATOR = new Creator<ChooseData>() {
        @Override
        public ChooseData createFromParcel(Parcel in) {
            return new ChooseData(in);
        }

        @Override
        public ChooseData[] newArray(int size) {
            return new ChooseData[size];
        }
    };
    private ChooseUserData.DataBean chooseUserData;
    private boolean isSelect;

    public ChooseData() {
    }

    public ChooseData(Parcel in) {
        isSelect = in.readByte() != 0;
    }

    public ChooseUserData.DataBean getChooseUserData() {
        return chooseUserData;
    }

    public void setChooseUserData(ChooseUserData.DataBean chooseUserData) {
        this.chooseUserData = chooseUserData;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "ChooseData{" +
                "chooseUserData=" + chooseUserData +
                ", isSelect=" + isSelect +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isSelect ? 1 : 0));
    }
}
