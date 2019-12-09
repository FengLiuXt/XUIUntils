package com.example.xuiuntils.InterfaceManagement;

import android.content.Context;
import android.view.View;

public abstract class ViewCenter {

    public View getView(Context context, String type){
        return resolver(context, type);
    }

    public abstract View resolver(Context context, String type);

}
