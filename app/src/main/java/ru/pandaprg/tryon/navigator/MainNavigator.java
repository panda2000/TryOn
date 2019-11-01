package ru.pandaprg.tryon.navigator;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

public class MainNavigator implements Navigator {
    private static final String TAG = "MainNavigator";
    private Resources res;

    private Context ctx;

    public MainNavigator (Context ctx){
        this.ctx =ctx;
        res = Resources.getSystem();
    }

    @Override
    public void applyCommands(Command[] commands) {
        Log.i(TAG, "applyCommands: ");
        for (Command command : commands){
            if (command instanceof Forward) {
                Forward forward = (Forward) command;

                Log.i(TAG, "applyCommands: "+forward.getScreenKey());
                if (forward.getScreenKey().equals("StartVideo")){
                    Log.i(TAG, "applyCommands: "+forward.getScreenKey());
                    ctx.startActivity( new Screens.StartVideo().getActivityIntent(ctx) );
                } else if (forward.getScreenKey().equals("StartGallery")){
                    Log.i(TAG, "applyCommands: "+forward.getScreenKey());
                    ctx.startActivity( new Screens.StartGallery().getActivityIntent(ctx) );
                }
            } else if (command instanceof BackTo) {
                BackTo backTo = (BackTo) command;
                Log.i(TAG, "applyCommands: BackTo");
                if (backTo.getScreenKey() == null) {
                    ctx.startActivity( new Screens.StartVideo().getActivityIntent(ctx) );
                }

            }
        }
    }
}
