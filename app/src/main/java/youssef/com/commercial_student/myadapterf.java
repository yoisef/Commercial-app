package youssef.com.commercial_student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 01/03/2018.
 */

public class myadapterf extends RecyclerView.Adapter<myadapterf.myviewholder> {

    Context contextt;
    firstyear f1year;
    ArrayList<String>matrieal=new ArrayList<>();
   RecyclerView recv1,recv2;
    myadapterf adapter;
    String dom,doce,doctyad,vevo,zezo,lezo;


    ArrayList<String> doctors=new ArrayList<>();
    int[]numbers={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference refmatrieals=database.getReference("yeat1").child("term1").child("matrieal");
    DatabaseReference refdoctors=database.getReference("yeat1").child("term1").child("doctors");
    public myadapterf(Context context,View view)
    {
        this.contextt=context;
        recv1=(RecyclerView)view.findViewById(R.id.recycleview);


        refmatrieals.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String matr=dataSnapshot.getValue().toString();
                matrieal.add(matr);
                recv1.getAdapter().notifyDataSetChanged();


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

        refdoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String>map= ((Map<String, String>) dataSnapshot.getValue());
                dom=map.get("mohasba");
                doce=map.get("ryada");
                doctyad=map.get("edara");
                vevo=map.get("aa");
                zezo=map.get("qq");
               lezo= map.get("zz");

                doctors.add(dom);
                doctors.add(doce);
                doctors.add(doctyad);
                doctors.add(vevo);
                doctors.add(zezo);
                doctors.add(lezo);
                recv1.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) contextt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.rowrecycle,parent,false);

        myviewholder holder=new myviewholder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(final myviewholder holder, final int position) {

        holder.matrieal.setText(matrieal.get(position));
        holder.doctor.setText(doctors.get(position));
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position)
                {
                    case 0:Intent site, chooser;
                        site = new Intent(Intent.ACTION_VIEW);
                        site.setData(Uri.parse("http://www.mediafire.com/file/rwjsae1qjrbott1/%D8%A3%D9%88%D8%B1%D8%A7%D9%82+%D9%85%D8%B9%D9%84%D9%88%D9%85%D8%A7%D8%AA+-+%D9%85%D8%A8%D8%A7%D8%AF%D8%A6+%D8%A7%D9%84%D9%85%D8%AD%D8%A7%D8%B3%D8%A8%D8%A9+%D8%A7%D9%84%D9%85%D8%A7%D9%84%D9%8A%D8%A9.rar"));
                        chooser = Intent.createChooser(site, "launchsite");
                        contextt.startActivity(chooser);
                        break;
                    case 1:Intent z, a;
                        z = new Intent(Intent.ACTION_VIEW);
                        z.setData(Uri.parse("https://www.google.com.eg/?gfe_rd=cr&dcr=0&ei=3DayWp60C9CFhgPysaqYBA"));
                        a = Intent.createChooser(z, "launchsite");
                        contextt.startActivity(a);
                        break;
                }
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position)
                {
                    case 0:{
                        Intent i=new Intent(contextt,Comment.class);
                        contextt.startActivity(i);
                    }
                }
            }
        });



            holder.number.setImageResource(numbers[position]);





    }


    @Override
    public int getItemCount() {
        return matrieal.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView matrieal,doctor;
        LinearLayout comment,download;
        ImageView number;

        public myviewholder(View itemView) {
            super(itemView);

            matrieal = (TextView) itemView.findViewById(R.id.matrieal);
            doctor = (TextView) itemView.findViewById(R.id.doctors);
            comment = (LinearLayout) itemView.findViewById(R.id.commentlayout);
            download = (LinearLayout) itemView.findViewById(R.id.comment);
            number = (ImageView) itemView.findViewById(R.id.number);
        }
    }
}