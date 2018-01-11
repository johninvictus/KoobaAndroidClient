package com.invictus.nkoba.nkoba.adapters;

import android.app.Notification;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.NotificationModel;
import com.invictus.nkoba.nkoba.utils.DateTimeUtils;
import com.invictus.nkoba.nkoba.utils.VectorDrawableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invictus on 1/10/18.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationModel> models = new ArrayList<>();
    private Context context;

    public NotificationsAdapter(Context context, ArrayList<NotificationModel> models) {
        this.models = models;
        this.context = context;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_notification_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        holder.populate(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_notification_date;
        TextView text_notification_description;
        TimelineView timelineView;

        public ViewHolder(View view) {
            super(view);
            text_notification_date = view.findViewById(R.id.text_notification_date);
            text_notification_description = view.findViewById(R.id.text_notification_description);
            timelineView = view.findViewById(R.id.time_marker);
        }

        public void populate(NotificationModel model) {
            if (model.isActive()) {
                timelineView.setMarker(VectorDrawableUtils.getDrawable(context,
                        R.drawable.ic_marker_active, R.color.colorAccent));
            } else {
                timelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker),
                        ContextCompat.getColor(timelineView.getContext(), R.color.colorAccent));
            }

            text_notification_date.setText(DateTimeUtils.parseDateTime(model.getDate(),
                    "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
            text_notification_description.setText(model.getDescription());
        }
    }
}
