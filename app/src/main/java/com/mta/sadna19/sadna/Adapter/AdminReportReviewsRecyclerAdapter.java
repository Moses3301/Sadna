package com.mta.sadna19.sadna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mta.sadna19.sadna.MenuProblem;
import com.mta.sadna19.sadna.R;


import java.util.ArrayList;

public class AdminReportReviewsRecyclerAdapter extends RecyclerView.Adapter<AdminReportReviewsRecyclerAdapter.ViewHolder> {

    public static final String TAG = "?AdminAdapter?";

    private ArrayList<MenuProblem> mAllMenuProblems;

    private Context mContext;

    public AdminReportReviewsRecyclerAdapter(ArrayList<MenuProblem> mAllMenuProblems, Context mContext) {
        this.mAllMenuProblems = mAllMenuProblems;
        this.mContext = mContext;
    }


    OnProblemClickListener mOnProblemClickListener;

    public void setOnProblemClickListener(OnProblemClickListener mOnProblemClickListener) {
        this.mOnProblemClickListener = mOnProblemClickListener;
    }

    public interface OnProblemClickListener {
         void OnProblemClick(MenuProblem i_MenuProblem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_report_reviews_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.e(TAG, "onBindViewHolder: called ");
        MenuProblem currMenuProblem = mAllMenuProblems.get(i);

        viewHolder.serviceName.setText(currMenuProblem.getmServiceName());
        viewHolder.problem.setText(currMenuProblem.getmUserFreeText());
        viewHolder.path.setText(currMenuProblem.getmLastCallPath());
        viewHolder.dialPath.setText(currMenuProblem.getmLastCallDialPath());
        viewHolder.classification.setText(currMenuProblem.getmClassification());
        viewHolder.status.setText(currMenuProblem.getmStatus());
        viewHolder.reporterEmail.setText(currMenuProblem.getmUserEmail());
        viewHolder.adminNotes.setText(currMenuProblem.getmAdminNotes());

        final MenuProblem mMenuProblem = currMenuProblem;

        viewHolder.adminReportItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnProblemClickListener != null)
                    mOnProblemClickListener.OnProblemClick(mMenuProblem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllMenuProblems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        TextView problem;
        TextView path;
        TextView dialPath;
        TextView reporterEmail;
        TextView classification;
        TextView status;
        TextView adminNotes;
        LinearLayout adminReportItemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.tvServiceName);
            problem = itemView.findViewById(R.id.tvProblem);
            path = itemView.findViewById(R.id.tvPath);
            dialPath = itemView.findViewById(R.id.tvDialPath);
            reporterEmail = itemView.findViewById(R.id.tvReporterEmail);
            classification = itemView.findViewById(R.id.tvClassification);
            status = itemView.findViewById(R.id.tvStatus);
            adminNotes = itemView.findViewById(R.id.tvAdminNotes);

            adminReportItemLayout = itemView.findViewById(R.id.adminReportItem);
        }

    }
}