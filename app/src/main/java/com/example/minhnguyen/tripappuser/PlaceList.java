package com.example.minhnguyen.tripappuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minhnguyen.tripappuser.Interface.ItemClickListener;
import com.example.minhnguyen.tripappuser.Model.Place;
import com.example.minhnguyen.tripappuser.ViewHolder.PlaceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaceList extends AppCompatActivity {

    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference placeList;

    String cityID="";

    FirebaseRecyclerAdapter<Place, PlaceViewHolder> adapter;

    //Search Function
    FirebaseRecyclerAdapter<Place,PlaceViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        placeList =  database.getReference("Place");

        recyclerview = (RecyclerView)findViewById(R.id.recycler_place);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Get Intent Here
        if(getIntent()!=null)
        cityID = getIntent().getStringExtra("CityID");
        if (!cityID.isEmpty() && cityID != null)
        {
            loadListPlace(cityID);
        }

        //Search
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search your place");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //When user type their text, we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When Search Bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerview.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result of search adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Place, PlaceViewHolder>(
                Place.class,
                R.layout.place_item,
                PlaceViewHolder.class,
                placeList.orderByChild("Name").equalTo(text.toString()) //Compare name
        ) {
            @Override
            protected void populateViewHolder(PlaceViewHolder viewHolder, Place model, int position) {
                viewHolder.place_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.place_image);

                final Place local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent placeDetail = new Intent(PlaceList.this,PlaceDetail.class);
                        placeDetail.putExtra("PlaceID",adapter.getRef(position).getKey());
                        startActivity(placeDetail);
                    }
                });
            }
        };
                recyclerview.setAdapter(searchAdapter);//
    }

    private void loadSuggest() {
        placeList.orderByChild("CityID").equalTo(cityID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Place item = postSnapshot.getValue(Place.class);
                            suggestList.add(item.getName()); //Add name of place to suggest
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadListPlace(String cityID) {
        adapter = new FirebaseRecyclerAdapter<Place, PlaceViewHolder>(Place.class,
                R.layout.place_item,
                PlaceViewHolder.class,
                placeList.orderByChild("CityID").equalTo(cityID)// like Select * From Place Where CityID = ..
                ) {
            @Override
            protected void populateViewHolder(PlaceViewHolder viewHolder, Place model, int position) {
                viewHolder.place_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.place_image);

                final Place local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent placeDetail = new Intent(PlaceList.this,PlaceDetail.class);
                        placeDetail.putExtra("PlaceID",adapter.getRef(position).getKey());
                        startActivity(placeDetail);
                    }
                });
            }
        };

        //set Adapter
        recyclerview.setAdapter(adapter);
    }
}
