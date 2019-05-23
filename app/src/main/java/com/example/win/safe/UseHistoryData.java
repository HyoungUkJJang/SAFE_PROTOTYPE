package com.example.win.safe;

import android.os.Parcel;
import android.os.Parcelable;

public class UseHistoryData {
    public String getUseName() {
        return UseName;
    }

    public String getUseOpenTime() {
        return UseOpenTime;
    }

    public String getUseCloseTime() {
        return UseCloseTime;
    }

    public void setUseName(String useName) {
        UseName = useName;
    }

    public void setUseOpenTime(String useOpenTime) {
        UseOpenTime = useOpenTime;
    }

    public void setUseCloseTime(String useCloseTime) {
        UseCloseTime = useCloseTime;
    }

    public UseHistoryData(String useName, String useOpenTime, String useCloseTime) {
        UseName = useName;
        UseOpenTime = useOpenTime;
        UseCloseTime = useCloseTime;
    }
    public UseHistoryData(){}

   private  String UseName;
   private  String UseOpenTime;
   private String UseCloseTime;





}
