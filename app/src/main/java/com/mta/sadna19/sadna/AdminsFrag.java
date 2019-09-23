package com.mta.sadna19.sadna;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminsFrag extends Fragment  {

    private static final String TAG = "onAdminReportReviews";
    private User currentUser;
    private HashMap<String, MenuProblem> userAndProblem = new HashMap<>();
    private HashMap<String, HashMap<String, MenuProblem>> menuProblems = new HashMap<>();
    private ServerHandler serverHandler;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuProblem> mAllMenusProblems = new ArrayList<>();
    private ArrayList<MenuProblem> mDoneMenusProblems = new ArrayList<>();
    private ArrayList<MenuProblem> mInProgressMenusProblems = new ArrayList<>();
    private FragmentTabHost mTabHost;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.admins_frag,null);

        mTabHost = (FragmentTabHost)fragView.findViewById(android.R.id.tabhost);
        mRecyclerView = fragView.findViewById(R.id.recyclerAdminReportReviews);
        serverHandler = new ServerHandler();

        return fragView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serverHandler = new ServerHandler();
        intent = new Intent(getActivity(), AdminHandelReport.class);
        initTabs();
    }

    private void initTabs()
    {
        sortMenusProblems();
        Bundle bundleProgress,bundleDone;
        bundleDone = new Bundle();
        bundleProgress = new Bundle();

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragDone").setIndicator("בקשות שהסתיימו"),
                DoneReviewsFrag.class, bundleDone);
        mTabHost.addTab(mTabHost.newTabSpec("fragInProgress").setIndicator("בקשות בטיפול"),
                InProgressReviewsFrag.class, bundleProgress);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.e("ID ",tabId);
                if (tabId.equals("fragDone"))
                {
                    Toast.makeText(getActivity(), "בקשות שהסתיימו",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "בקשות בטיפול",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sortMenusProblems()
    {
        for (MenuProblem problem : mAllMenusProblems)
        {
            if (problem.getmStatus().equals("הסתיים הטיפול"))
                mDoneMenusProblems.add(problem);
            else
                mInProgressMenusProblems.add(problem);
        }

    }




}
