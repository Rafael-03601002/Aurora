package com.arctic.aurora;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private TextInputEditText description;
    private ImageView image;
    private ActivityResultLauncher<Intent> launcher;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView confirm = findViewById(R.id.explore_post_confirm);
        ImageView cancel = findViewById(R.id.explore_post_cancel);
        image = findViewById(R.id.explore_post_image);
        description = findViewById(R.id.explore_post_description);

        confirm.setOnClickListener(btn_confirm);
        cancel.setOnClickListener(btn_cancel);

        // get image
        initializeLauncher();
        image.setOnClickListener(view -> selectImage());
    }

    private final View.OnClickListener btn_confirm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // get firebase user to generate unique fileName
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            // get database to prepare storing data
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // upload image to firebase storage
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
            Date now = new Date();
            String fileName = formatter.format(now);
            assert user != null;
            String uriString = "image/" + user.getEmail() + "/" + fileName + ".jpg";
            FirebaseStorage webStorage = FirebaseStorage.getInstance();
            StorageReference reference = webStorage.getReference(uriString);
            reference.putFile(imageUri);

            //store uri and desc into database
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String desc = String.valueOf(description.getText());
            String userName = user.getDisplayName();

            Explore explore = new Explore(userName, uriString, desc, dateFormat.format(now));

            db.collection("explore").add(explore);
            finish();
        }
    };
    private final View.OnClickListener btn_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    // Common function
    private void initializeLauncher() {
        // Using Intent to get image from local storage

        // initialize launcher
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    try {
                        imageUri = result.getData().getData();
                        image.setImageURI(imageUri);
                    }
                    catch (Exception e) {
                        Toast.makeText(PostActivity.this, "No Image selected", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void selectImage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && android.os.ext.SdkExtensions.getExtensionVersion(android.os.Build.VERSION_CODES.R) >= 2) {
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            launcher.launch(intent);
        }
    }
}