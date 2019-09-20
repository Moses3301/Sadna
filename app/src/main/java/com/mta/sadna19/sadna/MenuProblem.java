package com.mta.sadna19.sadna;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuProblem implements Parcelable {

    private String mAdminNotes;
    private String mServiceName;
    private String mLastCallDialPath;
    private String mLastCallPath;
    private String mUserFreeText;
    private String mClassification;
    private String mStatus;
    private String mUserEmail;

    public MenuProblem() {
    }

    public String getmAdminNotes() {
        return mAdminNotes;
    }

    public void setmAdminNotes(String mAdminNotes) {
        this.mAdminNotes = mAdminNotes;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public String getmLastCallDialPath() {
        return mLastCallDialPath;
    }

    public void setmLastCallDialPath(String mLastCallDialPath) {
        this.mLastCallDialPath = mLastCallDialPath;
    }

    public String getmLastCallPath() {
        return mLastCallPath;
    }

    public void setmLastCallPath(String mLastCallPath) {
        this.mLastCallPath = mLastCallPath;
    }

    public String getmUserFreeText() {
        return mUserFreeText;
    }

    public void setmUserFreeText(String mUserFreeText) {
        this.mUserFreeText = mUserFreeText;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getmClassification() {
        return mClassification;
    }

    public void setmClassification(String mClassification) {
        this.mClassification = mClassification;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuProblem> CREATOR = new Creator<MenuProblem>() {
        @Override
        public MenuProblem createFromParcel(Parcel in) {
            return new MenuProblem(in);
        }

        @Override
        public MenuProblem[] newArray(int size) {
            return new MenuProblem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLastCallDialPath);
        dest.writeString(mLastCallPath);
        dest.writeString(mServiceName);
        dest.writeString(mAdminNotes);
        dest.writeString(mUserFreeText);
        dest.writeString(mClassification);
        dest.writeString(mStatus);
        dest.writeString(mUserEmail);
    }


    protected MenuProblem(Parcel in) {
        mLastCallDialPath = in.readString();
        mLastCallPath = in.readString();
        mServiceName = in.readString();
        mAdminNotes = in.readString();
        mUserFreeText = in.readString();
        mClassification = in.readString();
        mStatus = in.readString();
        mUserEmail = in.readString();
    }

    @Override
    public String toString() {
        return "MenuProblem{" +
                "mAdminNotes='" + mAdminNotes + '\'' +
                ", mServiceName='" + mServiceName + '\'' +
                ", mLastCallDialPath='" + mLastCallDialPath + '\'' +
                ", mLastCallPath='" + mLastCallPath + '\'' +
                ", mUserFreeText='" + mUserFreeText + '\'' +
                ", mClassification='" + mClassification + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mUserEmail='" + mUserEmail + '\'' +
                '}';
    }
}