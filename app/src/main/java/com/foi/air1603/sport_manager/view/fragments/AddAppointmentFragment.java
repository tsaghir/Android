package com.foi.air1603.sport_manager.view.fragments;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.helper.enums.AddAppointmentViewEnums;
import com.foi.air1603.sport_manager.presenter.AddAppointmentPresenter;
import com.foi.air1603.sport_manager.presenter.AddAppointmentPresenterImpl;
import com.foi.air1603.sport_manager.presenter.AppointmentPresenterImpl;
import com.foi.air1603.sport_manager.presenter.MyPlacesAppointmentPresenterImpl;
import com.foi.air1603.sport_manager.view.AddAppointmentView;
import com.foi.air1603.sport_manager.view.AddPlaceView;
import com.google.gson.Gson;

/**
 * Created by Korisnik on 28-Dec-16.
 */

public class AddAppointmentFragment extends Fragment implements AddAppointmentView {

    AddAppointmentPresenter addAppointmentPresenter;
    CalendarView calendar;
    private int id_place;
    private int yearGet;
    private int monthGet;
    private int dayGet;
    private int currentPickedDate;
    private Context context;
    private View view;
    private Place place = null;
    private Button btnAddAppointment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleAddAppointmentActivity));
        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_add_appointment, null);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if(bundle.containsKey("Place")) {
                String place_serialized = bundle.getString("Place");
                Place place = new Gson().fromJson(place_serialized, Place.class);
                this.place = place;
            }
            else if (bundle.containsKey("place_id")) {
                id_place = bundle.getInt("place_id");
            }
        }

        addAppointmentPresenter = new AddAppointmentPresenterImpl(this);
        this.view = view;
        initializeCalendar();
        btnAddAppointment = (Button) getActivity().findViewById(R.id.buttonSetAppointment);

        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                addAppointmentPresenter.validateAppointment();

            }

        });
        final EditText appointmentEndInput = (EditText) getActivity().findViewById(R.id.etAppointmentHourEnd);
        final EditText appointmentStartInput = (EditText) getActivity().findViewById(R.id.etAppointmentHourStart);
        appointmentStartInput.setFocusable(false);
        appointmentEndInput.setFocusable(false);
        appointmentStartInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selH = String.valueOf(selectedHour);
                        String selM = String.valueOf(selectedMinute);
                        if (selectedHour < 10) selH = "0" + selectedHour;
                        if (selectedMinute < 10) selM = "0" + selectedMinute;
                        appointmentStartInput.setText(selH + ":" + selM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.titleTimePicker));
                mTimePicker.show();

            }
        });

        appointmentEndInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selH = String.valueOf(selectedHour);
                        String selM = String.valueOf(selectedMinute);
                        if (selectedHour < 10) selH = "0" + selectedHour;
                        if (selectedMinute < 10) selM = "0" + selectedMinute;
                        appointmentEndInput.setText(selH + ":" + selM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.titleTimePicker));
                mTimePicker.show();
            }
        });
    }

    @Override
    public String getAppointmentStartFromEditText() {
        EditText appointmentStartInput = (EditText) getActivity().findViewById(R.id.etAppointmentHourStart);
        return appointmentStartInput.getText().toString();
    }

    @Override
    public String getAppointmentEndFromEditText() {
        EditText appointmentEndInput = (EditText) getActivity().findViewById(R.id.etAppointmentHourEnd);
        return appointmentEndInput.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initializeCalendar() {
        calendar = (CalendarView) view.findViewById(R.id.calendarViewAppointment);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        currentPickedDate = (int) (c.getTimeInMillis() / 1000);
        calendar.setMinDate(System.currentTimeMillis() - 1000);
        calendar.setFirstDayOfWeek(2);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                yearGet = year;
                monthGet = month;
                dayGet = day;

                if (currentPickedDate == getDate()) {
                    return;
                }
                currentPickedDate = getDate();
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getDate() {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearGet);
        c.set(Calendar.MONTH, monthGet);
        c.set(Calendar.DAY_OF_MONTH, dayGet);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);
    }

    @Override
    public String getMaxPlayer() {
        EditText maxPlayerInput = (EditText) getActivity().findViewById(R.id.etMaxplayer);
        return maxPlayerInput.getText().toString();
    }

    @Override
    public String getPass() {
        EditText passInput = (EditText) getActivity().findViewById(R.id.etPasswordA);
        return passInput.getText().toString();
    }

    @Override
    public int getCurrentDate() {
        return currentPickedDate;
    }

    @Override
    public int getIdPlace() {
        return id_place;
    }

    @Override
    public void displayError(AddAppointmentViewEnums textView, String message) {

        final TextInputLayout appointmentStartWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAppointmentHourStart);
        final TextInputLayout appointmentEndWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAppointmentHourStop);
        final TextInputLayout appointmentMaxPlayerWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiMaxPlayer);

        if (message.equals("Polje je obavezno")){
            message = getResources().getString(R.string.errorFieldNecessary);
        } else if (message.equals("Mora biti barem jedan sudionik")){
            message = getResources().getString(R.string.errorMinOneParticipant);
        }

        if (textView == AddAppointmentViewEnums.AppointmentTimeStart) {
            appointmentStartWrapper.setError(message);
        } else if (textView == AddAppointmentViewEnums.AppointmentTimeEnd) {
            appointmentEndWrapper.setError(message);
        } else if (textView == AddAppointmentViewEnums.MaxPlayers) {
            appointmentMaxPlayerWrapper.setError(message);
        }
    }

    @Override
    public void removeError(AddAppointmentViewEnums textView) {

        final TextInputLayout appointmentStartWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAppointmentHourStart);
        final TextInputLayout appointmentEndWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAppointmentHourStop);
        final TextInputLayout appointmentMaxPlayerWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiMaxPlayer);

        appointmentStartWrapper.setErrorEnabled(false);
        appointmentEndWrapper.setErrorEnabled(false);
        appointmentMaxPlayerWrapper.setErrorEnabled(false);
    }

    @Override
    public void returnResponseCode(int statusCode, String message) {
        if (statusCode == 200) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastTermAddedTrue), Toast.LENGTH_LONG).show();
            MyPlacesAppointmentPresenterImpl.updateAppointments = true;
            getFragmentManager().popBackStack();

        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastTermAddedFalse) + message, Toast.LENGTH_LONG).show();
        }
    }
}
