package com.example.debug.model;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {
    // TYPE
    private final MutableLiveData<String> type = new MutableLiveData<>();

    // SCHOLASTIC
    private final MutableLiveData<String> lrn = new MutableLiveData<>();
    private final MutableLiveData<Uri> form137 = new MutableLiveData<>();

    // SHS
    private final MutableLiveData<String> jhsAttended = new MutableLiveData<>();
    private final MutableLiveData<Uri> jhsDiploma = new MutableLiveData<>();
    private final MutableLiveData<Uri> escCertificate = new MutableLiveData<>();

    // COLLEGE
    private final MutableLiveData<String> shsAttended = new MutableLiveData<>();
    private final MutableLiveData<Uri> shsDiploma = new MutableLiveData<>();
    private final MutableLiveData<Uri> transcriptRecords = new MutableLiveData<>();
    private final MutableLiveData<Uri> dismissalCertificate = new MutableLiveData<>();

    // PERSONAL INFO
    private final MutableLiveData<String> fname = new MutableLiveData<>();
    private final MutableLiveData<String> lname = new MutableLiveData<>();
    private final MutableLiveData<String> mname = new MutableLiveData<>();
    private final MutableLiveData<String> sex = new MutableLiveData<>();
    private final MutableLiveData<String> birthdate = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();

    // ADDRESS
    private final MutableLiveData<String> province = new MutableLiveData<>();
    private final MutableLiveData<String> municipality = new MutableLiveData<>();
    private final MutableLiveData<String> barangay = new MutableLiveData<>();
    private final MutableLiveData<String> purok = new MutableLiveData<>();

    // DOCUMENTS
    // CANONICAL
    private final MutableLiveData<Uri> baptismal = new MutableLiveData<>();
    private final MutableLiveData<Uri> confirmationCertificate = new MutableLiveData<>();

    // CIVIL
    private final MutableLiveData<Uri> birthCertificate = new MutableLiveData<>();
    private final MutableLiveData<Uri> marriageCertificate = new MutableLiveData<>();
    private final MutableLiveData<Uri> brgyResidence = new MutableLiveData<>();
    private final MutableLiveData<Uri> indigency = new MutableLiveData<>();
    private final MutableLiveData<Uri> bir = new MutableLiveData<>();

    // OTHER DOCUMENTS
    private final MutableLiveData<Uri> recommendationLetter = new MutableLiveData<>();
    private final MutableLiveData<String> parish = new MutableLiveData<>();
    private final MutableLiveData<Uri> medicalCertificate = new MutableLiveData<>();

    // ACCOUNT
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();

    // TYPE GETTER
    public MutableLiveData<String> getType() { return type; }
    public void setType(String type) { this.type.setValue(type); }

    // SCHOLASTIC GETTERS
    public MutableLiveData<String> getLrn() { return lrn; }
    public void setLrn(String lrn) { this.lrn.setValue(lrn); }
    public MutableLiveData<Uri> getForm137() { return form137; }
    public void setForm137(Uri form137) { this.form137.setValue(form137); }

    // SHS GETTERS
    public MutableLiveData<String> getJhsAttended() { return jhsAttended; }
    public void setJhsAttended(String jhsAttended) { this.jhsAttended.setValue(jhsAttended); }
    public MutableLiveData<Uri> getJhsDiploma() { return jhsDiploma; }
    public void setJhsDiploma(Uri jhsDiploma) { this.jhsDiploma.setValue(jhsDiploma); }
    public MutableLiveData<Uri> getEscCertificate() { return escCertificate; }
    public void setEscCertificate(Uri escCertificate) { this.escCertificate.setValue(escCertificate); }

    // COLLEGE GETTERS
    public MutableLiveData<String> getShsAttended() { return shsAttended; }
    public void setShsAttended(String shsAttended) { this.shsAttended.setValue(shsAttended); }
    public MutableLiveData<Uri> getShsDiploma() { return shsDiploma; }
    public void setShsDiploma(Uri shsDiploma) { this.shsDiploma.setValue(shsDiploma); }
    public MutableLiveData<Uri> getTranscriptRecords() { return transcriptRecords; }
    public void setTranscriptRecords(Uri transcriptRecords) { this.transcriptRecords.setValue(transcriptRecords); }
    public MutableLiveData<Uri> getDismissalCertificate() { return dismissalCertificate; }
    public void setDismissalCertificate(Uri dismissalCertificate) { this.dismissalCertificate.setValue(dismissalCertificate); }

    // PERSONAL GETTERS
    public MutableLiveData<String> getFname() { return fname; }
    public void setFname(String fname) { this.fname.setValue(fname); }
    public MutableLiveData<String> getLname() { return lname; }
    public void setLname(String lname) { this.lname.setValue(lname); }
    public MutableLiveData<String> getMname() { return mname; }
    public void setMname(String mname) { this.mname.setValue(mname); }
    public MutableLiveData<String> getSex() { return sex; }
    public void setSex(String sex) { this.sex.setValue(sex); }
    public MutableLiveData<String> getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate.setValue(birthdate); }
    public MutableLiveData<String> getEmail() { return email; }
    public void setEmail(String email) { this.email.setValue(email); }
    public MutableLiveData<String> getPhone() { return phone; }
    public void setPhone(String phone) { this.phone.setValue(phone); }

    // ADDRESS GETTERS
    public MutableLiveData<String> getProvince() { return province; }
    public void setProvince(String province) { this.province.setValue(province); }
    public MutableLiveData<String> getMunicipality() { return municipality; }
    public void setMunicipality(String municipality) { this.municipality.setValue(municipality); }
    public MutableLiveData<String> getBarangay() { return barangay; }
    public void setBarangay(String barangay) { this.barangay.setValue(barangay); }
    public MutableLiveData<String> getPurok() { return purok; }
    public void setPurok(String purok) { this.purok.setValue(purok); }

    // CANONICAL GETTERS
    public MutableLiveData<Uri> getBaptismal() { return baptismal; }
    public void setBaptismal(Uri baptismal) { this.baptismal.setValue(baptismal); }
    public MutableLiveData<Uri> getConfirmationCertificate() { return confirmationCertificate; }
    public void setConfirmationCertificate(Uri confirmationCertificate) { this.confirmationCertificate.setValue(confirmationCertificate); }

    // CIVIL GETTERS
    public MutableLiveData<Uri> getBirthCertificate() { return birthCertificate; }
    public void setBirthCertificate(Uri birthCertificate) { this.birthCertificate.setValue(birthCertificate); }
    public MutableLiveData<Uri> getMarriageCertificate() { return marriageCertificate; }
    public void setMarriageCertificate(Uri marriageCertificate) { this.marriageCertificate.setValue(marriageCertificate); }
    public MutableLiveData<Uri> getBrgyResidence() { return brgyResidence; }
    public void setBrgyResidence(Uri brgyResidence) { this.brgyResidence.setValue(brgyResidence); }
    public MutableLiveData<Uri> getIndigency() { return indigency; }
    public void setIndigency(Uri indigency) { this.indigency.setValue(indigency); }
    public MutableLiveData<Uri> getBir() { return bir; }
    public void setBir(Uri bir) { this.bir.setValue(bir); }

    // OTHER DOCUMENTS GETTERS
    public MutableLiveData<Uri> getRecommendationLetter() { return recommendationLetter; }
    public void setRecommendationLetter(Uri recommendationLetter) { this.recommendationLetter.setValue(recommendationLetter); }
    public MutableLiveData<String> getParish() { return parish; }
    public void setParish(String parish) { this.parish.setValue(parish); }
    public MutableLiveData<Uri> getMedicalCertificate() { return medicalCertificate; }
    public void setMedicalCertificate(Uri medicalCertificate) { this.medicalCertificate.setValue(medicalCertificate); }

    // ACCOUNT GETTERS
    public MutableLiveData<String> getUsername() { return username; }
    public void setUsername(String username) { this.username.setValue(username); }
    public MutableLiveData<String> getPassword() { return password; }
    public void setPassword(String password) { this.password.setValue(password); }
}

