package com.mta.sadna19.sadna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHandelReport extends AppCompatActivity {

    private static final String TAG = "onAdminHandelReport";

    private MenuProblem mMenuProblem = new MenuProblem();
    private User mCurrentUser = new User();
    private String mUserUID = new String();

    private TextView serviceName;
    private TextView problem;
    private TextView path;
    private TextView dialPath;
    private TextView reporterEmail;
    private Spinner statusSpinner;
    private TextView classification;
    private EditText adminNotes;
    private Button btnSave;


    private ArrayAdapter<CharSequence> adapter;

    private ServerHandler serverHandler = new ServerHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handel_report);

        initReport();

    }

    private void initReport() {

        Log.e(TAG, "onInitReport >>");
        mMenuProblem = getIntent().getExtras().getParcelable("Menu_Problem");
        mCurrentUser = getIntent().getExtras().getParcelable("Current_User");
        mUserUID = getIntent().getStringExtra("User_UID");

        mCurrentUser.setM_ID(mUserUID);

        serviceName = findViewById(R.id.tvServiceName);
        problem = findViewById(R.id.tvProblem);
        path = findViewById(R.id.tvPath);
        dialPath = findViewById(R.id.tvDialPath);
        reporterEmail = findViewById(R.id.tvReporterEmail);
        classification = findViewById(R.id.tvClassification);
        statusSpinner = findViewById(R.id.sStatus);
        btnSave = findViewById(R.id.btnSave);
        adminNotes = findViewById(R.id.etAdminNotes);

        initSpinner();

        serviceName.setText(mMenuProblem.getmServiceName());
        problem.setText(mMenuProblem.getmUserFreeText());
        path.setText(mMenuProblem.getmLastCallPath());
        dialPath.setText(mMenuProblem.getmLastCallDialPath());
        reporterEmail.setText(mMenuProblem.getmUserEmail());
        classification.setText(mMenuProblem.getmClassification());
        adminNotes.setText(mMenuProblem.getmAdminNotes());

        statusSpinner.setSelection(adapter.getPosition(mMenuProblem.getmStatus()));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "הדיווח עודכן ונשמר", Toast.LENGTH_SHORT).show();

                mMenuProblem.setmAdminNotes(adminNotes.getText().toString());
                mMenuProblem.setmStatus(statusSpinner.getSelectedItem().toString());
                serverHandler.writeAProblem(mMenuProblem, mCurrentUser, true);
                if (statusSpinner.getSelectedItem().toString().equals("הסתיים הטיפול")) {
                    String msg = new String("תודה רבה על פנייתך." + "\n" + "הדיווח טופל בהצלחה :)") + "\n" + "צוות Dialrectly";
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{reporterEmail.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "דיווח על תקלה שטופלה - Dialrectly");
                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                    startActivity(Intent.createChooser(intent, ""));
                }
            }
        });

        Log.e(TAG, "onInitReport <<");
    }

    private void initSpinner() {
        adapter = ArrayAdapter.createFromResource(this, R.array.ProblemStatus, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        statusSpinner.setAdapter(adapter);
    }
}
