package com.example.com.book_app2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Toolbar myToolbar;
    TextView boogleTv;
    ImageView settingsV;
    SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        myToolbar= (Toolbar) findViewById(R.id.toolbar_frame);
        setSupportActionBar(myToolbar);

        setUpCustomAppBar();
        searchViewOnclickFunctions();

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.your_placeholder, new SettingsFragnment())
                .commit();
    }


    /**
     * for setting up the appbar view
     */
    private void setUpCustomAppBar(){
        myToolbar= (Toolbar) findViewById(R.id.toolbar_frame);
        setSupportActionBar(myToolbar);

        boogleTv= findViewById(R.id.tooltext);

        settingsV= findViewById(R.id.settings);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    /**
     * This is to hide and display the @boogleTv and @settingsV
     * and to set navigation to home page and settings page
     */
    private void searchViewOnclickFunctions(){

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boogleTv.setVisibility(View.INVISIBLE);
                settingsV.setVisibility(View.INVISIBLE);

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                boogleTv.setVisibility(View.VISIBLE);
                settingsV.setVisibility(View.VISIBLE);
                return false;
            }
        });

        boogleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        settingsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
    }

    public  static class SettingsFragnment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settingsxml);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("Max_Search_Result")){
                EditTextPreference preference = (EditTextPreference) findPreference(key);
                preference.setSummary(sharedPreferences.getInt(key,0));
                int see =sharedPreferences.getInt(key,0);
                Toast.makeText(getActivity(),""+see,Toast.LENGTH_SHORT).show();

            }
        }
    }


}
