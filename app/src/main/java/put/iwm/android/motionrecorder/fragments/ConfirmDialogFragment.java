package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Szymon on 2015-05-27.
 */
public class ConfirmDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    private String message;
    private String confirmButtonMessage;
    private String cancelButtonMessage;
    private NoticeDialogListener listener;

    public ConfirmDialogFragment() {
        super();
    }

    public ConfirmDialogFragment(String message, String confirmButtonMessage, String cancelButtonMessage) {
        this();
        this.message = message;
        this.confirmButtonMessage = confirmButtonMessage;
        this.cancelButtonMessage = cancelButtonMessage;
    }

    public ConfirmDialogFragment(String message, String confirmButtonMessage, String cancelButtonMessage, NoticeDialogListener listener) {
        this(message, confirmButtonMessage, cancelButtonMessage);
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message);

        builder.setPositiveButton(confirmButtonMessage, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogPositiveClick(ConfirmDialogFragment.this);
            }
        });

        builder.setNegativeButton(cancelButtonMessage, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNegativeClick(ConfirmDialogFragment.this);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {

            if(activity instanceof NoticeDialogListener)
                listener = (NoticeDialogListener) activity;
            else
                throw new ClassCastException();
        } catch (ClassCastException e) {
            System.err.println(activity.toString() + " must implement NoticeDialogListener");
        }
    }
}
