package com.sebastian.helmet;

import android.app.Fragment;
import android.os.Bundle;

public class NavigationSectionFragment extends Fragment {

    protected static final String ARG_SECTION_NUMBER = "section_number";

    public static NavigationSectionFragment newInstance(int sectionNumber, NavigationSectionFragment fragment) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}