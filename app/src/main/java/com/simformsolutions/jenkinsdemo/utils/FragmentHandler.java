package com.simformsolutions.jenkinsdemo.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.simformsolutions.jenkinsdemo.R;

public class FragmentHandler {
    public void replaceFragment(Activity activity, Fragment fragment, Bundle bundle, boolean isAddToBackStack, String tag, ANIMATION_TYPE animationType){

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        switch (animationType){
            case SLIDE_IN_LEFT:
              //  fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right);
                break;

            case SLIDE_UP:
                ///fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,0,0);
                break;
        }

        fragmentTransaction.replace(R.id.container, fragment);

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public void addFragment(Activity activity,Fragment fragment,Fragment fragmentToTarget,Bundle bundle, boolean isAddToBackStack,String tag,int requestcode,ANIMATION_TYPE animationType){

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if(fragmentToTarget!=null){
            fragment.setTargetFragment(fragmentToTarget,requestcode);
        }

        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();

        switch (animationType){
            case SLIDE_IN_LEFT:
                //fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right);
                break;

            case SLIDE_UP:
               // fragmentTransaction.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_up,0,0);
                break;
        }

        fragmentTransaction.add(R.id.container,fragment);

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commit();
    }

    public void replaceChildFragment(int containerId , Fragment currentFragment,Fragment fragment,Bundle bundle,boolean isAddToBackStack,String tag,ANIMATION_TYPE animationType){

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = currentFragment.getChildFragmentManager().beginTransaction();
        switch (animationType){
            case SLIDE_IN_LEFT:
              //  fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right);
                break;

            case SLIDE_UP:
                ///fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,0,0);
                break;
        }

        fragmentTransaction.replace(containerId, fragment);

        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public enum ANIMATION_TYPE{
        SLIDE_IN_LEFT,SLIDE_UP,NONE
    }

    public void cleaerAllFragment(AppCompatActivity activity){
        activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
