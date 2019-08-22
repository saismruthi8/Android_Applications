package com.example.inclass08_801073413;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditExpenseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText et_name, et_amount;
    Spinner sp_category;
    Button btn_save, btn_cancel;
    String[] categoryArray;
    String category;
    String cat;
    //String category = null;
    int categorypos;


    public EditExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_expense, container, false);
        et_name = view.findViewById(R.id.et_name);
        et_amount = view.findViewById(R.id.et_amount);
        sp_category = view.findViewById(R.id.sp_category);
        btn_save = view.findViewById(R.id.btn_save);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        categoryArray = new String[]{"Groceries", "Invoice", "Transportation", "Shopping", "Rent", "Trips", "Utilities", "Other"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categoryArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(dataAdapter);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ExpenseData exp=(ExpenseData) bundle.getSerializable("showexpensedetail");
            categorypos=(int)bundle.getSerializable("category");
            cat=exp.category;
            Log.d("obtained category",""+cat);
            Log.d("sai","edit Expense Objs "+exp);
            Log.d("insidebundle categry",""+categorypos);
            editexpenseDataResult(exp);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().length()!=0 && et_amount.getText().length()!=0) {
                    category= sp_category.getSelectedItem().toString();


                    mListener.onsaveclick(et_name.getText().toString(),""+et_amount.getText().toString(), category, sp_category.getSelectedItemPosition());
                    // Log.d("expenseDataArrayList_addfragment",""+et_name.getText().toString()+ et_amount.getText().toString()+ category);
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.expense_error), Toast.LENGTH_SHORT).show();
                }

            }


        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelClickfromedit();
            }
        });
       

    return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

// getting the previously selected category on spinner
    void editexpenseDataResult(ExpenseData exp){
        et_name.setText(exp.name);
        et_amount.setText(exp.amount);
        int pos = 0;
        //spinner
        for(int i=0;i<categoryArray.length;i++){
            if(cat.equals(categoryArray[i])){
                pos = i;
                break;
            }
        }
        sp_category.setSelection(pos);

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
        void onCancelClickfromedit();
        void onsaveclick(String name, String amount,String category,int pos);
    }
}
