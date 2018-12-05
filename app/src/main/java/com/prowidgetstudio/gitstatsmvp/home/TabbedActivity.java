package com.prowidgetstudio.gitstatsmvp.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prowidgetstudio.gitstatsmvp.BaseActivity;
import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.customViews.CustomSwipeToRefresh;
import com.prowidgetstudio.gitstatsmvp.customViews.LockableViewPager;
import com.prowidgetstudio.gitstatsmvp.customViews.LogOutDialog;
import com.prowidgetstudio.gitstatsmvp.customViews.PagerAdapter;
import com.prowidgetstudio.gitstatsmvp.login.LoginActivity;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;


public class TabbedActivity extends BaseActivity implements TabbedView{

    private CustomSwipeToRefresh swipeContainer;
    private LogOutDialog dialog;
    private LockableViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private WebView webView;
    private RelativeLayout downloadLayout;
    private ConstraintLayout layout;

    private TextView downloaded, fullName, description;
    private int plava, zelena, zuta, siva;

    private TabbedPresenterImpl presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_tabbed;
    }

    @Override
    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        plava = getResources().getColor(R.color.plava);
        zelena = getResources().getColor(R.color.zelena);
        zuta = getResources().getColor(R.color.zuta);
        siva = getResources().getColor(R.color.siva);
        final int transparentna = getResources().getColor(R.color.transparentna);

        toolbar.setTitleTextColor(transparentna);
        toolbar.setBackgroundColor(plava);

        // portrait / landscape mode
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        swipeContainer = (CustomSwipeToRefresh) findViewById(R.id.swipeContainer);
        downloadLayout = (RelativeLayout) findViewById(R.id.downloadLayout);
        layout = (ConstraintLayout) findViewById(R.id.main_content);
        downloaded = (TextView) findViewById(R.id.downloaded);
        fullName = (TextView) findViewById(R.id.fullName);
        description = (TextView) findViewById(R.id.description);
        webView = (WebView) findViewById(R.id.webView);

        tabLayout.addTab(tabLayout.newTab().setText("Day"));
        tabLayout.addTab(tabLayout.newTab().setText("Week"));
        tabLayout.addTab(tabLayout.newTab().setText("Month"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(plava);
        tabLayout.setTabTextColors(siva, plava);
        swipeContainer.setColorSchemeResources(R.color.plava, R.color.zelena, R.color.zuta);

        tabsInit();
        pullDownUpdate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Context context = getApplicationContext();
        RepositoryImpl repository = new RepositoryImpl(prefs, context);
        presenter = new TabbedPresenterImpl(this, new TabbedInteractorImpl(), repository);
        presenter.getInfoData();
    }

    private void tabsInit(){

        //LockableViewPager
        viewPager = (LockableViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        // tab deviders
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.siva));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(20);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.reMeasureCurrentPage(viewPager.getCurrentItem());

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(plava);
                        tabLayout.setTabTextColors(siva, plava);
                        toolbar.setBackgroundColor(plava);
                        break;
                    case 1:
                        tabLayout.setSelectedTabIndicatorColor(zelena);
                        tabLayout.setTabTextColors(siva, zelena);
                        toolbar.setBackgroundColor(zelena);
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(zuta);
                        tabLayout.setTabTextColors(siva, zuta);
                        toolbar.setBackgroundColor(zuta);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void pullDownUpdate() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.downloadData(false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            showConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProjectInfo(String name, String descr, String readMe){

        fullName.setText(name);
        description.setText(descr);
        webView.loadDataWithBaseURL("", readMe, "text/html", "UTF-8", "");
    }


    private void showConfirmationDialog() {

        OnLogOutDialogClickListener dialogListener = new OnLogOutDialogClickListener() {
            @Override
            public void onYes() {
                presenter.logOut();
            }
        };
        dialog = new LogOutDialog(TabbedActivity.this, dialogListener);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    @Override
    protected void onStop() {
        super.onStop();

        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {

        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void stopDownload(View v) {
        presenter.stopDownload();
    }

    @Override
    public void showLoading(int count) {
        downloadLayout.setVisibility(View.VISIBLE);
        String strCount = Integer.toString(count);
        downloaded.setText(strCount);
    }

    @Override
    public void hideLoading(){
        downloadLayout.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void noAccess() {
        Snackbar snackBar = Snackbar.make(layout, "No internet access", 10000);
        snackBar.getView().setBackgroundColor(ContextCompat.getColor(TabbedActivity.this, R.color.crvena));
        snackBar.show();
    }

    @Override
    public void logOut(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
