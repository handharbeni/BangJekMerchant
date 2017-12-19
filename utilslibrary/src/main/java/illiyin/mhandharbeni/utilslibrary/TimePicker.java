package illiyin.mhandharbeni.utilslibrary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by root on 12/18/17.
 */

@SuppressLint("ValidFragment")
public class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private TextView txtEdit;

    @SuppressLint("ValidFragment")
    public TimePicker(TextView txtEdit){
        this.txtEdit = txtEdit;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        this.txtEdit.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
    }
}