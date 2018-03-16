package com.kevinersoy.androiddevelopment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinersoy on 3/15/18.
 */

public class DataManager {
    private static DataManager mInstance = null;
    private List<ListItemInfo> mItems = new ArrayList<>();


    public static DataManager getInstance(){
        if (mInstance == null){
            mInstance = new DataManager();
            mInstance.initializeExampleItems();
        }
        return mInstance;
    }

    public List<ListItemInfo> getItems(){
        return mItems;
    }

    public int createNewItem(){
        ListItemInfo item = new ListItemInfo(null, null, null);
        mItems.add(item);
        return mItems.size() - 1;
    }

    public int createNewItem(String title, String subtitleOne, String subtitleTwo){
        int index = createNewItem();
        ListItemInfo item = getItems().get(index);
        item.setTitle(title);
        item.setSubtitleOne(subtitleOne);
        item.setSubtitleTwo(subtitleTwo);

        return index;
    }

    public int findItem(ListItemInfo item){
        for(int i = 0; i < mItems.size() - 1; i++){
            if(item.equals(mItems.get(i))){
                return i;
            }
        }
        return -1;
    }

    public void removeItem(int index){
        mItems.remove(index);
    }

    public void initializeExampleItems() {
        for(int i = 0; i < 21; i++) {
            mItems.add(new ListItemInfo("Title " + i, "Subtitle Upper" + i, "Subtitle Lower" + i));
        }
    }


}
