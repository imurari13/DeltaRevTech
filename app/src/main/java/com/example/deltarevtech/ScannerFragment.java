package com.example.deltarevtech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class ScannerFragment extends Fragment {

    private DecoratedBarcodeView barcodeView;
    private TextView tvResult;
    private String lastScannedData = "";

    // Handle Camera Permission Request
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    barcodeView.resume();
                } else {
                    Toast.makeText(getContext(), "Camera permission is required", Toast.LENGTH_LONG).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

        barcodeView = view.findViewById(R.id.barcode_scanner);
        tvResult = view.findViewById(R.id.tv_result);

        // Continuous scanning engine
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() == null || result.getText().equals(lastScannedData)) {
                    return;
                }

                lastScannedData = result.getText();
                String scannedData = result.getText();

                // Null-safe UI update
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        tvResult.setText(String.format("Scanned: %s", scannedData));

                        if (scannedData.startsWith("http://") || scannedData.startsWith("https://")) {
                            barcodeView.pause(); // Stop camera before opening browser
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedData));
                            startActivity(browserIntent);
                        }
                    });
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // Leaving this empty clears the warning
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lastScannedData = "";

        // Always check permission on resume to ensure the camera starts
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            barcodeView.resume();
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause(); // Crucial for battery and camera resource health
    }
}