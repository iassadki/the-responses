package com.example.the_responses.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_responses.R;
import com.example.the_responses.ResponseAdapter;
import com.example.the_responses.ResponseItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessagesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ResponseAdapter responseAdapter;
    private List<ResponseItem> responseList;
    private SharedPreferences sharedPreferences;
    private static final String RESPONSES_KEY = "responses";

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        sharedPreferences = getActivity().getSharedPreferences("com.example.the_responses", Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recyclerViewResponses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        responseList = loadResponses();
        responseAdapter = new ResponseAdapter(getContext(), responseList);
        recyclerView.setAdapter(responseAdapter);

        EditText etNewResponse = view.findViewById(R.id.etNewResponse);
        Button btnAddResponse = view.findViewById(R.id.btnAddResponse);

        btnAddResponse.setOnClickListener(v -> {
            String newResponse = etNewResponse.getText().toString().trim();
            if (!TextUtils.isEmpty(newResponse)) {
                ResponseItem responseItem = new ResponseItem(newResponse, false, false);
                responseList.add(responseItem);
                responseAdapter.notifyDataSetChanged();
                etNewResponse.setText("");
                saveResponses();
            } else {
                Toast.makeText(getContext(), "Please enter a response", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private List<ResponseItem> loadResponses() {
        Set<String> responseSet = sharedPreferences.getStringSet(RESPONSES_KEY, new HashSet<>());
        List<ResponseItem> responses = new ArrayList<>();
        for (String response : responseSet) {
            String[] parts = response.split("\\|");
            responses.add(new ResponseItem(parts[0], Boolean.parseBoolean(parts[1]), Boolean.parseBoolean(parts[2])));
        }
        return responses;
    }

    private void saveResponses() {
        Set<String> responseSet = new HashSet<>();
        for (ResponseItem item : responseList) {
            responseSet.add(item.getResponseText() + "|" + item.isSpam() + "|" + item.isAutoResponse());
        }
        sharedPreferences.edit().putStringSet(RESPONSES_KEY, responseSet).apply();
    }
}
