package pl.app.projektio.fragments;

import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

import pl.app.projektio.InventoryDashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.ScannerActivity;
import pl.app.projektio.assets.Inventory;
import pl.app.projektio.assets.InventoryList;
import pl.app.projektio.assets.Item;
import pl.app.projektio.assets.RoomList;

public class InventoryUIInventory extends Fragment {
    private InventoryDashboardActivity parentActivity;
    private RoomList rooms;
    private ArrayList<Item> items;
    private Button scanButton;
    private Button finishButton;
    private Button pauseButton;
    private LinearLayout linearLayout;

    public InventoryUIInventory() { super(R.layout.fragment_inventory_ui_inventory); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (InventoryDashboardActivity) getActivity();
        scanButton = getView().findViewById(R.id.buttonScanInventoryInventoryFragment);
        finishButton = getView().findViewById(R.id.buttonFinishInventoryInventoryFragment);
        pauseButton = getView().findViewById(R.id.buttonPauseInventoryInventoryFragment);
        linearLayout = getView().findViewById(R.id.linearLayoutScrollViewInventoryUIInventory);
        rooms = new RoomList(parentActivity.service.getRooms(), -1);
        items = parentActivity.service.getInventoryItems(parentActivity.inventories.activeInventory);

        pauseButton.setOnClickListener(v -> parentActivity.getSupportFragmentManager().popBackStack());

        finishButton.setOnClickListener(v -> {
            Inventory inventoryToFinish = parentActivity.inventories.activeInventory;
            inventoryToFinish.setStatus(1);
            parentActivity.service.updateInventory(inventoryToFinish);
            parentActivity.inventories = new InventoryList(parentActivity.service.getInventories());
            parentActivity.getSupportFragmentManager().popBackStack();
        });

        scanButton.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(parentActivity);

            intentIntegrator.setPrompt("Użyj przycisku podgłaśniania, aby włączyć latarkę.");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(ScannerActivity.class);
            intentIntegrator.initiateScan();
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        linearLayout.removeAllViews();
        for (Item i: items) {
            linearLayout.addView(i.getInventoryLayout(this.getContext(), rooms.getRoomById(i.getRoomId()).getName()));
        }
    }
}
