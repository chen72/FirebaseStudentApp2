package sg.edu.rp.webservices.firebasestudentapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryDetailsActivity extends AppCompatActivity {


    private EditText etName, etPrice;
    private Button btnUpdate, btnDelete;

    private Inventory inventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        etName = (EditText) findViewById(R.id.editTextName);
        etPrice = (EditText) findViewById(R.id.editTextPrice);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        firebaseAuth = FirebaseAuth.getInstance();
        //get current user information
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference(firebaseUser.getUid()+"/inventoryList");
        Intent intent = getIntent();
        inventory = (Inventory) intent.getSerializableExtra("Inventory");
        //Display Student details as retrieved from the intent
        etName.setText(inventory.getName());
        etPrice.setText(String.valueOf(inventory.getPrice()));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                Double price = Double.parseDouble(etPrice.getText().toString().trim());
                //TODO: Update Student record based on input given

                Inventory inventorya = new Inventory(name, price);
                inventoryListRef.child(inventory.getId()).setValue(inventorya);

                Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inventoryListRef.child(inventory.getId()).removeValue();
                Toast.makeText(getApplicationContext(), "Item record Delete successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });



    }
}
