package com.simformsolutions.jenkinsdemo.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.simformsolutions.jenkinsdemo.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by paresh.mayani on 24/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder!=null)
            unbinder.unbind();
    }

    protected abstract void init();
    protected abstract int getLayoutResource();

    public void setToolbarTitle(String title) {

        try {
            TextView textView = findViewById(R.id.toolbar_title);
            textView.setText(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
