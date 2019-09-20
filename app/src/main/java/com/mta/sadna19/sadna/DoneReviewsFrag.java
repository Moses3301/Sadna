package com.mta.sadna19.sadna;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.mta.sadna19.sadna.Adapter.AdminReportReviewsRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DoneReviewsFrag extends Fragment {

    RecyclerView mRecyclerView;
    ServerHandler serverHandler;
    Intent intent;
    private HashMap<String, MenuProblem> userAndProblem = new HashMap<>();
    private ArrayList<MenuProblem> mAllMenusProblems = new ArrayList<>();
    private ArrayList<MenuProblem> mDoneMenusProblems = new ArrayList<>();

    public DoneReviewsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.done_reviews_frag,null);
        intent = new Intent(getActivity(),AdminHandelReport.class);
        mRecyclerView = fragView.findViewById(R.id.recyclerAdminReportReviews);
        serverHandler = new ServerHandler();
        return fragView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //mAllMenusProblems = (ArrayList<MenuProblem>)getArguments().get("data");

        serverHandler.SetonProblemsFetchedListener(new ServerHandler.onProblemsFetchedListener() {
            @Override
            public void OnProblemsFetchedListener(HashMap<String, HashMap<String, MenuProblem>> i_Problems) {
                int indexOfUserAndMenus = 0;
                mAllMenusProblems.clear();
                for (String serviceName : i_Problems.keySet()) {
                    userAndProblem = i_Problems.get(serviceName);
                    for (String userID : userAndProblem.keySet()) {
                        MenuProblem currMenuProblemAtArray = new MenuProblem();// = mAllMenusProblems.get(indexOfUserAndMenus);

                        currMenuProblemAtArray.setmServiceName(userAndProblem.get(userID).getmServiceName());

                        currMenuProblemAtArray.setmUserFreeText(userAndProblem.get(userID).getmUserFreeText());

                        currMenuProblemAtArray.setmLastCallPath(userAndProblem.get(userID).getmLastCallPath());

                        currMenuProblemAtArray.setmLastCallDialPath(userAndProblem.get(userID).getmLastCallDialPath());

                        currMenuProblemAtArray.setmClassification(userAndProblem.get(userID).getmClassification());

                        currMenuProblemAtArray.setmStatus(userAndProblem.get(userID).getmStatus());

                        currMenuProblemAtArray.setmUserEmail(userAndProblem.get(userID).getmUserEmail());

                        currMenuProblemAtArray.setmAdminNotes(userAndProblem.get(userID).getmAdminNotes());

                        mAllMenusProblems.add(indexOfUserAndMenus, currMenuProblemAtArray);
                        indexOfUserAndMenus++;
                    }
                }
                sortMenusProblems();
                initRecyclerView();
            }
        });
        serverHandler.fetchProblems();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerView() {
        AdminReportReviewsRecyclerAdapter adapter = new AdminReportReviewsRecyclerAdapter(mDoneMenusProblems, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnProblemClickListener(new AdminReportReviewsRecyclerAdapter.OnProblemClickListener() {
            @Override
            public void OnProblemClick(MenuProblem i_MenuProblem) {
                initUser(i_MenuProblem.getmUserEmail());
                intent.putExtra("Menu_Problem", i_MenuProblem);
            }
        });

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

    private void sortMenusProblems()
    {
        mDoneMenusProblems.clear();
        for (MenuProblem problem : mAllMenusProblems)
        {
            if (problem.getmStatus().equals("הסתיים הטיפול"))
                mDoneMenusProblems.add(problem);

        }

    }

}
