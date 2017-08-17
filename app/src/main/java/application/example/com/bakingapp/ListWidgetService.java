package application.example.com.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Dell on 13-08-2017.
 */
public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new LisstRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
