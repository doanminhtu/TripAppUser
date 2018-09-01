package com.example.minhnguyen.tripappuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.minhnguyen.tripappuser.Common.Common;
import com.example.minhnguyen.tripappuser.Database.Database;
import com.example.minhnguyen.tripappuser.Interface.ItemClickListener;
import com.example.minhnguyen.tripappuser.Model.CusJourney;
import com.example.minhnguyen.tripappuser.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyJourney extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<CusJourney,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_journey);

        //Firebase
        database = FirebaseDatabase.getInstance();
        journey = database.getReference("Journeys");

        recyclerView = (RecyclerView)findViewById(R.id.listJourneys);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadJourneys(Common.currentUser.getPhone());
    }

    private void loadJourneys(String phone){
        adapter = new FirebaseRecyclerAdapter<CusJourney, OrderViewHolder>(
                CusJourney.class,
                R.layout.journey_layout,
                OrderViewHolder.class,
                journey.orderByChild("phone")
                       .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, CusJourney model, int position) {
                viewHolder.txtTourID.setText(adapter.getRef(position).getKey());
                viewHolder.txtTourName.setText(model.getTourname());
                viewHolder.txtListPlaces.setText(model.getlistPlaces());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.INFO))
            showInfoDialog(adapter.getRef(item.getOrder()).getKey());
        if(item.getTitle().equals(Common.DELETE))
            deleteJourney(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void showInfoDialog(String key) {
        //de sau hen
    }

    private void deleteJourney(String key) {
        journey.child(key).removeValue();
    }
}
