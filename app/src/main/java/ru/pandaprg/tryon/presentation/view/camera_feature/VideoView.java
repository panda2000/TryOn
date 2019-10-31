package ru.pandaprg.tryon.presentation.view.camera_feature;

import android.view.ViewGroup;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface VideoView extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void setAlpha (float alpha);
    @StateStrategyType(AddToEndStrategy.class)
    void setLocalPicture (int id);
    @StateStrategyType(AddToEndStrategy.class)
    void getPictureParams ();
    @StateStrategyType(AddToEndStrategy.class)
    void setPictureParams (ViewGroup.LayoutParams params);
}
