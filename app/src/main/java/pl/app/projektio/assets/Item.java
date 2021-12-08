package pl.app.projektio.assets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.app.projektio.R;

public class Item {
    private int id;
    private int roomId;
    private String status;
    private String code;
    private String category;
    private String buyDate;
    private String name;

    private Date buyDateDate;
    public ConstraintLayout display;

    public Item() {}

    public Item(int id, int roomId, String status, String code, String category, Date buyDate, String name) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        this.id = id;
        this.roomId = roomId;
        this.status = status;
        this.code = code;
        this.category = category;
        this.buyDateDate = buyDate;
        this.name = name;
        this.buyDate = dateFormat.format(this.buyDateDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public Date getBuyDateDate() {
        return buyDateDate;
    }

    public void setBuyDateDate(Date buyDateDate) {
        this.buyDateDate = buyDateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConstraintLayout getLayout(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        display = (ConstraintLayout) inflater.inflate(R.layout.view_item_display, null, false);

        TextView statusTV = (TextView) display.findViewById(R.id.textViewStatus);
        TextView codeTV = (TextView) display.findViewById(R.id.textViewCode);
        TextView categoryTV = (TextView) display.findViewById(R.id.textViewCategory);
        TextView nameTV = (TextView) display.findViewById(R.id.textViewName);

        statusTV.setText(Html.fromHtml(status, 0));
        codeTV.setText(Html.fromHtml(code, 0));
        categoryTV.setText(Html.fromHtml(category, 0));
        nameTV.setText(Html.fromHtml(name, 0));

        return display;
    }

    public ConstraintLayout getInventoryLayout(Context context, String roomName) {

        LayoutInflater inflater = LayoutInflater.from(context);
        display = (ConstraintLayout) inflater.inflate(R.layout.view_inventory_item_display, null, false);

        TextView codeTV = (TextView) display.findViewById(R.id.textViewIICode);
        TextView categoryTV = (TextView) display.findViewById(R.id.textViewIIRoom);
        TextView nameTV = (TextView) display.findViewById(R.id.textViewIIName);

        codeTV.setText(Html.fromHtml(code, 0));
        categoryTV.setText(Html.fromHtml(roomName, 0));
        nameTV.setText(Html.fromHtml(name, 0));

        return display;
    }
}
