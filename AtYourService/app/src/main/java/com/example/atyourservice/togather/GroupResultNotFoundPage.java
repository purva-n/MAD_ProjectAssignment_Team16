package com.example.atyourservice.togather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atyourservice.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupResultNotFoundPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupResultNotFoundPage extends Fragment {

    public GroupResultNotFoundPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GroupResultNotFoundPage.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupResultNotFoundPage newInstance() {
        GroupResultNotFoundPage fragment = new GroupResultNotFoundPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_result_not_found_page, container, false);
    }
}