package com.symphonyfintech.tips.adapters.advisorAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.CropCircleTransformation;
import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseRecyclerViewAdapter;
import com.symphonyfintech.tips.model.advisor.AdvisorBean;
import com.symphonyfintech.tips.model.advisor.AdvisorHeaderItem;
import com.symphonyfintech.tips.model.advisor.AdvisorItem;
import com.symphonyfintech.tips.model.advisor.AdvisorList;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.advisors.Advisor;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;
import com.symphonyfintech.tips.view.tips.TipRowDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvisorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean firebaseUpdateWorking = false;
    public static AdvisorBean selectedTip;

    private User user;

    private List<AdvisorList> advisorList ;

    private HashMap<String, List<AdvisorList>> lstAdvisors;

    final Handler myHandler;
    private Context mContext;

    DatabaseReference analystsFirebaseRef;

    public AdvisorAdapter() {
        user = User.getInstance();
        myHandler = new Handler();
        advisorList = new ArrayList<>();
        lstAdvisors = new HashMap<String,List<AdvisorList>>();
        lstAdvisors.put("Favorite",new ArrayList<AdvisorList>());
        lstAdvisors.put("All",new ArrayList<AdvisorList>());
        analystsFirebaseRef = FirebaseDatabase.getInstance().getReference("Analysts/");
        analystsFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUpdateWorking=true;
               Object dtata =  dataSnapshot.getValue();
               HashMap<String, HashMap<String, HashMap<String, Object>>> value1 = (HashMap<String, HashMap<String, HashMap<String, Object>>>) dataSnapshot.getValue();
                if(value1!=null){
                    advisorList.clear();
                    lstAdvisors.get("Favorite").clear();
                    lstAdvisors.get("All").clear();
                    lstAdvisors.get("Favorite").add(new AdvisorHeaderItem("FAVORITE ADVISORS"));
                    lstAdvisors.get("All").add(new AdvisorHeaderItem("ALL ADVISORS"));
                }
                if(value1!=null) {
                    for (HashMap.Entry<?, ?> entry2 : value1.entrySet()) {
                        AdvisorBean advisorbean = new AdvisorBean();
                        for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {
                            if (entry.getKey().equals("ExecutedTipCount"))
                                advisorbean.ExecutedTipCount = entry.getValue().toString();
                            else if (entry.getKey().equals("UserType"))
                                advisorbean.UserType = entry.getValue().toString();
                            else if (entry.getKey().equals("name"))
                                advisorbean.name = entry.getValue().toString();
                            else if (entry.getKey().equals("profileIcon"))
                                advisorbean.profileIcon = entry.getValue().toString();
                            else if (entry.getKey().equals("profileImage"))
                                advisorbean.profileImage = entry.getValue().toString();
                            else if (entry.getKey().equals("tipCount"))
                                advisorbean.tipCont = entry.getValue().toString();
                            else if (entry.getKey().equals("userId"))
                                advisorbean.userId = entry.getValue().toString();
                            else if (entry.getKey().equals("about"))
                                advisorbean.about = entry.getValue().toString();
                            else if (entry.getKey().equals("subscribed"))
                                advisorbean.subscribed = entry.getValue().toString();
                        }
                        if(user.getList().contains(advisorbean.userId)){
                            lstAdvisors.get("Favorite").add(new AdvisorItem(advisorbean));
                        }
                        else{
                            lstAdvisors.get("All").add(new AdvisorItem(advisorbean));
                        }

                    }
                }

                if(lstAdvisors.get("Favorite").size() > 1){
                    advisorList.addAll(lstAdvisors.get("Favorite"));
                }
                else{
                    lstAdvisors.get("Favorite").clear();
                }
                advisorList.addAll(lstAdvisors.get("All"));
                notifyItemInserted(advisorList.size()==0 ? 0 : advisorList.size()-1);
                notifyDataSetChanged();
                firebaseUpdateWorking=false;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DATA", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (i) {
            case AdvisorList.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.list_item_header, viewGroup, false);
                return new HeaderViewHolder(itemView);
            }
            case AdvisorList.TYPE_ADVISOR: {
                View itemView = inflater.inflate(R.layout.activity_advisor_row, viewGroup, false);
                return new ItemViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case AdvisorList.TYPE_HEADER: {
                AdvisorHeaderItem header = (AdvisorHeaderItem)advisorList.get(position);
                HeaderViewHolder viewhold = (HeaderViewHolder) holder;
                // your logic here
                viewhold.txt_header.setText(header.getHeader());
                break;
            }
            case AdvisorList.TYPE_ADVISOR: {
                final AdvisorItem advisor = (AdvisorItem) advisorList.get(position);
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                createView(viewHolder,advisor.getadvisor(),position);
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    private void createView(final ItemViewHolder viewHolder,final AdvisorBean advisor,int position){
        if(user.AuthType == User.AUTH_USER ) {
            if (lstAdvisors.get("Favorite").size() > 0 && position <= lstAdvisors.get("Favorite").size()){//lstAdvisors.size() > 1 && (position+1) <= lstAdvisors.size() - 1) {
                viewHolder.img_fav_advisor.setImageResource(R.drawable.ic_fav_advisor);
                viewHolder.img_fav_advisor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if(user.getList().contains(advisor.userId)) {
                                user.getList().remove(advisor.userId);
                                int subsc = Integer.parseInt(advisor.subscribed);
                                subsc--;
                                HashMap<String, List<String>> entry = new HashMap<String, List<String>>();
                                entry.put("ID", User.getInstance().getList());
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("/");
                                myRef.child("Users/" + user.getUserName() + "/" + "FavAnalyst/").setValue(entry);
                                myRef.child("Analysts/" + advisor.userId + "/subscribed").setValue("" + subsc);
                            }
                            else {
                                Toast.makeText(mContext,"Already removed, wait...",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (NullPointerException ex){

                        }
                        catch(Exception ex){

                        }
                    }
                });
            } else {
                viewHolder.img_fav_advisor.setImageResource(R.drawable.ic_not_fav_advisor);
                viewHolder.img_fav_advisor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if(!user.getList().contains(advisor.userId)) {
                                int subsc = Integer.parseInt(advisor.subscribed);
                                subsc++;
                                user.getList().add(advisor.userId);
                                HashMap<String, List<String>> entry = new HashMap<String, List<String>>();
                                entry.put("ID", user.getList());
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("/");
                                myRef.child("Users/" + user.getUserName() + "/" + "FavAnalyst/").setValue(entry);
                                myRef.child("Analysts/" + advisor.userId + "/subscribed").setValue("" + subsc);
                            }
                            else{
                                Toast.makeText(mContext,"Already added, wait...",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (NullPointerException ex){

                        }
                        catch(Exception ex){

                        }
                    }
                });
            }
        }
        else{
            viewHolder.img_fav_advisor.setImageResource(R.drawable.ic_not_fav_advisor);
            viewHolder.img_fav_advisor.setAlpha(.5f);
            viewHolder.img_fav_advisor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(viewHolder.view.getContext(),"Sign in to set favorite.",Toast.LENGTH_SHORT).show();
                }
            });
        }
        viewHolder.tiprow_analystName.setText(advisor.name);
        viewHolder.txt_tip_count.setText(advisor.tipCont);
        viewHolder.tiprow_executedTipCount.setText(advisor.tipCont);

        //new ImageLoadTask(advisorList.get(i).profileIcon,viewHolder.tiprow_profileIcon).execute();
        Glide.with(viewHolder.view.getContext())
                .load(advisor.profileIcon)
                .bitmapTransform(new CropCircleTransformation(viewHolder.view.getContext()))
                .into(viewHolder.tiprow_profileIcon);

        //Substitute for Glide.
        //Picasso.with(viewHolder.view.getContext()).load(advisorList.get(i).profileIcon).into(viewHolder.tiprow_profileIcon);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTip=advisor;
                Intent intent = new Intent(v.getContext(), Advisor.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return advisorList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return advisorList.size();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_header;
        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = (TextView) itemView.findViewById(R.id.txt_header);
        }
    }
    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tiprow_analystName,txt_tip_count,tiprow_executedTipCount;
        ImageView tiprow_profileIcon,img_fav_advisor;
        View view;

        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            tiprow_analystName = (TextView)view.findViewById(R.id.tiprow_analystName);
            txt_tip_count = (TextView)view.findViewById(R.id.txt_tip_count);
            tiprow_executedTipCount = (TextView)view.findViewById(R.id.tiprow_executedTipCount);
            tiprow_profileIcon =(ImageView)view.findViewById(R.id.tiprow_profileIcon);
            img_fav_advisor = (ImageView)view.findViewById(R.id.img_fav_advisor);
        }
    }
}