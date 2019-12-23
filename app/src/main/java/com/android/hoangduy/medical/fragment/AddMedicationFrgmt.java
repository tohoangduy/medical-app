package com.android.hoangduy.medical.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.activity.CaptureActivity;
import com.android.hoangduy.medical.activity.OcrCaptureActivity;
import com.android.hoangduy.medical.adapter.MedicationAdapter;
import com.android.hoangduy.medical.base.BaseFragment;
import com.android.hoangduy.medical.listeners.ICallback;
import com.android.hoangduy.medical.model.dto.MedicationDTO;
import com.android.hoangduy.medical.utils.DateUtil;
import com.android.hoangduy.medical.utils.PermissionUtil;
import com.android.hoangduy.medical.views.UIHelper;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddMedicationFrgmt extends BaseFragment
        implements View.OnClickListener, ICallback, ActivityCompat.RequestPermissionsRequestCodeValidator {

    private static final int RC_OCR_CAPTURE = 9003;

    private TextInputEditText edDateVisited;
    private SelectDateFragment mCalendar;
    private RecyclerView rcMedication;
    private List<MedicationDTO> datasetMedicationDto = new ArrayList<>();
    private MedicationAdapter medicationAdapter;
    private FloatingActionButton btnAddMedication;

    public static AddMedicationFrgmt newInstance() {
        return new AddMedicationFrgmt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.frgmt_add_medication, container, false);
        setTitle(getString(R.string.add_prescribed_medication));

        // get references
        btnAddMedication = contentView.findViewById(R.id.btnAddMedication);
        edDateVisited = contentView.findViewById(R.id.edDateVisited);
        rcMedication = contentView.findViewById(R.id.rcMedication);

        // set listeners
        btnAddMedication.setOnClickListener(this);
        edDateVisited.setOnClickListener(this);

        init();

        return contentView;
    }

    private void init() {
        mCalendar = new SelectDateFragment()
                .setOnSelectedDate(date -> edDateVisited.setText(date))
                .setMaxDate(new Date().getTime());

        medicationAdapter = new MedicationAdapter(getActivity(), datasetMedicationDto, this);
        medicationAdapter.setCallBack(this);
        rcMedication.setAdapter(medicationAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMedication:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!PermissionUtil.isPermissionGranted(getContext(), Manifest.permission.CAMERA)) {
                        PermissionUtil.requestPermission(getActivity(), new String[]{Manifest.permission.CAMERA});
                        break;
                    }
                }

                openCameraIntent();
                btnAddMedication.setVisibility(View.GONE);
                break;

            case R.id.edDateVisited:
                mCalendar
                        .setMaxDate(new Date().getTime())
                        .show(
                                getActivity().getSupportFragmentManager(),
                                DateUtil.getTodayString(),
                                "dpStart"
                        );
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    openCameraIntent();
                    btnAddMedication.setVisibility(View.GONE);
                } else {
                    // Permission Denied
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (PermissionUtil.isPermissionGranted(getContext(), Manifest.permission.CAMERA)) {
                            UIHelper.showMessageOKCancel(
                                    getContext(),
                                    getString(R.string.you_need_to_allow_access_permission),
                                    (DialogInterface dialog, int which) -> {
                                        PermissionUtil.requestPermission(getActivity(), new String[]{Manifest.permission.CAMERA});
                                    }
                            );
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void validateRequestPermissionsRequestCode(int requestCode) {
        if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE) {
            openCameraIntent();
            btnAddMedication.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String textDetected = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    Log.d(getTag(), "Text read: " + textDetected);

                    String[] drugNameArr = textDetected.split("\n");
                    boolean[] choicesInitial = new boolean[drugNameArr.length];
                    Arrays.fill(choicesInitial, false);

                    // show multi select list for user choose medication name
                    new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()))
                            .setTitle(getString(R.string.which_medication_name_of_you_bellow))
                            .setPositiveButton(getString(R.string.select), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String drugName = "";
                                    for (int i = 0; i < choicesInitial.length; i++) {
                                        if (choicesInitial[i]) {
                                            drugName = drugName.concat(" " + drugNameArr[i].trim());
                                        }
                                    }
                                    showAddMedicationForm(drugName);
                                }
                            })
                            .setNeutralButton(getString(R.string.cancel), null)
                            .setMultiChoiceItems(drugNameArr, choicesInitial, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    choicesInitial[which] = isChecked;
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(getContext(), R.string.ocr_failure, Toast.LENGTH_LONG).show();
                    Log.d(getTag(), "No Text captured, intent data is null");
                }
            } else {
                Toast.makeText(
                        getContext(),
                        String.format(
                                getString(R.string.ocr_error),
                                CommonStatusCodes.getStatusCodeString(resultCode)
                        ),
                        Toast.LENGTH_LONG
                )
                        .show();
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) { // take picture frm MedicationAdapter
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                medicationAdapter.onCropImageResult(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                UIHelper.showMessageOK(getContext(), getString(R.string.error), error.getMessage(), null);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showAddMedicationForm(String drugName) {
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.name = drugName;
        medicationDTO.isBeforeMeal = false;
        medicationDTO.quantity = 1;
        medicationDTO.takeTimeList.add("_ _:_ _");
        datasetMedicationDto.add(medicationDTO);
        medicationAdapter.notifyDataSetChanged();
    }

    private void openCameraIntent() {
        // launch Ocr capture activity.
        Intent intent = new Intent(getContext(), OcrCaptureActivity.class);
        intent.putExtra(OcrCaptureActivity.AutoFocus, true);

        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @Override
    public void onSaved() {
        btnAddMedication.setVisibility(View.VISIBLE);
    }

    public interface CameraIntent {
        void openCamera();
    }
}
