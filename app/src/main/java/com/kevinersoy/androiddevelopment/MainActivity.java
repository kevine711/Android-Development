package com.kevinersoy.androiddevelopment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
    implements Selector.OnFragmentSelectorListener, Location.OnFragmentLocationListener,
    ListItems.OnFragmentListItemsListener {
    private static final String LOCATION_FRAGMENT = "com.kevinersoy.androiddevelopment.LOCATION_FRAGMENT";
    private static final String LIST_ITEMS_FRAGMENT = "com.kevinersoy.androiddevelopment.LIST_ITEMS_FRAGMENT";
    private static final String CURRENT_RIGHT_FRAGMENT = "com.kevinersoy.androiddevelopment.CURRENT_RIGHT_FRAGMENT";
    private String currentRightFragment = LOCATION_FRAGMENT;
    public static final String LOCATION_BUTTON = "com.kevinersoy.androiddevelopment.LOCATION_BUTTON";
    public static final String LIST_ITEMS_BUTTON = "com.kevinersoy.androiddevelopment.LIST_ITEMS_BUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //set fragment left
        FrameLayout layoutLeft = findViewById(R.id.fragment_left);
        ft.replace(layoutLeft.getId(), new Selector());

        //deal with fragment right present or not
        if(savedInstanceState != null)
            currentRightFragment = savedInstanceState.getString(CURRENT_RIGHT_FRAGMENT);
        FrameLayout layoutRight = findViewById(R.id.fragment_right);
        if(layoutRight != null){
            switch(currentRightFragment){
                case(LOCATION_FRAGMENT):
                    ft.replace(layoutRight.getId(), new Location());
                    break;
                case(LIST_ITEMS_FRAGMENT):
                    ft.replace(layoutRight.getId(), new ListItems());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown fragment recalled" + currentRightFragment);
            }
        }
        ft.commit();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_RIGHT_FRAGMENT, currentRightFragment);
    }

    @Override
    public void onFragmentSelectorInteraction(String string) {
        switch (string) {
            case LOCATION_BUTTON:
                //set location fragment in right if landscape or left if portrait
                currentRightFragment = LOCATION_FRAGMENT;
                if(findViewById(R.id.fragment_right) != null)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_right, new Location())
                            .commit();
                else
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_left, new Location())
                            .addToBackStack(null)
                            .commit();
                break;
            case LIST_ITEMS_BUTTON:
                //set location fragment in right if landscape or left if portrait
                currentRightFragment = LIST_ITEMS_FRAGMENT;
                if(findViewById(R.id.fragment_right) != null)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_right, new ListItems())
                            .commit();
                else
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_left, new ListItems())
                            .addToBackStack(null)
                            .commit();
                break;
            default:
                throw new IllegalArgumentException("Unknown button pressed" + string);
        }

    }

    @Override
    public void onFragmentLocationInteraction(Uri uri) {
        //Not using this functionality yet
    }

    @Override
    public void onFragmentListItemsInteraction(String string) {
        //Not using this functionality yet
    }
}
