package tthanh.com.maddiscovery;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText txtEventName;
    private EditText txtEventLocation;
    private EditText txtEventDate;
    private EditText txtEventTime;
    private EditText txtEventOrganizer;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEventName = (EditText) findViewById(R.id.txtEventName);
        txtEventLocation = (EditText) findViewById(R.id.txtEventLocation);

        txtEventDate = (EditText) findViewById(R.id.txtEventDate);
        txtEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "DatepickerDialog");
            }
        });
        txtEventTime = (EditText) findViewById(R.id.txtEventTime);
        txtEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cTime = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        MainActivity.this,
                        cTime.get(Calendar.HOUR_OF_DAY),
                        cTime.get(Calendar.MINUTE),
                        true
                );
                tpd.show(getFragmentManager(), "TimepickerDialog");
            }
        });

        txtEventOrganizer = (EditText) findViewById(R.id.txtEventOrganizer);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = txtEventName.getText().toString();
                String eventLocation  = txtEventLocation.getText().toString();
                String eventDate = txtEventDate.getText().toString();
                String eventTime = txtEventTime.getText().toString();
                String eventOrganizer = txtEventOrganizer.getText().toString();
                Event event = new Event(eventName, eventLocation, eventDate, eventTime, eventOrganizer);
                List<String> errors = validate(event);
                if(errors.size() > 0){
                    StringBuilder sb = new StringBuilder();
                    for(String error : errors){
                        sb.append(error);
                        sb.append(System.lineSeparator());
                    }
                    alert(sb.toString());
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        txtEventDate.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time = hourOfDay + ":" + minute;
        txtEventTime.setText(time);
    }

    private List<String> validate(Event event){
        List<String> errors = new ArrayList<>();
        if(event.getEventName().isEmpty()){
            errors.add("Event Name is required!");
        }
        if(event.getEventDate().isEmpty()){
            errors.add("Event Date is required!");
        }
        if(event.getEventOrganizer().isEmpty()){
            errors.add("Event Organizer is required!");
        }
        return errors;
    }

    private void alert(String message){
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Errors");
        ab.setMessage(message).setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog ad = ab.create();
        ad.show();
    }
}
