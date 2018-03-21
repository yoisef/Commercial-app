package youssef.com.commercial_student;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static youssef.com.commercial_student.R.attr.layoutManager;

/**
 * Created by mohamed on 26/02/2018.
 */

public class firstyear extends Fragment {
    RecyclerView recyclefirst,recyvlesecond;
    RecyclerView.LayoutManager manager;
    TextView secondterm;
    ScrollView scrollView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        secondterm=(TextView)view.findViewById(R.id.term2);
        scrollView=(ScrollView)view.findViewById(R.id.scroll);

        secondterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.post(new Runnable() {

                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });


        StorageReference reference= FirebaseStorage.getInstance().getReference("books/mohasba.rar");

        recyclefirst=(RecyclerView)view.findViewById(R.id.recycleview);
        recyclefirst.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myadapterf adapter=new myadapterf(this.getActivity(),this.getView());
        recyclefirst.setAdapter(adapter);

        recyclefirst.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        recyvlesecond=(RecyclerView)view.findViewById(R.id.recycleviw2);
        recyvlesecond.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myadapter2 adapter2=new myadapter2(this.getActivity(),this.getView());
        recyvlesecond.setAdapter(adapter2);

        recyvlesecond.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }


}
