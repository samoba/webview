package com.samoba.remo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.ads.mediation.inmobi.InMobiAdapter;
import com.google.ads.mediation.inmobi.InMobiConsent;
import com.google.ads.mediation.inmobi.InMobiNetworkKeys;
import com.google.ads.mediation.inmobi.InMobiNetworkValues;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;
import com.startapp.mediation.admob.StartappAdapter;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextView textView;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        StartAppSDK.setTestAdsEnabled(true);
        TextView textView = (TextView) findViewById(R.id.nxt);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.rs);
        textView.setText(mFirebaseRemoteConfig.getString("namew"));
        mFirebaseRemoteConfig.fetchAndActivate();
        Banner();

        Gd();
      

    }
    public void Banner(){
        View view = findViewById(R.id.bannar);
        mAdView  = new AdView(MainActivity.this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("ca-app-pub-2929991619820450/5175993496");
        AdRequest adRequest = new AdRequest.Builder().build();
        ((RelativeLayout) view).addView(mAdView);
        mAdView.loadAd(adRequest);

    }

    public void Gd(){

        JSONObject consentObject = new JSONObject();
        try {
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            consentObject.put("gdpr", "1");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        InMobiConsent.updateGDPRConsent(consentObject);

    }

    public void Startapp(){
        final Bundle extras = new StartappAdapter.Extras.Builder()
                .setAdTag("bannerTagFromAdRequest")
                .enable3DBanner()
                .setMinCPM(0.01)
                .toBundle();

        mAdView.loadAd(new AdRequest.Builder()
                .addCustomEventExtrasBundle(StartappAdapter.class, extras)
                .build());

    }
}
