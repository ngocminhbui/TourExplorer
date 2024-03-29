/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.cntn14.ngocminhbui.tourexplorer.Places.app.Places;

import android.graphics.Bitmap;


// A support class encapsulating the info for one book
public class Book
{
    private String title;
    private String description;
    private String ratingAvg;
    private String ratingTotal;
    private String priceList;
    private String priceYour;
    private String targetId;
    private Bitmap thumb;
    private String bookUrl;
    private int ID;
    
    public Book()
    {
        
    }
    
    
    public String getTitle()
    {
        return title;
    }
    
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    
    public String getDescription()
    {
        return description;
    }
    
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
    public String getRatingAvg()
    {
        return ratingAvg;
    }
    
    
    public void setRatingAvg(String ratingAvg)
    {
        this.ratingAvg = ratingAvg;
    }
    
    
    public String getRatingTotal()
    {
        return ratingTotal;
    }
    
    
    public void setRatingTotal(String ratingTotal)
    {
        this.ratingTotal = ratingTotal;
    }
    
    
    public String getPriceList()
    {
        return priceList;
    }
    
    
    public void setPriceList(String priceList)
    {
        this.priceList = priceList;
    }
    
    
    public String getPriceYour()
    {
        return priceYour;
    }
    
    
    public void setPriceYour(String priceYour)
    {
        this.priceYour = priceYour;
    }
    
    
    public String getTargetId()
    {
        return targetId;
    }
    
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
    
    public Bitmap getThumb()
    {
        return thumb;
    }
    
    
    public void setThumb(Bitmap thumb)
    {
        this.thumb = thumb;
    }
    
    
    public String getBookUrl()
    {
        return bookUrl;
    }
    
    
    public void setBookUrl(String bookUrl)
    {
        this.bookUrl = bookUrl;
    }
    
    
    public void recycle()
    {
        // Cleans the Thumb bitmap variable
        thumb.recycle();
        thumb = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
