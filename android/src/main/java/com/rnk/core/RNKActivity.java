package com.rnk.core;
import android.os.Bundle;
import com.facebook.react.ReactActivity;

public class RNKActivity extends ReactActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RNKRegistry.instance.trigger("activity.create");
    }
}