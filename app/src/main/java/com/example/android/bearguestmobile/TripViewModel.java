package com.example.android.bearguestmobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TripViewModel extends ViewModel {

    private MutableLiveData<String> selectedTrip = new MutableLiveData<>();
    private MutableLiveData<String> tripPageHeader = new MutableLiveData<>();

    public void setSelectedTrip(String selectedTrip) {
        this.selectedTrip.setValue(selectedTrip);
    }

    public LiveData<String> getSelectedTrip() {
        return this.selectedTrip;
    }

    public void setTripPageHeader(String newHeader) {
        this.tripPageHeader.setValue(newHeader);
    }

    public LiveData<String> getTripPageHeader() { return this.tripPageHeader; }
}
