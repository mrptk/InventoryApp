package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Date;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.ScannerActivity;
import pl.app.projektio.assets.Item;
import pl.app.projektio.fragments.ManagerUIViewDatabaseFragment;

public class AddItemFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUIViewDatabaseFragment parentFragment;
    private EditText editTextStatus;
    public EditText editTextCode;
    private EditText editTextCategory;
    private EditText editTextName;
    private Button scanCodeButton;
    // private Button addItemButton; WAZNE -> FUNKCJE DODAWANIA OBSLUGUJE PRZYCISK Z FRAGMENTU NADRZEDNEGO
    private Button cancelButton;

    public AddItemFragment() { super(R.layout.dialog_manager_ui_add_item); }

    public Item scrapItem() {
        Item newItem = new Item(parentActivity.items.getNextId(), parentActivity.rooms.getChosenRoom().getId(),
                editTextStatus.getText().toString(), editTextCode.getText().toString(), editTextCategory.getText().toString(),
                new Date(), editTextName.getText().toString());

        return newItem;
    }

    @Override
    public void onStart() {
        super.onStart();

        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUIViewDatabaseFragment) getParentFragment();
        editTextStatus = getView().findViewById(R.id.editTextStatusAddItemFragment);
        editTextCode = getView().findViewById(R.id.editTextCodeAddItemFragment);
        editTextCategory = getView().findViewById(R.id.editTextCategoryAddItemFragment);
        editTextName = getView().findViewById(R.id.editTextNameAddItemFragment);
        scanCodeButton = getView().findViewById(R.id.buttonScanAddItemFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelAddItemFragment);

        scanCodeButton.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(parentActivity);

            intentIntegrator.setPrompt("Użyj przycisku podgłaśniania, aby włączyć latarkę.");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(ScannerActivity.class);
            intentIntegrator.initiateScan();
        });
        cancelButton.setOnClickListener(v -> parentFragment.getChildFragmentManager().popBackStack());
    }
}
