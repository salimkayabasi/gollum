package com.github.celepharn.gollum.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.celepharn.gollum.example.model.Student;
import com.github.celepharn.gollum.ui.fragment.LFragment;
import com.github.celepharn.gollum.ui.util.LDialogUtil;
import com.github.celepharn.gollum.ui.widgets.LSpinner;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class MainActivityFragment extends LFragment {

    @Bind(R.id.lspinner)
    protected LSpinner<Student> lSpinner;
    private ArrayList<Student> studentList;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setUpViews() {
        studentList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            studentList.add(new Student(i, "Murat"));
        }
        lSpinner.setType(LSpinner.SpinnerType.MULTI);
        lSpinner.setItems(studentList);
    }

    @OnItemSelected(R.id.lspinner)
    void onStudentSelected() {
        if (lSpinner.getSelectedItems().size() > 0)
            LDialogUtil.showMessage(getActivity(), lSpinner.getSelectedItems().toString());
    }


    @Override
    protected void onRestore() {
        setUpViews();
    }

    @Override
    protected void init() {
        setUpViews();
    }

    @Override
    protected void onSaveState(Bundle outState) {

    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {

    }
}