package com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample;

import com.cntn14.ngocminhbui.tourexplorer.Model.UtilityType;

/**
 * Created by ngocminh on 5/24/17.
 */
public class Utility {
    public int type;
    public String displaytext;
    public String content;
    public int iconid;
    public Utility(int type, String displaytext, String content, int iconid) {
        this.type=type;
        this.displaytext=displaytext;
        this.content=content;
        this.iconid=iconid;
    }
}
