package com.scpfoundation.psybotic.app.ui.login;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.ui.main.MainActivity;
import com.scpfoundation.psybotic.app.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener, LocationListener {

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    RequestQueue requestQueue;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private ProgressDialog dialog;
    private GoogleSignInAccount account;
    private boolean alreadySigned = false;


    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String lat;

    protected String latitude, longitude;

    protected boolean gps_enabled, network_enabled;


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         * Check if user already signed in via a google account, if yes go to MainActivity
         */
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            alreadySigned = true;
            login(account);
            updateUI(account);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            final User user = new User();
            user.setEmail(account.getEmail());
            user.setFirstName(account.getGivenName());
            user.setLastName(account.getFamilyName());
            user.setGoogleId(account.getId());
            Context context = this;

            /*
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //LocationProvider provider=locationManager.getProvider(LocationManager.GPS_PROVIDER);
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            //System.out.println(lastKnownLocation.getTime());
            System.out.println(lastKnownLocation);
            System.out.println("latitude"+lastKnownLocation.getLatitude());
            System.out.println("longitude"+lastKnownLocation.getLongitude());

            LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, this);
            Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            System.out.println(location.getLongitude());
            System.out.println(location.getLatitude());

            /*

             */
            /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationProvider provider=locationManager.getProvider(LocationManager.GPS_PROVIDER);
            final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!gpsEnabled)
            {
                System.out.println("Izin problemi");
            }
            else
            {
                System.out.println(provider.getName());
            }
            */


            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            String token;
                            if (!task.isSuccessful()) {
                                Log.w("Device token exception", "Fetching FCM registration token failed", task.getException());
                                return;
                            } else {
                                // Get new FCM registration token
                                 token = task.getResult();
                                user.setDeviceToken(token);
                            }

                            Log.d("Device token: ", token);
                            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, HOST + "/users/login",
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dialog.cancel();
                                    updateUI(account);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.cancel();
                                    System.err.println(error.getMessage());
                                }
                            }) {
                                @Override
                                public byte[] getBody() {
                                    Gson gson = new Gson();
                                    String body = gson.toJson(user);
                                    return body.getBytes();
                                }
                            };
                            dialog = ProgressDialog.show(context, "",
                                    "Loading. Please wait...", true);
                            requestQueue.add(req);
                            dialog.cancel();
                        }
                    });



            login(account);
            showDialog(this);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace();
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account", account);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("Oluştu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.google_login_button).setOnClickListener(this);
        findViewById(R.id.guest_login_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                signIn();
                break;
            case R.id.guest_login_button:
                updateUI(null);
                break;
            default:
                Toast.makeText(this, "Button not defined in onClickListener", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.cancel();
    }

    @Override
    public void onResponse(JSONObject response) {
        dialog.cancel();
        if (!alreadySigned) {
            updateUI(account);
        }
    }

    private User setUser(GoogleSignInAccount account) {
        User user = new User();
        user.setEmail(account.getEmail());
        user.setFirstName(account.getGivenName());
        user.setLastName(account.getFamilyName());
        user.setGoogleId(account.getId());
        return user;
    }

    private JsonObjectRequest createLoginRequest(User user) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, HOST + "/users/login",
            null, response -> {
            if (dialog != null) {
                dialog.cancel();
            }
                updateUI(account);
            }, error -> {
            if (dialog != null) {
                dialog.cancel();
            }
                System.err.println(error.getMessage());
            }) {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                String body = gson.toJson(user);
                return body.getBytes();
            }
        };
        return req;
    }

    private void login(GoogleSignInAccount account) {
        final User user = setUser(account);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    String token;
                    if (!task.isSuccessful()) {
                        Log.w("Device token exception", "Fetching FCM registration token failed", task.getException());
                        return;
                    } else {
                        // Get new FCM registration token
                        token = task.getResult();
                        user.setDeviceToken(token);
                    }
                    Log.d("Device token: ", token);
                    JsonObjectRequest req = createLoginRequest(user);
                    requestQueue.add(req);
                });
    }

    private void showDialog(Context context) {
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

}