package youssef.com.commercial_student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mohamed on 01/03/2018.
 */

public class myadapter2 extends RecyclerView.Adapter<myadapter2.myviewholder> {
    Context contextt;
    ArrayList<String> matrieal=new ArrayList<>();
    ArrayList<String> doctors=new ArrayList<>();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference refmatrieals=database.getReference("yeat1").child("term2").child("matrieal");
    DatabaseReference refdoctors=database.getReference("yeat1").child("term2").child("doctors");
    String a,b,c,d,e,f;
    RecyclerView recv2;
    int[]numbers={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};

    public myadapter2(Context context,View view)
    {
        this.contextt=context;
        recv2=(RecyclerView)view.findViewById(R.id.recycleviw2);
        refmatrieals.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            matrieal= (ArrayList<String>)dataSnapshot.getValue();
            recv2.getAdapter().notifyDataSetChanged();


    }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refdoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,String>map= ((Map<String, String>) dataSnapshot.getValue());
                a=map.get("a");
                b=map.get("b");
                c=map.get("c");
                d=map.get("d");
                e=map.get("e");
                f=map.get("f");
                doctors.add(a);
                doctors.add(b);
                doctors.add(c);
                doctors.add(d);
                doctors.add(e);
                doctors.add(f);
                recv2.getAdapter().notifyDataSetChanged();


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
        holder.number.setImageResource(numbers[position]);



    }

    @Override
    public int getItemCount() {
        return matrieal.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView matrieal,doctor;
        LinearLayout comment;
        ImageView number;

        public myviewholder(View itemView) {
            super(itemView);

            matrieal=(TextView)itemView.findViewById(R.id.matrieal);
            doctor=(TextView)itemView.findViewById(R.id.doctors);
            comment=(LinearLayout)itemView.findViewById(R.id.commentlayout);
            number=(ImageView)itemView.findViewById(R.id.number);
        }
    }
}