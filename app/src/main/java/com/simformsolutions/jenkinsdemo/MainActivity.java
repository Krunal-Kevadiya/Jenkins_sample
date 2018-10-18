package com.simformsolutions.jenkinsdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simformsolutions.jenkinsdemo.base.BaseActivity;

import timber.log.Timber;

import static com.simformsolutions.jenkinsdemo.utils.ViewUtils.findById;

public class MainActivity extends BaseActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView appVersionView = findById(this, R.id.appVersionView);
        appVersionView.setText(getString(R.string.main_app_version, BuildConfig.VERSION_NAME));

        TextView appNameView = findById(this, R.id.appNameView);
        appNameView.setText(getString(R.string.main_app_name, getString(R.string.app_name)));

        TextView appVersionCodeView = findById(this, R.id.appVersionCodeView);
        appVersionCodeView.setText(getString(R.string.main_app_version_code, BuildConfig.VERSION_CODE));

        Button exitView = findById(this, R.id.exitView);
        exitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onExitClicked();
            }
        });

        presenter = new MainPresenter(this);

        Timber.i("Hello world - info log %s", "Paresh");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        setToolbarTitle(getString(R.string.app_name));
    }

    @Override
    public void exit() {
        finish();
    }
}
