package com.ouledahmed.simplynote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedNotesReadViewModel extends ViewModel {

    // the type of notes requested
    final private MutableLiveData<Integer> requested = new MutableLiveData<Integer>();



    void setRequested(int value){
        this.requested.setValue(value);
    }

    public LiveData<Integer> getRequested(){
        return requested;
    }

}
