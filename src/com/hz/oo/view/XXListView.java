package com.hz.oo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class XXListView extends ListView{    
    private static final int MAX_SCROLL = 200;    
    private static final float SCROLL_RATIO = 0.5f;// ×èÄáÏµÊý  
         
    private Context mContext;    
         
    public XXListView(Context context){    
        super(context);    
        mContext = context;    
    }    
         
    public XXListView(Context context, AttributeSet attrs){    
        super(context, attrs);    
        mContext = context;    
    }    
         
    public XXListView(Context context, AttributeSet attrs, int defStyle){    
        super(context, attrs, defStyle);    
        mContext = context;    
    }    
         
    @Override    
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){     
        int newDeltaY = deltaY;  
        int delta = (int) (deltaY * SCROLL_RATIO);  
        if (delta != 0) newDeltaY = delta;  
        return super.overScrollBy(deltaX, newDeltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, MAX_SCROLL, isTouchEvent);      
    }  
}  
