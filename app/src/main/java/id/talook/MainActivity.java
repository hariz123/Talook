package id.talook;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TabLayout tab_layout;
    Fragment selectedFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedFragment = ArtikelFragment.newInstance();
        initComponent();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
    }

    private void initComponent() {
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_home), 0);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_favorite_border), 1);
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_person), 2);

        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources()
                .getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources()
                .getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources()
                .getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, selectedFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, new ExploreFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, selectedFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.deep_orange_500),
                        PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
