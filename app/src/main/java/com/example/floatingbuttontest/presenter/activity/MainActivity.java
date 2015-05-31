package com.example.floatingbuttontest.presenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.presenter.adapter.MyItemDecoration;
import com.example.floatingbuttontest.presenter.fragment.ContentFragment;
import com.example.floatingbuttontest.presenter.fragment.PictureFragment;
import com.example.floatingbuttontest.presenter.fragment.ViewPagerIndicator;
import com.example.floatingbuttontest.presenter.fragment.VpSimpleFragment;
import com.example.floatingbuttontest.presenter.adapter.HomeAdapter;
import com.example.floatingbuttontest.util.UpdateContent;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ContentFragment.OnFragmentInteractionListener, PictureFragment.OnFragmentInteractionListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ShareActionProvider mShareActionProvider;

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private HomeAdapter nAdapter;


    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("妹子图", "段子", "小电影", "主题4",
            "主题5", "主题6", "主题7", "主题8", "主题9");
    private List<String> nDatas = Arrays.asList("主题1", "主题2", "主题3", "主题4",
            "主题5", "主题6", "主题7");

    private ViewPagerIndicator mIndicator;


    private PictureFragment mPictureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
        //设置Tab上的标题
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration(this));
        mRecyclerView.setAdapter(nAdapter = new HomeAdapter(mList));
        nAdapter.setOnItemClickListener(new HomeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                switch (data) {
                    case "A":
                        Intent intent1 = new Intent(MainActivity.this, FirstActivity.class);
                        startActivity(intent1);
                        break;
                    case "B":
                        Intent intent2 = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;

                }


            }
        });


        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setTitle("煎蛋");
        mToolbar.setSubtitle("给你幸福的APP~");
        setSupportActionBar(mToolbar);


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_LONG).show();
                        UpdateContent manager = new UpdateContent(MainActivity.this);
                        manager.checkUpdate();
                        break;
                    case R.id.action_share:
                        Toast.makeText(MainActivity.this, "action_share", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu
                .findItem(R.id.action_share));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initDatas() {
        mTabContents = new ArrayList<Fragment>();
        ContentFragment mContentFragment = ContentFragment.newInstance("RGB", "ARGB");
        mPictureFragment = new PictureFragment();
        mTabContents.add(mPictureFragment);
        mTabContents.add(mContentFragment);
        for (String data : nDatas) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };

        mList = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mList.add("" + (char) i);
        }
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
