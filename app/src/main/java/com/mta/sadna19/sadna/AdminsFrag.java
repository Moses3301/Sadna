package com.mta.sadna19.sadna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.mta.sadna19.sadna.Adapter.AdminReportReviewsRecyclerAdapter;
import com.mta.sadna19.sadna.Adapter.CategoryRecyclerAdapter;
import com.mta.sadna19.sadna.Adapter.MenuAdapter;
import com.mta.sadna19.sadna.MenuRegisters.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminsFrag extends Fragment  {

    private static final String TAG = "onAdminReportReviews";
    private User currentUser;
    private HashMap<String, MenuProblem> userAndProblem = new HashMap<>();
    private HashMap<String, HashMap<String, MenuProblem>> menuProblems = new HashMap<>();
    private ServerHandler serverHandler;

    RecyclerView mRecyclerView;
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
        //initUser();
        //initReportItems();

        return fragView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serverHandler = new ServerHandler();
        intent = new Intent(getActivity(), AdminHandelReport.class);
        initTabs();
        //initReportItems();
    }

    /*private void initReportItems() {
        Log.e(TAG, "initReportItems >>");
        serverHandler.fetchProblems();
        serverHandler.SetonProblemsFetchedListener(new ServerHandler.onProblemsFetchedListener() {
            @Override
            public void OnProblemsFetchedListener(HashMap<String, HashMap<String, MenuProblem>> i_Problems) {
                menuProblems = i_Problems;
                Log.e(TAG, "menuProblems >> " + menuProblems.toString());
                int indexOfUserAndMenus = 0;
                for (String serviceName : menuProblems.keySet()) {
                    userAndProblem = menuProblems.get(serviceName);
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
                initTabs();

            }
        });
        Log.e(TAG, "initReportItems <<");

    }*/

    private void initTabs()
    {

        sortMenusProblems();
        Bundle bundleProgress,bundleDone;
        bundleDone = new Bundle();
        bundleProgress = new Bundle();
        /*bundleDone.putSerializable("data",mDoneMenusProblems);
        bundleProgress.putSerializable("data",mInProgressMenusProblems);
*/
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
