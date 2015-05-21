package put.iwm.android.motionrecorder.fragments;

import android.widget.TextView;

/**
 * Created by Szymon on 2015-05-21.
 */
public class ViewHolder {

    private long id;
    private TextView idTextView;
    private TextView startDateTextView;
    private TextView finishDateTextView;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TextView getIdTextView() {
        return idTextView;
    }

    public void setIdTextView(TextView idTextView) {
        this.idTextView = idTextView;
    }

    public TextView getStartDateTextView() {
        return startDateTextView;
    }

    public void setStartDateTextView(TextView startDateTextView) {
        this.startDateTextView = startDateTextView;
    }

    public TextView getFinishDateTextView() {
        return finishDateTextView;
    }

    public void setFinishDateTextView(TextView finishDateTextView) {
        this.finishDateTextView = finishDateTextView;
    }
}
