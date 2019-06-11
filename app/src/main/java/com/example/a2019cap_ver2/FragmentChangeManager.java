package com.example.a2019cap_ver2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FragmentChangeManager {
    FragmentManager manager;
    Map<String, Fragment> fragments;


    FragmentChangeManager(FragmentManager manager){
        this.manager = manager;

        fragments = new HashMap<>();
    }

    public void replaceFragment(String key){
        manager.beginTransaction().replace(R.id.container, fragments.get(key)).commit();
    }

    public void changeArgument(String key, Bundle b){
        Fragment f = fragments.get(key);
        if(f != null){
            f.setArguments(b);
            fragments.put(key, f);
        }else{
            Log.d("Error", "지정한 tag의 프래그먼트가 없습니다");
        }
    }

    public void putFragment(String key, Fragment f){
        fragments.put(key, f);
    }

    public Fragment getFragment(String key){
        return fragments.get(key);
    }

}
