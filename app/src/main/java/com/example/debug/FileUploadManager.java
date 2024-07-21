package com.example.debug;

import android.content.Context;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.debug.Models.SignUpViewModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploadManager {
    private static final String TAG = "FileUploadManager";
    private final ApiService apiService;
    private final Context context;

    public FileUploadManager(String baseUrl, Context context) {
        this.apiService = RetrofitClient.getClient(baseUrl).create(ApiService.class);
        this.context = context;
    }

    public void uploadData(SignUpViewModel viewModel, @NonNull final UploadCallback callback) {
        // Retrieve data from ViewModel
        Map<String, RequestBody> stringParts = createStringParts(viewModel);
        Map<String, MultipartBody.Part> fileParts = createFileParts(viewModel);

        // Prepare the call with only non-null parts
        Call<Void> call = apiService.uploadFiles(
                stringParts.get("type"),
                stringParts.get("lrn"),
                stringParts.get("jhsAttended"),
                stringParts.get("shsAttended"),
                stringParts.get("fname"),
                stringParts.get("lname"),
                stringParts.get("mname"),
                stringParts.get("sex"),
                stringParts.get("birthdate"),
                stringParts.get("email"),
                stringParts.get("phone"),
                stringParts.get("province"),
                stringParts.get("municipality"),
                stringParts.get("barangay"),
                stringParts.get("purok"),
                stringParts.get("parish"),
                stringParts.get("username"),
                stringParts.get("password"),
                fileParts.get("form137"),
                fileParts.get("jhsDiploma"),
                fileParts.get("escCertificate"),
                fileParts.get("shsDiploma"),
                fileParts.get("transcriptRecords"),
                fileParts.get("dismissalCertificate"),
                fileParts.get("baptismal"),
                fileParts.get("confirmationCertificate"),
                fileParts.get("birthCertificate"),
                fileParts.get("marriageCertificate"),
                fileParts.get("brgyResidence"),
                fileParts.get("indigency"),
                fileParts.get("bir"),
                fileParts.get("recommendationLetter"),
                fileParts.get("medicalCertificate")
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Upload successful");
                    callback.onSuccess();
                } else {
                    Log.e(TAG, "Upload failed. Response code: " + response.code());
                    callback.onFailure("Upload failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Upload failed: " + t.getMessage(), t);
                callback.onFailure("Upload failed: " + t.getMessage());
            }
        });
    }

    private Map<String, RequestBody> createStringParts(SignUpViewModel viewModel) {
        Map<String, RequestBody> stringParts = new HashMap<>();
        addNonNullPart(stringParts, "type", viewModel.getType().getValue());
        addNonNullPart(stringParts, "lrn", viewModel.getLrn().getValue());
        addNonNullPart(stringParts, "jhsAttended", viewModel.getJhsAttended().getValue());
        addNonNullPart(stringParts, "shsAttended", viewModel.getShsAttended().getValue());
        addNonNullPart(stringParts, "fname", viewModel.getFname().getValue());
        addNonNullPart(stringParts, "lname", viewModel.getLname().getValue());
        addNonNullPart(stringParts, "mname", viewModel.getMname().getValue());
        addNonNullPart(stringParts, "sex", viewModel.getSex().getValue());
        addNonNullPart(stringParts, "birthdate", viewModel.getBirthdate().getValue());
        addNonNullPart(stringParts, "email", viewModel.getEmail().getValue());
        addNonNullPart(stringParts, "phone", viewModel.getPhone().getValue());
        addNonNullPart(stringParts, "province", viewModel.getProvince().getValue());
        addNonNullPart(stringParts, "municipality", viewModel.getMunicipality().getValue());
        addNonNullPart(stringParts, "barangay", viewModel.getBarangay().getValue());
        addNonNullPart(stringParts, "purok", viewModel.getPurok().getValue());
        addNonNullPart(stringParts, "parish", viewModel.getParish().getValue());
        addNonNullPart(stringParts, "username", viewModel.getUsername().getValue());
        addNonNullPart(stringParts, "password", viewModel.getPassword().getValue());
        return stringParts;
    }

    private Map<String, MultipartBody.Part> createFileParts(SignUpViewModel viewModel) {
        Map<String, MultipartBody.Part> fileParts = new HashMap<>();
        addNonNullFilePart(fileParts, "form137", viewModel.getForm137().getValue());
        addNonNullFilePart(fileParts, "jhsDiploma", viewModel.getJhsDiploma().getValue());
        addNonNullFilePart(fileParts, "escCertificate", viewModel.getEscCertificate().getValue());
        addNonNullFilePart(fileParts, "shsDiploma", viewModel.getShsDiploma().getValue());
        addNonNullFilePart(fileParts, "transcriptRecords", viewModel.getTranscriptRecords().getValue());
        addNonNullFilePart(fileParts, "dismissalCertificate", viewModel.getDismissalCertificate().getValue());
        addNonNullFilePart(fileParts, "baptismal", viewModel.getBaptismal().getValue());
        addNonNullFilePart(fileParts, "confirmationCertificate", viewModel.getConfirmationCertificate().getValue());
        addNonNullFilePart(fileParts, "birthCertificate", viewModel.getBirthCertificate().getValue());
        addNonNullFilePart(fileParts, "marriageCertificate", viewModel.getMarriageCertificate().getValue());
        addNonNullFilePart(fileParts, "brgyResidence", viewModel.getBrgyResidence().getValue());
        addNonNullFilePart(fileParts, "indigency", viewModel.getIndigency().getValue());
        addNonNullFilePart(fileParts, "bir", viewModel.getBir().getValue());
        addNonNullFilePart(fileParts, "recommendationLetter", viewModel.getRecommendationLetter().getValue());
        addNonNullFilePart(fileParts, "medicalCertificate", viewModel.getMedicalCertificate().getValue());
        return fileParts;
    }

    private void addNonNullPart(Map<String, RequestBody> parts, String key, String value) {
        if (value != null) {
            parts.put(key, createPartFromString(value));
        }
    }

    private void addNonNullFilePart(Map<String, MultipartBody.Part> parts, String key, Uri fileUri) {
        MultipartBody.Part part = prepareFilePart(key, fileUri);
        if (part != null) {
            parts.put(key, part);
        }
    }

    private RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value != null ? value : "");
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        if (fileUri == null) {
            Log.e(TAG, "File URI for part " + partName + " is null");
            return null;
        }

        String filePath = getPath(fileUri);
        if (filePath == null) {
            Log.e(TAG, "File does not exist: " + fileUri.getPath());
            return null;
        }

        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private String getPath(Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return context.getExternalFilesDir(null) + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                return getDataColumn(contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public interface UploadCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
