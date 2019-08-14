package com.example.gbloodbank12;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BloodRequirementList extends ArrayAdapter<BloodRequirement> {

    private Activity context;
    private List<BloodRequirement> requirementList;

    public BloodRequirementList(Activity context,List<BloodRequirement> requirementList){
        super(context,R.layout.requirement_list_layout,requirementList);
        this.context = context;
        this.requirementList = requirementList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItems = inflater.inflate(R.layout.requirement_list_layout,null,true);

        TextView textViewName = listViewItems.findViewById(R.id.rtextViewName);
        TextView textViewBlood = listViewItems.findViewById(R.id.rtextViewBlood);
        TextView textViewContactNo = listViewItems.findViewById(R.id.rtextViewContactNo);
        TextView textViewHName = listViewItems.findViewById(R.id.rtextViewHName);
        TextView textViewHContactNo = listViewItems.findViewById(R.id.rtextViewHContactNo);


        BloodRequirement requirement = requirementList.get(position);

        textViewName.setText("Patient_Name: " + requirement.getClientName());
        textViewBlood.setText("BloodGroup: " + requirement.getBloodGroup());
        textViewContactNo.setText("P_ContactNo.: "+ requirement.getC_clientNo());
        textViewHName.setText("Hospital_Name: " + requirement.getHospitalName());
        textViewHContactNo.setText("H_ContactNo.: "+ requirement.getH_contactNo());

        return listViewItems;
    }
}
