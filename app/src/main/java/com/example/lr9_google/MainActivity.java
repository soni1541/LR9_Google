package com.example.lr9_google;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = (Button) findViewById(R.id.login_btn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = gsc.getSignInIntent();
                GoogleSignInLauncher.launch(intent);
            }
        });

    }


    ActivityResultLauncher<Intent> GoogleSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                task.getResult(ApiException.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Вход успешен", Toast.LENGTH_LONG);
                toast.show();

                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (account != null) {
                    String name = account.getDisplayName();
                    String email = account.getEmail();

                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                    i.putExtra("username", name);
                    i.putExtra("email", email);
                    startActivity(i);

//                    Toast toast_account =
//                            Toast.makeText(getApplicationContext(),
//                                    "Добро пожаловать, " + name + "(" + email + ")", Toast.LENGTH_LONG);
//                    toast_account.show();
                }

            } catch (ApiException e) {
                Toast toast = Toast.makeText(getApplicationContext(),"Ошибка входа", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    });
}