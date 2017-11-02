package mcgee.temperatureconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener {

    //define variables for the widgets

    private TextView degreesLabel;
    private TextView fahrenheitLabel;
    private TextView celsiusLabel;
    private TextView celsiusConvertedLabel;
    private EditText fahrenheitET;

    //string instance variables

    private String fahrenheitString = "";
    private float fahrenheit;
    private float celsius;

    //format for degrees
    NumberFormat degrees = NumberFormat.getNumberInstance();

    private SharedPreferences savedValues;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to the widget
        degreesLabel = (TextView) findViewById(R.id.degreesLabel);
        fahrenheitLabel = (TextView) findViewById(R.id.fahrenheitLabel);
        celsiusLabel = (TextView) findViewById(R.id.celsiusLabel);
        celsiusConvertedLabel = (TextView) findViewById(R.id.celsiusConvertedLabel);
        fahrenheitET = (EditText) findViewById(R.id.fahrenheitET);

        //set listeners
        fahrenheitET.setOnEditorActionListener(this);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            convertAndDisplay();
        }

        return false;
    }

    private void convertAndDisplay() {
        fahrenheitString = fahrenheitET.getEditableText().toString();

        if(fahrenheitString.equals("")) {
            fahrenheit = 0;
        } else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }

        //convert fahrenheit to celsius
        celsius  = ((fahrenheit - 32) * 5 / 9);

        //display results
        celsiusConvertedLabel.setText(degrees.format(celsius) + (char)0x00B0);
    }

    @Override
    protected void onPause() {
        //save instance variables
        editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get the instance variables
        fahrenheitString = savedValues.getString("fahrenheitString", "");

        //set the fahrenheit amount on its widget
        fahrenheitET.setText(fahrenheitString);

        //calculate and display
        convertAndDisplay();
    }

}
