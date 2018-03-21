package youssef.com.commercial_student;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static youssef.com.commercial_student.R.id.ss;

/**
 * Created by mohamed on 26/02/2018.
 */

public class menu extends Fragment {
    CircleImageView proilephoto;
    Context context;
    ProgressBar progressBar;
    EditText Fname,Lname;
    TextView uploadphoto,profilenamee;
    String downloadedurl;
    Button savebutton;
    private FirebaseAuth mAuth;
    Uri uriprofileimage;
    private static final int image = 101;
    Profile pro=new Profile();
    String user;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reft=database.getReference("usercomment");
    ArrayList<String> vv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadphoto=(TextView)view.findViewById(R.id.addphoto);
        progressBar=(ProgressBar)view.findViewById(R.id.proimage);
        Fname=(EditText)view.findViewById(R.id.firstn);
        Lname=(EditText)view.findViewById(R.id.Lastn);
        mAuth=FirebaseAuth.getInstance();
        vv=new ArrayList<>();
        context=view.getContext();
        proilephoto=(CircleImageView)view.findViewById(R.id.photoshow);
        profilenamee=(TextView)view.findViewById(R.id.username);
        /*
        reft.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for (DataSnapshot snap:dataSnapshot.getChildren())
             {
                 String s=snap.getKey();
                 vv.add(s);

             }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

     loaduserinfo();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == image && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriprofileimage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriprofileimage);

                proilephoto.setBackground(null);
                proilephoto.setImageBitmap(bitmap);

                uploadimagetofirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadimagetofirebase() {

        StorageReference profileimageref = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
        if (uriprofileimage != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileimageref.putFile(uriprofileimage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            downloadedurl = downloadUrl.toString();
                            Toast.makeText(context, "photo updated", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
        }
    }

    private void saveuserinfo() {
        String username = Fname.getText().toString() + Lname.getText().toString();
        if (username.isEmpty()) {
            Fname.setError("name required");
            Fname.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && downloadedurl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .setPhotoUri(Uri.parse(downloadedurl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "profile updated", Toast.LENGTH_LONG).show();

                            }
                            if (!task.isSuccessful()) {
                                Toast.makeText(context, "profile not updated", Toast.LENGTH_LONG).show();


                            }
                        }

                    });
        }

    }

    private void loaduserinfo() {


        String photourl;
        String usernamee;
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                photourl = user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(photourl)
                        .into(proilephoto);
            }
            if (user.getDisplayName() != null) {
                usernamee = user.getDisplayName();
                if (usernamee != null)
                    profilenamee.setText(usernamee);


            }
        }
    }
}
