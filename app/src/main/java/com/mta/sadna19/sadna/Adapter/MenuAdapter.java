package com.mta.sadna19.sadna.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mta.sadna19.sadna.R;
import com.mta.sadna19.sadna.ServiceItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter< MenuViewHolder > implements Filterable {
    private Context mContext;
    private List<ServiceItem> mMenuList;
    private List<ServiceItem> mMenuListFull;
    OnMenuClickListener mOnMenuClickListener;
    OnMenuLongClickListener mOnMenuLongClickListener;

    public List<ServiceItem> getmMenuListFull() {
        return mMenuListFull;
    }

    public void setmMenuListFull(List<ServiceItem> mMenuListFull) {
        this.mMenuListFull = mMenuListFull;
    }

    public void setOnMenuLongClickListener(OnMenuLongClickListener mOnMenuLongClickListener) {
        this.mOnMenuLongClickListener = mOnMenuLongClickListener;
    }

    public void setOnMenuClickListener(OnMenuClickListener mOnMenuClickListener) {
        this.mOnMenuClickListener = mOnMenuClickListener;
    }

    public interface OnMenuClickListener{
        void OnMenuClick(ServiceItem iMenu);
    }

    public interface OnMenuLongClickListener{
        void OnMenuLongClick(ServiceItem iMenu);
    }

    public MenuAdapter(Context iContext, List< ServiceItem > iMenuList) {
        this.mContext = iContext;
        this.mMenuList = iMenuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recyclerview_item, parent, false);
        return new MenuViewHolder(mView);
    }

    public void SetMenu(List<ServiceItem> iMenuList) {
        this.mMenuList = iMenuList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        try{
        Picasso.get().load(mMenuList.get(position).getM_avatar()).into(holder.mImage);
        }catch (Exception e){ }
        holder.mTitle.setText(mMenuList.get(position).getM_name());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuClickListener!= null)
                    mOnMenuClickListener.OnMenuClick(mMenuList.get(position));
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnMenuLongClickListener!=null)
                    mOnMenuLongClickListener.OnMenuLongClick(mMenuList.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    @Override
    public Filter getFilter() {
        return menuFilter;
    }

    private Filter menuFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List <ServiceItem> filteredItems = new ArrayList<>();
            if (constraint==null||constraint.length()==0)
            {
                filteredItems.addAll(mMenuListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ServiceItem item : mMenuListFull)
                {
                    if (item.getM_name().toLowerCase().contains(filterPattern))
                    {
                        filteredItems.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredItems;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMenuList.clear();
            mMenuList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}

class MenuViewHolder extends RecyclerView.ViewHolder {
    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    MenuViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}