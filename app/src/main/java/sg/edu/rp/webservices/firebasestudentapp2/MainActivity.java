package sg.edu.rp.webservices.firebasestudentapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView lvInventory;
    private ArrayList<Inventory> alInventory;
    private ArrayAdapter<Inventory> aaInventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        //get current user information
        firebaseUser = firebaseAuth.getCurrentUser();

        lvInventory = (ListView)findViewById(R.id.listViewItems);
        alInventory = new ArrayList<Inventory>();
        aaInventory = new ArrayAdapter<Inventory>(this, android.R.layout.simple_list_item_1, alInventory);
        lvInventory.setAdapter(aaInventory);

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference(firebaseUser.getUid()+"/inventoryList");


        inventoryListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildAdded()");
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                Log.i("inventoryname",inventory.getName());
                if (inventory != null) {
                    inventory.setId(dataSnapshot.getKey());
                    alInventory.add(inventory);
                    aaInventory.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildChanged()");

                String selectedId = dataSnapshot.getKey();
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    for (int i = 0; i < alInventory.size(); i++) {
                        if (alInventory.get(i).getId().equals(selectedId)) {
                            inventory.setId(selectedId);
                            alInventory.set(i, inventory);
                            break;
                        }
                    }
                    aaInventory.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Log.i("MainActivity", "onChildRemoved()");

                String selectedId = dataSnapshot.getKey();
                for(int i= 0; i < alInventory.size(); i++) {
                    if (alInventory.get(i).getId().equals(selectedId)) {
                        alInventory.remove(i);
                        break;
                    }
                }
                aaInventory.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildMoved()");


                String selectedId = dataSnapshot.getKey();
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    for (int i = 0; i < alInventory.size(); i++) {
                        if (alInventory.get(i).getId().equals(selectedId)) {
                            inventory.setId(selectedId);
                            alInventory.set(i, inventory);
                            break;
                        }
                    }
                    aaInventory.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Database error occurred", databaseError.toException());
            }
        });


        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inventory inventory = alInventory.get(i);  // Get the selected Student
                Intent intent = new Intent(MainActivity.this, InventoryDetailsActivity.class);
                intent.putExtra("Inventory", inventory);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_view_profile) {
            Intent i = new Intent(getBaseContext(), ViewProfileActivity.class);
            startActivity(i);
        }

        if (id == R.id.action_logout) {

            firebaseAuth.signOut();

            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
        }
        if (id == R.id.addInventory) {

            Intent intent = new Intent(getApplicationContext(), AddInventoryActivity.class);
            startActivity(intent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
