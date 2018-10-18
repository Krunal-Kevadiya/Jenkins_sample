package com.simformsolutions.jenkinsdemo;

public class MainPresenter {

    private final MainView view;

    MainPresenter(MainView view) {
        this.view = view;
    }

    public void onExitClicked() {
        view.exit();
    }
}
