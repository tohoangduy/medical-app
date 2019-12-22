package com.android.hoangduy.medical.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.android.hoangduy.medical.R;
import com.android.hoangduy.medical.activity.CaptureActivity;
import com.android.hoangduy.medical.base.BaseFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils extends BaseFragment {
    // Constants
    private static final String TAG = CameraUtils.class.getSimpleName();
    public static final int CAMERA_REQUEST = 1888;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // Properties
    private static final String className = CameraUtils.class.getSimpleName();
    protected File photoFile = null;

//    public CameraUtils(Activity activity) {
//        this.activity = activity;
//        className = getClass().getSimpleName();
//    }

    public CameraUtils() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.empty_layout, container, false);
        setShowToolBar(false);

        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            capture();
        } else {
            requestCameraPermission();
        }
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        Toast.makeText(getContext(), R.string.permission_camera_rationale, Toast.LENGTH_LONG).show();
    }

    public void capture() {
        try {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (captureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                photoFile = createImageFile();

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri uri = getFileUri(photoFile);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    getActivity().startActivityForResult(captureIntent, CAMERA_REQUEST);
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }

    public String encodeBytes(byte[] byteArray) {
        return (byteArray == null) ? null : Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public String encodeImageBitmap(Bitmap bm) {
        try {
            if (bm != null) {
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return "";
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
            return "";
        }
    }

    private static final int REQUEST_WRITE_EXTERNAL_CODE = 101;

    private File createImageFile() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Create an image file name
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File image = File.createTempFile(
                            imageFileName,  /* prefix */
                            ".jpg",   /* suffix */
                            storageDir      /* directory */
                    );

                    return image;
                } else {
                    requestWriteExternalStoragePermission();
                }
            }
            return null;
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }

        return null;
    }

    private void requestWriteExternalStoragePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, REQUEST_WRITE_EXTERNAL_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_WRITE_EXTERNAL_CODE) {//check if same request code
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//only one permission, check if granted
                createImageFile();
            } else {
                if (shouldShowRequestPermissionRationale(permissions[0])) {//display error dialog explaining why the permission is needed
                    new MaterialAlertDialogBuilder(getContext())
                            .setMessage(getString(R.string.error_msg_permission_write_external))
                            .setPositiveButton(getString(R.string.allow), (dialog, which) -> requestWriteExternalStoragePermission())
                            .setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {
                                getActivity().finish();
                                dialog.dismiss();
                            }))
                            .show();
                } else {
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle(getString(R.string.error))
                            .setMessage(getString(R.string.you_need_to_grant_permission))
                            .setPositiveButton(getString(R.string.grant), (dialog, which) -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                            })
                            .setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {
                                getActivity().finish();
                                dialog.dismiss();
                            }))
                            .show();
                }
            }
        }
    }

    /**
     * return String with format for can use with ACTION_VIEW intents
     */
    public Uri getFileUri(File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String packageName = getActivity().getApplicationContext().getPackageName();
            return FileProvider.getUriForFile(getActivity(), packageName, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public void deletePhoto() {
        try {
            if (photoFile != null && photoFile.exists()) {
                if (photoFile.delete()) {
                    System.out.println("file Deleted :" + photoFile.getAbsolutePath());
                } else {
                    System.out.println("file not Deleted :" + photoFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CameraUtils.CAMERA_REQUEST) {
                try {
                    performCrop();
                } catch (Exception e) {
                    Log.e("Error: ", e.toString());
                }

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                try {
                    Uri imageCroppedUri = result.getUri();
                    Bitmap bitmapPicture = BitmapFactory.decodeStream(
                            getContext()
                                    .getContentResolver()
                                    .openInputStream(imageCroppedUri)
                    );
                    deletePhoto();

                    int quality = 20;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmapPicture.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
                    byte[] bitmapdata = byteArrayOutputStream.toByteArray();
                    bitmapPicture.recycle();
                    Intent intentResult = new Intent();
                    intentResult.putExtra(CaptureActivity.class.getSimpleName(), bitmapdata);
                    getActivity().finishActivity(CaptureActivity.REQUEST_CODE);
                    getActivity().finish();
                } catch (Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Exception error = result.getError();
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void performCrop() {
        try {
            Uri uri = getFileUri(photoFile);
            Intent intent = CropImage.activity(uri).getIntent(getActivity());
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}
