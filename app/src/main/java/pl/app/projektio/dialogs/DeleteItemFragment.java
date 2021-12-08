package pl.app.projektio.dialogs;

import android.widget.Button;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.fragments.ManagerUIViewDatabaseFragment;

public class DeleteItemFragment extends Fragment {
    private DashboardActivity parentActivity;
    private EditItemFragment parentFragment;

    private Button deleteButton;
    private Button cancelButton;

    public DeleteItemFragment() { super(R.layout.dialog_manager_ui_delete_item); }

    @Override
    public void onStart() {
        super.onStart();

        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (EditItemFragment) getParentFragment();

        deleteButton = getView().findViewById(R.id.buttonDeleteDeleteItemFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelDeleteItemFragment);

        deleteButton.setOnClickListener(v -> {
            parentActivity.service.deleteItem(parentActivity.items.getChosenItem());
            parentActivity.onBackPressed();
        });

        cancelButton.setOnClickListener(v -> parentFragment.getChildFragmentManager().popBackStack());
    }
}
