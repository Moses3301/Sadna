package com.mta.sadna19.sadna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mta.sadna19.sadna.Adapter.AdminReportReviewsRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminReportReviews extends AppCompatActivity {

   /* private static final String TAG = "onAdminReportReviews";
    private User currentUser;
    private HashMap<String, MenuProblem> userAndProblem = new HashMap<>();
    private HashMap<String, HashMap<String, MenuProblem>> menuProblems = new HashMap<>();
    private ServerHandler serverHandler;
    private ArrayList<String> serviceArray = new ArrayList<>();
    private ArrayList<String> problemArray = new ArrayList<>();
    private ArrayList<String> pathArray = new ArrayList<>();
    private ArrayList<String> pathDialArray = new ArrayList<>();
    private ArrayList<String> reporterEmailArray = new ArrayList<>();
    private ArrayList<String> classificationArray = new ArrayList<>();
    private ArrayList<String> statusArray = new ArrayList<>();
    private ArrayList<String> adminNotesArray = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(TAG, "onCreate >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_reviews);
        serverHandler = new ServerHandler();
        intent = new Intent(AdminReportReviews.this, AdminHandelReport.class);
        initReportItems();

    }

    private void initUser(String i_UserEmail) {
        serverHandler.fetchUserByEmail(i_UserEmail);
        serverHandler.SetOnUserFetchedByEmailListener(new ServerHandler.OnUserFetchedByEmailListener() {
            @Override
            public void OnUserFetchedByEmail(User i_user, String i_userUID) {
                intent.putExtra("Current_User", i_user);
                intent.putExtra("User_UID", i_userUID);
                startActivity(intent);
            }
        });
    }


    private void initReportItems() {
        Log.e(TAG, "initReportItems >>");
        serverHandler.fetchProblems();
        serverHandler.SetonProblemsFetchedListener(new ServerHandler.onProblemsFetchedListener() {
            @Override
            public void OnProblemsFetchedListener(HashMap<String, HashMap<String, MenuProblem>> i_Problems) {
                menuProblems = i_Problems;
                Log.e(TAG, "menuProblems >> " + menuProblems.toString());

                serviceArray = new ArrayList<>();
                problemArray = new ArrayList<>();
                pathArray = new ArrayList<>();
                pathDialArray = new ArrayList<>();
                reporterEmailArray = new ArrayList<>();
                classificationArray = new ArrayList<>();
                statusArray = new ArrayList<>();
                adminNotesArray = new ArrayList<>();

                int indexOfUserAndMenus = 0;
                for (String serviceName : menuProblems.keySet()) {
                    userAndProblem = menuProblems.get(serviceName);
                    for (String userID : userAndProblem.keySet()) {
                        Log.e(TAG, "userID >> " + userID);
                        serviceArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmServiceName());
                        Log.e(TAG, "~serviceArray >>" + serviceArray);
                        problemArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmUserFreeText());
                        Log.e(TAG, "~problemArray >>" + problemArray);
                        pathArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmLastCallPath());
                        Log.e(TAG, "~pathArray >>" + pathArray);
                        pathDialArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmLastCallDialPath());
                        Log.e(TAG, "~pathDialArray >>" + pathDialArray);
                        classificationArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmClassification());
                        Log.e(TAG, "~classificationArray >>" + classificationArray);
                        statusArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmStatus());
                        Log.e(TAG, "~statusArray >>" + statusArray);
                        reporterEmailArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmUserEmail());
                        Log.e(TAG, "~reporterEmailArray >>" + reporterEmailArray);
                        adminNotesArray.add(indexOfUserAndMenus, userAndProblem.get(userID).getmAdminNotes());
                        Log.e(TAG, "~adminNotesArray >>" + adminNotesArray);
                        indexOfUserAndMenus++;
                    }
                }

                initRecyclerView();
            }
        });
        Log.e(TAG, "initReportItems <<");

    }

    private void initRecyclerView() {
        Log.e(TAG, "initRecyclerView >>");
        RecyclerView mRecyclerView = findViewById(R.id.recyclerAdminReportReviews);
        AdminReportReviewsRecyclerAdapter adapter = new AdminReportReviewsRecyclerAdapter(serviceArray, problemArray, pathArray, pathDialArray, reporterEmailArray, statusArray, classificationArray, adminNotesArray, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnProblemClickListener(new AdminReportReviewsRecyclerAdapter.OnProblemClickListener() {
            @Override
            public void OnProblemClick(MenuProblem i_MenuProblem) {
                initUser(i_MenuProblem.getmUserEmail());
                intent.putExtra("Menu_Problem", i_MenuProblem);
            }
        });
        Log.e(TAG, "initRecyclerView <<");
    }*/

}
