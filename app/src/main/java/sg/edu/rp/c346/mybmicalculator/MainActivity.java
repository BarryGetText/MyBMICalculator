package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    EditText etWeight,etHeight;
    Button btnCalculate,btnReset;
    TextView tvDate,tvBmi,tvWeightType;
    String weightType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight=findViewById(R.id.editTextWeight);
        etHeight=findViewById(R.id.editTextHeight);

        btnCalculate=findViewById(R.id.buttonCalculate);
        btnReset=findViewById(R.id.buttonReset);

        tvDate=findViewById(R.id.textViewDate);
        tvBmi=findViewById(R.id.textViewBMI);
        tvWeightType=findViewById(R.id.textViewWeightType);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWeight = etWeight.getText().toString();
                String strHeight= etHeight.getText().toString();

                Float fltWeight = Float.parseFloat(strWeight);
                Float fltHeight = Float.parseFloat( strHeight);

                Float BMI = fltWeight/(fltHeight*fltHeight);
                if(BMI<18.5){
                    weightType="You are under weight";
                }
                else if(BMI<25){
                    weightType="You are normal";
                }
                else if(BMI<30){
                    weightType="You are overweight";
                }
                else if(BMI>=30){
                    weightType="You are obese";
                }

                String strBmi = "Last bmi calculated: "+BMI.toString();
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = "Last date calculated: "+now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText(datetime);
                tvBmi.setText(strBmi);
                tvWeightType.setText(weightType);


                SharedPreferences bmiPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor bmiEditor = bmiPref.edit();
                bmiEditor.putString("bmi",strBmi);
                bmiEditor.commit();


                SharedPreferences datePref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor dateEditor = datePref.edit();
                dateEditor.putString("name",datetime);
                dateEditor.commit();

                SharedPreferences weightPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor weightEditor = weightPref.edit();
                weightEditor.putString("weight",weightType);
                weightEditor.commit();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences bmiPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor bmiEditor = bmiPref.edit();
                bmiEditor.putString("bmi","Last bmi calculated: ");
                bmiEditor.commit();


                SharedPreferences datePref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor dateEditor = datePref.edit();
                dateEditor.putString("name","Last date calculated: ");
                dateEditor.commit();

                SharedPreferences weightPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor weightEditor = weightPref.edit();
                weightEditor.putString("weight","  ");
                weightEditor.commit();


                etHeight.setText("");
                etWeight.setText("");

                tvBmi.setText("Last bmi calculated: ");
                tvDate.setText("Last date calculated: ");
                tvWeightType.setText("  ");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences bmiPref =PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences datePref = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences weightPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        etHeight.setText("");
        etWeight.setText("");
        tvBmi.setText(bmiPref.getString("bmi","Last bmi calculated"));
        tvDate.setText(datePref.getString("name","Last date calculated"));
        tvWeightType.setText(weightPref.getString("weight","  "));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
