package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.sql.Date;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.registration.RegisterRequest;

public class RegisterFragment extends Fragment {

    private OnRegisterFragmentInteractionListener mListener;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private RadioButton maleRadioButton;
    private DatePicker dateOfBirthPicker;
    private Button registerButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        usernameEditText = (EditText) getView().findViewById(R.id.username_edit_text);
        passwordEditText  = (EditText) getView().findViewById(R.id.password_edit_text);
        repeatPasswordEditText = (EditText) getView().findViewById(R.id.password_repeat_edit_text);
        heightEditText = (EditText) getView().findViewById(R.id.height);
        weightEditText = (EditText) getView().findViewById(R.id.weight);
        maleRadioButton = (RadioButton) getView().findViewById(R.id.male_radio_button);
        dateOfBirthPicker = (DatePicker) getView().findViewById(R.id.date_of_birth_picker);
        registerButton = (Button) getView().findViewById(R.id.register_request_button);

        setupEventHandlers();
    }

    private void setupEventHandlers() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerRequestButtonClicked(v);
            }
        });

    }

    private void registerRequestButtonClicked(View v) {

        RegisterRequest registerRequest = constructRegisterRequest();

        mListener.onRegisterFragmentInteraction(registerRequest);
    }

    private RegisterRequest constructRegisterRequest() {

        Date dateOfBirth = constructDateOfBirth();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();

        if(height.isEmpty())
            height = "0";

        if(weight.isEmpty())
            weight = "0";

        RegisterRequest registerRequest = new RegisterRequest(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                repeatPasswordEditText.getText().toString(),
                maleRadioButton.isChecked(),
                dateOfBirth,
                Integer.parseInt(height),
                Integer.parseInt(weight)
        );

        return registerRequest;
    }


    private Date constructDateOfBirth() {

        long date = dateOfBirthPicker.getCalendarView().getDate();

        Date dateOfBirth = new Date(date);

        return dateOfBirth;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        mListener = (OnRegisterFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRegisterFragmentInteractionListener {

        public void onRegisterFragmentInteraction(RegisterRequest registerRequest);
    }

}
