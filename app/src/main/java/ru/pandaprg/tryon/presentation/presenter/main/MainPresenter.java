package ru.pandaprg.tryon.presentation.presenter.main;

import ru.pandaprg.tryon.App;
import ru.terrakok.cicerone.Router;

public class MainPresenter {
    private Router router;

    public MainPresenter() {
        router = App.INSTANCE.getRouter();
    }

    public void start() {
        router.navigateTo( "StartVideo");
    }

}
