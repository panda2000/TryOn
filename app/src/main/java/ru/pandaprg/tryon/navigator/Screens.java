package ru.pandaprg.tryon.navigator;

import android.content.Context;
import android.content.Intent;

import ru.pandaprg.tryon.ui.activity.camera_feature.VideoActivity;
import ru.pandaprg.tryon.ui.activity.gallery.GalleryActivity;

public class Screens {

    public static final class StartVideo {
        public Intent getActivityIntent (Context context) {
            return VideoActivity.getIntent(context);
        }
    }

    public static final class StartGallery {
        public Intent getActivityIntent (Context context) {
            return GalleryActivity.getIntent (context);
        }
    }
}
