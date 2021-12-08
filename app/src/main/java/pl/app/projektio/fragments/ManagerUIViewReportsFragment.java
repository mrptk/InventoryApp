package pl.app.projektio.fragments;


import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.BuildingList;
import pl.app.projektio.assets.FloorList;
import pl.app.projektio.assets.Inventory;
import pl.app.projektio.assets.InventoryList;

public class ManagerUIViewReportsFragment extends Fragment {
    private DashboardActivity parentActivity;
    private LinearLayout linearLayout;
    private Button buttonGetReports;
    private FloorList floors;
    private BuildingList buildings;

    public ManagerUIViewReportsFragment() { super(R.layout.fragment_manager_ui_view_reports); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        linearLayout = getView().findViewById(R.id.linearLayoutScrollViewManagerUIViewReportsFragment);
        buttonGetReports = getView().findViewById(R.id.buttonGetReportsManagerUIViewReportsFragment);

        buttonGetReports.setOnClickListener(v -> {
            parentActivity.inventories = new InventoryList(parentActivity.service.getInventories());
            floors = new FloorList(parentActivity.service.getFloors(), -1);
            buildings = new BuildingList(parentActivity.service.getBuildings());
            linearLayout.removeAllViews();

            for (Inventory i: parentActivity.inventories.finishedInventories) {
                String iStartDate = i.getStartDate();
                String inventoryDescription = "Inwentaryzacja: \n" + floors.getFloorById(i.getFloorId()).getName()
                        + ", " + buildings.getBuildingById(floors.getFloorById(i.getFloorId()).getBuildingId()).getName() + "\n"
                        + iStartDate.substring(0,iStartDate.indexOf("T")) + "\n"
                        + iStartDate.substring(iStartDate.indexOf("T") + 1);

                linearLayout.addView(i.getReportsLayout(this.getContext(), inventoryDescription, c -> {
                    parentActivity.service.getReport(i);
                }));
            }
        });
    }
}
