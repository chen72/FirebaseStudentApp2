package sg.edu.rp.webservices.firebasestudentapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInventoryActivity extends AppCompatActivity {


    private EditText etName, etPrice;
    private Button btnAdd;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        etName = (EditText) findViewById(R.id.editTextName);
        etPrice = (EditText) findViewById(R.id.editTextPrice);
        btnAdd = (Button) findViewById(R.id.buttonAdd);

        firebaseAuth = FirebaseAuth.getInstance();
        //get current user information
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("/inventoryList/"+firebaseUser.getUid());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 3: Add student to database and go back to main screen
                String name = etName.getText().toString();
                Double price = Double.parseDouble(etPrice.getText().toString());
                Inventory inventory = new Inventory(name, price);
                inventoryListRef.push().setValue(inventory);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

}
