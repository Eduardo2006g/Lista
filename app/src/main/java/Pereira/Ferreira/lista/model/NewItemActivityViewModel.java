package Pereira.Ferreira.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {

    private Uri selectedPhotoLocation = null;

    public Uri getSelectedPhotoLocation() {
        return selectedPhotoLocation;
    }

    public void setSelectedPhotoLocation(Uri selectedPhotoLocation) {
        this.selectedPhotoLocation = selectedPhotoLocation;
    }

    public Uri getSelectPhotoLocation() {
        return selectedPhotoLocation;
    }
}
