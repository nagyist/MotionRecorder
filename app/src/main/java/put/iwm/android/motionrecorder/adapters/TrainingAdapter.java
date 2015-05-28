package put.iwm.android.motionrecorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.fragments.ViewHolder;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-20.
 */
public class TrainingAdapter extends ArrayAdapter<Training> {

    private TextGenerator textGenerator;

    public TrainingAdapter(Context context, List<Training> trainings) {
        super(context, 0, trainings);
        textGenerator = new TextGenerator();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Training training = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {

            //viewHolder = buildViewHolder(training, convertView, parent);
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.training_list_item, parent, false);
            viewHolder.setId(training.getId());
            viewHolder.setIdTextView((TextView) convertView.findViewById(R.id.training_id));
            viewHolder.setStartDateTextView((TextView) convertView.findViewById(R.id.training_start_date));
            viewHolder.setFinishDateTextView((TextView) convertView.findViewById(R.id.training_finish_date));
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.getIdTextView().setText("#" + String.valueOf(training.getId()));
        viewHolder.getStartDateTextView().setText(textGenerator.createDateText(training.getStartDate()));
        viewHolder.getFinishDateTextView().setText(textGenerator.createDateText((training.getFinishDate())));

        return convertView;
    }

    private ViewHolder buildViewHolder(Training training, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.training_list_item, parent, false);
        viewHolder.setId(training.getId());
        viewHolder.setIdTextView((TextView) convertView.findViewById(R.id.training_id));
        viewHolder.setStartDateTextView((TextView) convertView.findViewById(R.id.training_start_date));
        viewHolder.setFinishDateTextView((TextView) convertView.findViewById(R.id.training_finish_date));
        convertView.setTag(viewHolder);

        return viewHolder;
    }
}
