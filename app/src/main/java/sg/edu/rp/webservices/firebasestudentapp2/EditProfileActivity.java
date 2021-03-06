package sg.edu.rp.webservices.firebasestudentapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private EditText etName,etContactNo, etHobbies;
    private TextView tvEmail;
    private Button btnUpdate, btnDelete;
    private UserProfile userProfile;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseRef;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userProfileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = (EditText) findViewById(R.id.editTextName);
        etContactNo = (EditText) findViewById(R.id.editTextContactNo);
        etHobbies = (EditText) findViewById(R.id.editTextHobbies);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);

        tvEmail = (TextView)findViewById(R.id.textViewEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        //get current user information
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseRef = firebaseDatabase.getReference("/profiles");
        Log.i("user",firebaseUser+"");

        userProfileRef = firebaseDatabase.getReference("profiles/" + firebaseUser.getUid());


        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "userProfileRef.addValueEventListener -- onDataChange()");
                UserProfile profile = dataSnapshot.getValue(UserProfile.class);
                if (profile != null) {
                    Log.i(TAG, "profile: " + profile.toString());
                    etName.setText(profile.getName());
                    etContactNo.setText(profile.getContactNo());
                    etHobbies.setText(profile.getHobbies());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error occurred", databaseError.toException());
            }
        });


        tvEmail.setText(firebaseUser.getEmail());

//        Intent intent = getIntent();
//        student = (Student) intent.getSerializableExtra("Student");
//        //Display Student details as retrieved from the intent
//        etName.setText(student.getName());
//        etAge.setText(String.valueOf(student.getAge()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String contactNo = etContactNo.getText().toString().trim();
                String hoobies  = etHobbies.getText().toString().trim();
                String id = firebaseUser.getUid();
                //TODO: Update Student record based on input given
                UserProfile user = new UserProfile(id,name,contactNo,hoobies);
                firebaseRef.child(id).setValue(user);
                Toast.makeText(getApplicationContext(), "user record updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
