package youssef.com.commercial_student;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    TabLayout mytab;
    ViewPager mypager;
    adapter myadapter;
    Toolbar mytool,ss;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mytab=(TabLayout)findViewById(R.id.mytablayout);
        mytool=(Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(mytool);
        mauth=FirebaseAuth.getInstance();

        myadapter=new adapter(getSupportFragmentManager());
        mypager=(ViewPager)findViewById(R.id.viewpager);
        mypager.setAdapter(myadapter);
        mytab.setupWithViewPager(mypager);

        final TabLayout.Tab menu=mytab.newTab();
        final TabLayout.Tab first=mytab.newTab();
        final TabLayout.Tab second=mytab.newTab();
        final TabLayout.Tab third=mytab.newTab();
        final TabLayout.Tab forth=mytab.newTab();
        menu.setIcon(R.drawable.menublue);
        first.setIcon(R.drawable.first);
        second.setIcon(R.drawable.second);
        third.setIcon(R.drawable.third);
        forth.setIcon(R.drawable.forth);

        mytab.addTab(menu,0);
        mytab.addTab(first,1);
        mytab.addTab(second,2);
        mytab.addTab(third,3);
        mytab.addTab(forth,4);

        for(int i=0; i < mytab.getTabCount(); i++) {
            View tab = ((ViewGroup) mytab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 0, 0);
            tab.requestLayout();
        }

        mypager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mytab));


        mypager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0: {
                        menu.setIcon(R.drawable.menublue);
                        first.setIcon(R.drawable.first);
                        second.setIcon(R.drawable.second);
                        third.setIcon(R.drawable.third);
                        forth.setIcon(R.drawable.forth);
                        break;
                    }
                    case 1: {
                        menu.setIcon(R.drawable.menublack);
                        first.setIcon(R.drawable.firstblue);
                        second.setIcon(R.drawable.second);
                        third.setIcon(R.drawable.third);
                        forth.setIcon(R.drawable.forth);
                        break;
                    }
                    case 2:{
                        menu.setIcon(R.drawable.menublack);
                        first.setIcon(R.drawable.first);
                        second.setIcon(R.drawable.secondblue);
                        third.setIcon(R.drawable.third);
                        forth.setIcon(R.drawable.forth);
                        break;
                    }
                    case 3:{
                        menu.setIcon(R.drawable.menublack);
                        first.setIcon(R.drawable.first);
                        second.setIcon(R.drawable.second);
                        third.setIcon(R.drawable.thirdblue);
                        forth.setIcon(R.drawable.forth);
                        break;
                    }
                    case 4:{
                        menu.setIcon(R.drawable.menublack);
                        first.setIcon(R.drawable.first);
                        second.setIcon(R.drawable.second);
                        third.setIcon(R.drawable.third);
                        forth.setIcon(R.drawable.forthblue);
                        break;

                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });








    }

    private class adapter extends FragmentStatePagerAdapter {

        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position)
            {
                case 0:fragment=new menu();
                    break;
                case 1:fragment=new firstyear();
                    break;
                case 2:fragment=new secyear();
                    break;
                case 3:fragment=new thirdyear();
                    break;
                case 4:fragment=new forthyear();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }


    }

    protected void onStart() {
        super.onStart();

        if (mauth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Home.this, MainActivity.class));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout)
        {

            mauth.signOut();
            finish();
            startActivity(new Intent(Home.this,MainActivity.class));
        }

        return true;
    }
}
