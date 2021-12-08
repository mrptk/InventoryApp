package pl.app.projektio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import pl.app.projektio.ServerService.ServiceBinder;

public class LogInActivity extends AppCompatActivity {
    public ServerService service;
    ProgressBar progressBar;
    private boolean isBound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ServiceBinder binder = (ServiceBinder) iBinder;
            service = binder.getService();
            isBound = true;
            if (service.getToken() != null) {
                startActivity(new Intent(getBaseContext(), DashboardActivity.class));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLoginActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ServerService.class); // Startuje Service do komunikacji z serwerem
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void logInClick(View view) {
        progressBar.setVisibility(View.VISIBLE); // Pokazuje progress bar

        EditText username = findViewById(R.id.editTextUsernameLoginActivity);
        EditText password = findViewById(R.id.editTextPasswordLoginActivity);
        TextView msg = findViewById(R.id.textViewMessageLoginActivity);

        service.logIn(username.getText().toString(), password.getText().toString());

        switch(service.getResponse()){
            case 200:
                msg.setText(R.string.logInActivityLoginSuccessfulMessageLabel);
                if (service.getUserGroupID() == 1) startActivity(new Intent(this, DashboardActivity.class));
                else startActivity(new Intent(this, InventoryDashboardActivity.class));
                break;
            case 401:
                msg.setText(R.string.logInActivityLoginFailedMessageLabel);
                break;
            default:
                String msgText = R.string.logInActivityLoginErrorMessageLabel + "\n" + (service.isError() ? service.getExceptionMsg() : "");
                msg.setText(msgText);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}