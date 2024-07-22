package com.example.debug.Interface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("https://enrol.lesterintheclouds.com/signup.php")
    Call<Void> uploadFiles(
            @Part("type") RequestBody type,
            @Part("lrn") RequestBody lrn,
            @Part("jhsAttended") RequestBody jhsAttended,
            @Part("shsAttended") RequestBody shsAttended,
            @Part("fname") RequestBody fname,
            @Part("lname") RequestBody lname,
            @Part("mname") RequestBody mname,
            @Part("sex") RequestBody sex,
            @Part("birthdate") RequestBody birthdate,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("province") RequestBody province,
            @Part("municipality") RequestBody municipality,
            @Part("barangay") RequestBody barangay,
            @Part("purok") RequestBody purok,
            @Part("parish") RequestBody parish,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part form137,
            @Part MultipartBody.Part jhsDiploma,
            @Part MultipartBody.Part escCertificate,
            @Part MultipartBody.Part shsDiploma,
            @Part MultipartBody.Part transcriptRecords,
            @Part MultipartBody.Part dismissalCertificate,
            @Part MultipartBody.Part baptismal,
            @Part MultipartBody.Part confirmationCertificate,
            @Part MultipartBody.Part birthCertificate,
            @Part MultipartBody.Part marriageCertificate,
            @Part MultipartBody.Part brgyResidence,
            @Part MultipartBody.Part indigency,
            @Part MultipartBody.Part bir,
            @Part MultipartBody.Part recommendationLetter,
            @Part MultipartBody.Part medicalCertificate
    );
}
