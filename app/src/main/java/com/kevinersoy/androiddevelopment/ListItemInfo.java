package com.kevinersoy.androiddevelopment;

/**
 * Created by kevinersoy on 3/15/18.
 */

public class ListItemInfo {
    private String mTitle;
    private String mSubtitleOne;
    private String mSubtitleTwo;

    public ListItemInfo(String title, String subtitleOne, String subtitleTwo){
        mTitle = title;
        mSubtitleOne = subtitleOne;
        mSubtitleTwo = subtitleTwo;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitleOne() {
        return mSubtitleOne;
    }

    public void setSubtitleOne(String subtitleOne) {
        mSubtitleOne = subtitleOne;
    }

    public String getSubtitleTwo() {
        return mSubtitleTwo;
    }

    public void setSubtitleTwo(String subtitleTwo) {
        mSubtitleTwo = subtitleTwo;
    }

    public String toString(){
        return mTitle;
    }
}
