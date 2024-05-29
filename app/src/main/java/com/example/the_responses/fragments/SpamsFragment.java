package com.example.the_responses.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.the_responses.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpamsFragment extends Fragment {

    private Spinner spinnerContacts;
    private Button btnSendSpam;
    private List<String> contactsList;
    private SharedPreferences sharedPreferences;
    private static final String RESPONSES_KEY = "responses";
    private static final String CONTACTS_KEY = "contacts";

    public SpamsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spams, container, false);

        spinnerContacts = view.findViewById(R.id.spinnerContacts);
        btnSendSpam = view.findViewById(R.id.btnSendSpam);

        sharedPreferences = getActivity().getSharedPreferences("com.example.the_responses", Context.MODE_PRIVATE);

        // Charge les contacts depuis les préférences partagées et configure l'adaptateur du Spinner
        loadContactsFromPreferences();

        btnSendSpam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedContact = spinnerContacts.getSelectedItem().toString();
                String selectedResponse = getSelectedResponse();
                if (!selectedResponse.isEmpty()) {
                    sendSpam(selectedContact, selectedResponse);
                } else {
                    Toast.makeText(getContext(), "No response selected to send as spam", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadContactsFromPreferences() {
        Set<String> contactsSet = sharedPreferences.getStringSet(CONTACTS_KEY, new HashSet<>());
        contactsList = new ArrayList<>(contactsSet);

        // Configure l'adaptateur du Spinner avec les contacts chargés
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, contactsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContacts.setAdapter(adapter);
    }

    private String getSelectedResponse() {
        Set<String> responseSet = sharedPreferences.getStringSet(RESPONSES_KEY, null);
        if (responseSet != null) {
            for (String response : responseSet) {
                String[] parts = response.split("\\|");
                boolean isAutoResponse = Boolean.parseBoolean(parts[2]);
                if (isAutoResponse) {
                    return parts[0]; // Return the auto response text
                }
            }
        }
        return "";
    }

    private void sendSpam(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(getContext(), "Spam sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
}
