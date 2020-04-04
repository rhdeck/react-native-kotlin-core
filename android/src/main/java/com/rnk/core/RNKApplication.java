package com.rnk.core
import android.app.Application;
import com.facebook.react.PackageList;
import java.util.List;
public class RNKApplication extends Application {
    @override
    public void onCreate() {
        super.onCreate();
        List<Object> packages = new PackageList(this, null).getPackages();
        for (pk : packages) {
            if pk instanceof RNKPackage {
                RNKPackage p = pk;
                RNKRegistry.instance.registerPackage(pk)
            }
        }
        RNKRegistry.instance.triggerEvent("application.create")

    }
}