package com.example.Samar.capstonetwo.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.activities.FavouriteDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class SamarWidget extends AppWidgetProvider {

    public static final String LOG=SamarWidget.class.getName();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {



            // Set up the intent that starts the ListViewService, which will

            // provide the views for this collection.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
            Intent intent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(appWidgetIds[i],R.id.list_view, intent);

            // Set the PlantDetailActivity intent to launch when clicked
            Intent appIntent = new Intent(context,FavouriteDetailActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.list_view, appPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);


        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }





}
