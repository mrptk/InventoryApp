package pl.app.projektio.assets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.app.projektio.R;

public class Inventory {
    private int id;
    private int floorId;
    private String startDate;
    private int status; //do Inventory status 0 to utworzona inwentaryzacja a 1 to zakończona
    // w InventoryItem status 1 to info, że przedmiot jest na właściwym piętrze a 0 na złym
    public ConstraintLayout display;
    public FloatingActionButton actionButton;
    public TextView desc;

    public Inventory() {}

    public Inventory(int id, int floorId, Date startDate, int status) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

        this.id = id;
        this.floorId = floorId;
        this.startDate = dateFormat.format(startDate);
        this.status = status;

    }

    public ConstraintLayout getInventoryLayout(Context context, String descText, View.OnClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        display = (ConstraintLayout) inflater.inflate(R.layout.view_report_display_inventory, null, false);

        desc = display.findViewById(R.id.textViewInventoryResumeReports);
        actionButton =  display.findViewById(R.id.floatingActionButtonInventoryResumeReports);

        desc.setText(descText);
        actionButton.setOnClickListener(listener);

        return display;
    }

    public ConstraintLayout getReportsLayout(Context context, String descText, View.OnClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        display = (ConstraintLayout) inflater.inflate(R.layout.view_report_display_manager, null, false);

        desc = display.findViewById(R.id.textViewInventoryViewReports);
        actionButton =  display.findViewById(R.id.floatingActionButtonInventoryViewReports);

        desc.setText(descText);
        actionButton.setOnClickListener(listener);

        return display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
