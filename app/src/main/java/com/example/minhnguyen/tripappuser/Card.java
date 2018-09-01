package com.example.minhnguyen.tripappuser;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.minhnguyen.tripappuser.Common.Common;
import com.example.minhnguyen.tripappuser.Database.Database;
import com.example.minhnguyen.tripappuser.Model.CusJourney;
import com.example.minhnguyen.tripappuser.Model.Order;
import com.example.minhnguyen.tripappuser.ViewHolder.CardAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class Card extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference journey;

    FButton btnSetName;

    List<Order> card = new ArrayList<>();

    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        //Firebase
        database = FirebaseDatabase.getInstance();
        journey = database.getReference("Journeys");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCard);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnSetName = (FButton)findViewById(R.id.btnNameJourney);

        btnSetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();
            }
        });

        loadListPlace();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Card.this);
        alertDialog.setTitle("Make your own journey!");
        alertDialog.setMessage("Enter your tour name: ");

        final EditText edtTourName = new EditText(Card.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtTourName.setLayoutParams(lp);
        alertDialog.setView(edtTourName);// Add edit Text to alert dialog
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Create new customer journey
                CusJourney cusJourney = new CusJourney(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtTourName.getText().toString(),
                        card
                );

                //Submit to Firebase
                journey.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(cusJourney);
                //Delete Card
                new Database(getBaseContext()).cleanCard();
                Toast.makeText(Card.this, "Thank You", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadListPlace() {
        card = new Database(this).getCards();
        adapter = new CardAdapter(card, this);
        recyclerView.setAdapter(adapter);

    }
}
