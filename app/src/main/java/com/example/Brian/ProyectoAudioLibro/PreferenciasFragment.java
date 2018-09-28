package com.example.Brian.ProyectoAudioLibro;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class PreferenciasFragment extends PreferenceFragmentCompat {

    @Override public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

}
