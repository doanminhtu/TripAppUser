package com.example.minhnguyen.tripappuser;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhnguyen.tripappuser.Database.Database;
import com.example.minhnguyen.tripappuser.Model.Order;
import com.example.minhnguyen.tripappuser.Model.Place;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PlaceDetail extends AppCompatActivity {

    TextView place_name, place_address, place_description;
    ImageView place_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnAdd;

    String placeID="";

    FirebaseDatabase database;
    DatabaseReference place;

    Place currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        place = database.getReference("Place");

        //Init View
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAddPlace);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCard(new Order(
                        placeID,
                        currentPlace.getName(),
                        currentPlace.getAddress()


                ));

                Toast.makeText(PlaceDetail.this, "Added to your journey", Toast.LENGTH_SHORT).show();
            }
        });

        place_description = (TextView)findViewById(R.id.place_description);
        place_name = (TextView)findViewById(R.id.place_name);
        place_address = (TextView)findViewById(R.id.place_address);
        place_image = (ImageView)findViewById(R.id.img_place);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //Get Place ID from Intent
        if (getIntent() != null)
            placeID = getIntent().getStringExtra("PlaceID");
        if (!placeID.isEmpty())
        {
            getDetailPlace(placeID);
        }
    }

    private void getDetailPlace(String placeID) {
        place.child(placeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentPlace = dataSnapshot.getValue(Place.class);

                //Set Image
                Picasso.get().load(currentPlace.getImage())
                        .into(place_image);

                collapsingToolbarLayout.setTitle(currentPlace.getName());

                place_address.setText(currentPlace.getAddress());
                place_name.setText(currentPlace.getName());
                place_description.setText(currentPlace.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
