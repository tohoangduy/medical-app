package com.android.hoangduy.medical.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class PermissionUtil {
    public static final int PERMISSION_REQUEST_CODE = 200;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isPermissionGranted(@NonNull Context context, @NonNull String permission) {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermission(final @NonNull Activity activity, final  @NonNull String[] permissions) {
        activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE);
    }
}
