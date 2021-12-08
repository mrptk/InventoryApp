package pl.app.projektio;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import pl.app.projektio.ServerService.ServiceBinder;
import pl.app.projektio.assets.*;
import pl.app.projektio.dialogs.AddItemFragment;
import pl.app.projektio.dialogs.EditItemFragment;
import pl.app.projektio.fragments.ManagerUIViewDatabaseFragment;
import pl.app.projektio.fragments.ManagerUIViewReportsFragment;

public class DashboardActivity extends AppCompatActivity {
    public BuildingList buildings;
    public FloorList floors;
    public RoomList rooms;
    public ItemList items;
    public InventoryList inventories;
    public ServerService service;
    public String scanResult;

    private LinearLayout buttons;
    private boolean isBound = false;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ServiceBinder binder = (ServiceBinder) iBinder;
            service = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_manager);
        buttons = findViewById(R.id.linearLayoutDashManActivity);

        buttons.setBackgroundResource(R.drawable.activity_manager_buttonlayout_reports_button_active);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerDashManActivity, ManagerUIViewReportsFragment.class, null)
                .commit();

    }

    @Override
    protected void onStart() {
        if (service != null && service.chosenItem != null) {
            items = new ItemList(service.getItems());
            items.setChosenItem(service.chosenItem);
        }

        super.onStart();
        Intent intent = new Intent(this, ServerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void databaseClick(View view) {
        buttons.setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);
        buildings = new BuildingList(service.getBuildings());
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerDashManActivity, ManagerUIViewDatabaseFragment.class, null, "view_database")
                .commit();
    }

    public void reportsClick(View view) {
        buttons.setBackgroundResource(R.drawable.activity_manager_buttonlayout_reports_button_active);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerDashManActivity, ManagerUIViewReportsFragment.class, null)
                .commit();
    }

    public void showToast(@StringRes int message, int duration) {
        Toast toastMessage = Toast.makeText(this, message, duration);
        toastMessage.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        items = new ItemList(service.getItems());
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        ManagerUIViewDatabaseFragment viewDbFragment =
                (ManagerUIViewDatabaseFragment) getSupportFragmentManager().findFragmentByTag("view_database");
        EditItemFragment editItemFragment =
                (EditItemFragment) getSupportFragmentManager().findFragmentByTag("edit_item");

        if (intentResult.getContents() != null) {
            scanResult = intentResult.getContents();

            if (viewDbFragment != null && viewDbFragment.isVisible()) {
                AddItemFragment addItemFragment = (AddItemFragment) viewDbFragment.getChildFragmentManager().findFragmentByTag("add_item");

                if (addItemFragment != null && addItemFragment.isVisible())
                    addItemFragment.editTextCode.setText(scanResult);
            }

            if (editItemFragment != null && editItemFragment.isVisible())
                editItemFragment.codeET.setText(scanResult);

        } else {
            Toast.makeText(getApplicationContext(), R.string.toastScanFailed, Toast.LENGTH_SHORT).show();
        }
    }
}