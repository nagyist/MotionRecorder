package put.iwm.android.motionrecorder.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import put.iwm.android.motionrecorder.R;

/**
 * Created by Szymon on 2015-04-15.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private int selectedItemPosition;
    private Resources resources;

    public NavigationDrawerAdapter(Context context, int resource, String[] objects) {

        super(context, resource, objects);
        resources = context.getResources();
    }

    public void setSelectedItemPosition(int selectedItemPosition) {

        this.selectedItemPosition = selectedItemPosition;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView view = (TextView) super.getView(position, convertView, parent);

        view.setTextColor(resources.getColor(R.color.white));


        if (position == selectedItemPosition) {
            view.setBackgroundColor(resources.getColor(R.color.selected_item_color));

        }
        else {
            view.setBackgroundColor(resources.getColor(R.color.white));
            view.setTextColor(resources.getColor(R.color.selected_item_color));
        }

        return view;
    }
}
