package pl.app.projektio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import pl.app.projektio.assets.FloorList;
import pl.app.projektio.assets.InventoryList;
import pl.app.projektio.assets.Item;
import pl.app.projektio.assets.ItemList;
import pl.app.projektio.assets.RoomList;
import pl.app.projektio.dialogs.NewInventoryFragment;
import pl.app.projektio.fragments.InventoryUIViewInventories;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;

public class InventoryDashboardActivity extends AppCompatActivity {
    public ServerService service;
    public InventoryList inventories;
    public String scanResult;
    private Button buttonNewInventory;
    private Button buttonResumeInventory;
    private ItemList items;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ServerService.ServiceBinder binder = (ServerService.ServiceBinder) iBinder;
            service = ((ServerService.ServiceBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_inventory);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ServerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        buttonNewInventory = findViewById(R.id.buttonNewInventory);
        buttonNewInventory.setOnClickListener(v -> {
            if (getSupportFragmentManager().getFragments().size() > 0)
                getSupportFragmentManager().popBackStack();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerInventoryDashboardActivity, NewInventoryFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        buttonResumeInventory = findViewById(R.id.buttonResumeInventory);
        buttonResumeInventory.setOnClickListener(v -> {
            if (getSupportFragmentManager().getFragments().size() > 0)
                getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerInventoryDashboardActivity, InventoryUIViewInventories.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        items = new ItemList(service.getItems());
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult.getContents() != null) {
            scanResult = intentResult.getContents();
            Item scannedItem = items.getItemByCode(scanResult);
            if (scannedItem != null)
                service.createInventoryItem(0, inventories.activeInventory.getId(), scannedItem.getId());
            else
                Toast.makeText(getApplicationContext(), R.string.toastNoSuchItem, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), R.string.toastScanFailed, Toast.LENGTH_SHORT).show();
        }
    }
}