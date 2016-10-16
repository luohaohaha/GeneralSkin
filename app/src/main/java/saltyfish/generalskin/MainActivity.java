package saltyfish.generalskin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import saltyfish.library.base.SaltyfishBaseActivity;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

public class MainActivity extends SaltyfishBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> titles = Collections.synchronizedList(new LinkedList<String>());
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));

        final List<Fragment> fragments = Collections.synchronizedList(new LinkedList<Fragment>());
        fragments.add(new WidgetFragment());
        fragments.add(new TabFragment2());
        fragments.add(new TabFragment3());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saltyfish_setting,menu);
       MenuItem menuItem= menu.findItem(R.id.action_setting);
        menuItem.setIcon(getSaltyfishInstance(SaltyfishSkinColorTool.class).changeDrawableColor(getResources().getDrawable(R.mipmap.ic_settings), Color.WHITE));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.action_setting==item.getItemId()) {

            startActivity(new Intent(this,SaltyfishSetSkinColorActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

}
