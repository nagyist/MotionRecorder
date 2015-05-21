package put.iwm.android.motionrecorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.fragments.ViewHolder;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-20.
 */
public class TrainingAdapter extends ArrayAdapter<Training> {

    private DateFormat dataFormat;

    public TrainingAdapter(Context context, List<Training> trainings) {
        super(context, 0, trainings);
        dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Training training = getItem(position);

        ViewHolder viewHolder;

        //TODO zmienić na funkcje
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.training_list_item, parent, false);
            viewHolder.setId(training.getId());
            viewHolder.setIdTextView((TextView) convertView.findViewById(R.id.training_id));
            viewHolder.setStartDateTextView((TextView) convertView.findViewById(R.id.training_start_date));
            viewHolder.setFinishDateTextView((TextView) convertView.findViewById(R.id.training_finish_date));
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getIdTextView().setText("#" + String.valueOf(training.getId()));
        viewHolder.getStartDateTextView().setText(dataFormat.format(training.getStartDate()));
        viewHolder.getFinishDateTextView().setText(dataFormat.format(training.getFinishDate()));

        return convertView;
    }

    private void setupViewHolder(View convertView, ViewGroup parent) {
    }
}
