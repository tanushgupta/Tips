package com.symphonyfintech.tips.adapters.advisorAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.CropCircleTransformation;
import com.symphonyfintech.tips.adapters.CustomAdapter.ImageLoadTask;
import com.symphonyfintech.tips.model.advisor.AdvisorBean;
import com.symphonyfintech.tips.view.advisors.Advisor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvisorAdapter extends RecyclerView.Adapter<AdvisorAdapter.ViewHolder> {
    DatabaseReference tipsFirebaseRef;
   public static AdvisorBean selectedTip;
    boolean firebaseUpdateWorking = false;
    private List<AdvisorBean> advisorList ;
    final Handler myHandler;
    private Context mContext;

    public AdvisorAdapter() {
        myHandler = new Handler();
        advisorList = new ArrayList<>();
        tipsFirebaseRef = FirebaseDatabase.getInstance().getReference("Analysts/");
//        handler.postDelayed(new SimpleTread(),30L);

        tipsFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUpdateWorking=true;

               Object dtata =  dataSnapshot.getValue();
               HashMap<String, HashMap<String, HashMap<String, Object>>> value1 = (HashMap<String, HashMap<String, HashMap<String, Object>>>) dataSnapshot.getValue();
                        if(value1!=null){
                            advisorList = null;
                            advisorList = new ArrayList<AdvisorBean>();
                        }

                if(value1!=null) {

                    for (HashMap.Entry<?, ?> entry2 : value1.entrySet()) {
                        AdvisorBean advisorbean = new AdvisorBean();

                        for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {

                            //print keys and values
//                            for (HashMap.Entry<?, ?> entry : ((HashMap<String, String>) entry2.getValue()).entrySet()) {
//                        System.out.println("**********\t " + entry.getKey() + " : " + entry.getValue());
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

                        }
//                    System.out.println(" \n\n\n new Entry ");
                        advisorList.add(advisorbean);

                    }
                }

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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_advisor_row, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tiprow_analystName.setText(advisorList.get(i).name);
        viewHolder.txt_tip_count.setText(advisorList.get(i).tipCont);
        viewHolder.tiprow_executedTipCount.setText(advisorList.get(i).tipCont);
        //new ImageLoadTask(advisorList.get(i).profileIcon,viewHolder.tiprow_profileIcon).execute();
        Glide.with(viewHolder.view.getContext())
                .load(advisorList.get(i).profileIcon)
                .bitmapTransform(new CropCircleTransformation(viewHolder.view.getContext()))
                .into(viewHolder.tiprow_profileIcon);
        /*Glide.with(viewHolder.view.getContext()).load(advisorList.get(i).profileIcon).asBitmap().centerCrop().into(
                new BitmapImageViewTarget(viewHolder.tiprow_profileIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(viewHolder.view.getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });*/
        //Picasso.with(viewHolder.view.getContext()).load(advisorList.get(i).profileIcon).into(viewHolder.tiprow_profileIcon);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTip=advisorList.get(i);
                Intent intent = new Intent(v.getContext(), Advisor.class);
                v.getContext().startActivity(intent);
            }
        });
    }
 
    @Override
    public int getItemCount() {
        return advisorList.size();
    }
 
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tiprow_analystName,txt_tip_count,tiprow_executedTipCount;
        ImageView tiprow_profileIcon;
        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tiprow_analystName = (TextView)view.findViewById(R.id.tiprow_analystName);
            txt_tip_count = (TextView)view.findViewById(R.id.txt_tip_count);
            tiprow_executedTipCount = (TextView)view.findViewById(R.id.tiprow_executedTipCount);
            tiprow_profileIcon =(ImageView)view.findViewById(R.id.tiprow_profileIcon);
        }
    }
}