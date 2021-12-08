package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;

public class DeleteFloorFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;
    private TextView textViewDeleteMsg;
    private Button deleteButton;
    private Button cancelButton;

    public DeleteFloorFragment() { super(R.layout.dialog_manager_ui_delete_floor); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        textViewDeleteMsg = getView().findViewById(R.id.textViewDeleteFloorFragment);
        deleteButton = getView().findViewById(R.id.buttonDeleteDeleteFloorFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelDeleteFloorFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        String textViewText = getString(R.string.managerUIDeleteMsg) +
                " " + parentActivity.floors.getChosenFloor().getName() + "?\n" + getString(R.string.managerUIDeleteWarning);
        textViewDeleteMsg.setText(textViewText);

        deleteButton.setOnClickListener(v -> {
            parentActivity.service.deleteFloor(parentActivity.floors.getChosenFloor());
            parentFragment.updateFloors();
            parentFragment.getChildFragmentManager().popBackStack();
            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });

        cancelButton.setOnClickListener(v -> {
            parentFragment.getChildFragmentManager().popBackStack();
            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });
    }
}
