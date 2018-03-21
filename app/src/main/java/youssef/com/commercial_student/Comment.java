package youssef.com.commercial_student;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment extends AppCompatActivity {

    RecyclerView commentrecycle;
    ImageView sendimage;
    FirebaseAuth mauth;
    EditText usercomment;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference refcomments, refuser, username,userimges;
    ArrayList<String> commentslist, photplist, nameslist;
    String userid, key;
    Iterable<DataSnapshot> iterable;
    ArrayList<String> userkeys;
    Profile profile;
    String usern,photon;
    int i,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentslist = new ArrayList<>();
        photplist = new ArrayList<>();
        nameslist = new ArrayList<>();
        userkeys = new ArrayList<>();
        mauth = FirebaseAuth.getInstance();
        refcomments = firebaseDatabase.getReference("Comments");
        refuser = firebaseDatabase.getReference("usercomment");
        username = firebaseDatabase.getReference("userkeys");
        userimges=firebaseDatabase.getReference("userimg");


        usercomment = (EditText) findViewById(R.id.usercomment);
        commentrecycle = (RecyclerView) findViewById(R.id.recyclecomment);
        final commentadapter commentadapter = new commentadapter(this);
        commentrecycle.setAdapter(commentadapter);

        profile = new Profile();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        commentrecycle.setLayoutManager(linearLayoutManager);


        sendimage = (ImageView) findViewById(R.id.sendimg);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference refcomments = firebaseDatabase.getReference("Comments");


  /*
        refuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 DataSnapshot snap:dataSnapshot.getChildren())
                {
                    key= snap.getKey();
                    userkeys.add(key);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refuser.child(userkeys.get(0)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userid= dataSnapshot.child("username").getValue().toString();
                userphoto= dataSnapshot.child("userphoto").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


        sendimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usercom = usercomment.getText().toString();
                refcomments.push().setValue(usercom);
                usercomment.setText(null);

            }
        });

       String username;
      FirebaseUser user=mauth.getCurrentUser();
        if (user!=null) {
            if (user.getDisplayName() != null) {
                username= user.getDisplayName();
                refuser.child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> map = ((Map<String, String>) dataSnapshot.getValue());
                        usern = map.get("username");
                        nameslist.add(usern);
                        commentadapter.notifyDataSetChanged();
                        photon = map.get("userphoto");
                        photplist.add(photon);
                        commentadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }





        refcomments.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String comm = dataSnapshot.getValue().toString();
                commentslist.add(comm);
                commentadapter.notifyDataSetChanged();
                commentrecycle.smoothScrollToPosition(commentslist.size() - 1);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    /**
     * Created by mohamed on 14/03/2018.
     */

    public class commentadapter extends RecyclerView.Adapter<commentadapter.viewholder> {

        ArrayList<String> arrayList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser myuser;

        Context context;

        public commentadapter(Context context) {
            this.context = context;
            DatabaseReference comref = database.getReference("Comments");
        }

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.commentrow, parent, false);
            viewholder holder = new viewholder(view);

            return holder;
        }


        @Override
        public void onBindViewHolder(viewholder holder, int position) {

           i=nameslist.size();
            z=photplist.size();





            holder.username.setText("youssef essam");
            holder.userpphoto.setImageDrawable(getResources().getDrawable(R.drawable.unknown));
            holder.usercomment.setText(commentslist.get(position));

            }
        public int getImage(String imageName) {

            int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

            return drawableResourceId;
        }





        @Override
        public int getItemCount() {
            return commentslist.size();
        }

      public class viewholder extends RecyclerView.ViewHolder  {

          TextView username,usercomment;
          CircleImageView userpphoto;


         public viewholder(View itemview)
         {
             super(itemview);
             username=(TextView)itemview.findViewById(R.id.nameshow);
             usercomment=(TextView)itemview.findViewById(R.id.commentshow);
             userpphoto=(CircleImageView)itemview.findViewById(R.id.photoshow);

         }



      }
    }
}
