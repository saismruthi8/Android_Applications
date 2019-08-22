package com.example.inclass08_801073413;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ExpenseAppFragment.OnFragmentInteractionListener , AddExpenseFragment.OnFragmentInteractionListener, ShowExpenseFragment.OnFragmentInteractionListener,EditExpenseFragment.OnFragmentInteractionListener {

    Context context;
    LinearLayout linlyt_container;
    ArrayList<ExpenseData> expenseDataArrayList = new ArrayList<>();
    ArrayList<ExpenseData> expenseslist=new ArrayList<>();
    FirebaseDatabase database;
    //=FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    //=database.getReference("expenses");
    int categorypos;
    int listviewpos;
    ExpenseData e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linlyt_container = findViewById(R.id.linlyt_container);
        setTitle("Expense App");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.linlyt_container,new ExpenseAppFragment(),"ExpenseAppFragment")
                .commit();
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("expenses");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseslist = new ArrayList<>();
                for(DataSnapshot expsnapshot:dataSnapshot.getChildren()){
                    //e=expsnapshot.getValue(ExpenseData.class);
                    try {
                        expenseslist.add(expsnapshot.getValue(ExpenseData.class));
                    }
                    catch(Exception e)
                    {
                        Log.d("smu","crash "+e);
                    }
                }

                Log.d("smu",""+expenseslist);

                setTitle("Expense App");
                ExpenseAppFragment frag = new ExpenseAppFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("expenselist", expenseslist);
                //        bundle.putParcelable("list", (Parcelable) listOfExpenses);
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linlyt_container,frag,"ExpenseAppFragment").commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onAddExpClick(String name, String amount, String category,int categoryposition) {

        ExpenseData expenseData = new ExpenseData();
        expenseData.name = name;
        expenseData.amount= amount;
        expenseData.category = category;
        categorypos=categoryposition;
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String date = formatter.format(today);
        expenseData.date = date;
        expenseslist.add(expenseData);
        Log.d("inside list",""+expenseslist);

        myRef.setValue(expenseslist);

    }

    @Override
    public void onCancelClick() {
        setTitle("Expense App");
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else{
            Log.d("back_pressed3:::","stack empty");
        }
    }

    @Override
    public void addButtonClick(boolean clicked) {
        setTitle("Add Expense");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container,new AddExpenseFragment(),"AddExpenseFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void itemLongClick(ArrayList<ExpenseData> explist,int delpos) {
        myRef=FirebaseDatabase.getInstance().getReference("expenses").child(String.valueOf(delpos));
        //expenseDataArrayList.remove(expenseData);
        myRef.removeValue();
        myRef=database.getReference("expenses");
//        if(explist.size()==0){
//            expenseDataArrayList.
//        }

//        ExpenseAppFragment frag = new ExpenseAppFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("expenselist", expenseslist);
//        Log.d("list after deletion",""+expenseslist);
//        //        bundle.putParcelable("list", (Parcelable) listOfExpenses);
//        frag.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.linlyt_container,frag,"ExpenseAppFragment").commit();
    }

    @Override
    public void itemClick(ExpenseData expenseData,int position) {

        ShowExpenseFragment s = new ShowExpenseFragment();
        listviewpos=position;
        // expenseDataArrayList.add(e);
        Bundle b = new Bundle();
        b.putSerializable("expensedetails", expenseData);
        s.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, s, "ShowExpenseFragment")
                .commit();

    }

    @Override
    public void onCloseButtonClick() {

        setTitle("Expense App");
        ExpenseAppFragment s = new ExpenseAppFragment();
        // expenseDataArrayList.add(e);
        Bundle b = new Bundle();
        b.putSerializable("expenselist", expenseslist);
        s.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, s, "ExpenseAppFragment")
                .commit();

    }

    @Override
    public void onEditButtonClick(ExpenseData e) {
        setTitle("Edit Expense");
        EditExpenseFragment ed=new EditExpenseFragment();
        Bundle b=new Bundle();
        b.putSerializable("showexpensedetail",e);
        Log.d("mains exp obj",""+e);
        b.putSerializable("category",categorypos);
        Log.d("category",""+categorypos);
        ed.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container,ed,"EditExpenseFragment")
                .commit();

    }

    @Override
    public void onCancelClickfromedit() {
        //setTitle("Expense App");
        ExpenseAppFragment s = new ExpenseAppFragment();
        // expenseDataArrayList.add(e);
        Bundle b = new Bundle();
        b.putSerializable("expenselist", expenseslist);
        s.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, s, "ExpenseAppFragment")
                .commit();

    }

    @Override
    public void onsaveclick(String name, String amount, String category,int pos) {
        ExpenseData expenseData = new ExpenseData();
        expenseData.name = name;
        expenseData.amount= amount;
        expenseData.category = category;
        categorypos=pos;
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String date = formatter.format(today);
        expenseData.date = date;
        expenseslist.remove(listviewpos);
        Log.d("listafterreminsave",""+expenseslist);
        expenseslist.add(listviewpos,expenseData);
        Log.d("inside saves list",""+expenseslist);


        myRef.setValue(expenseslist);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ExpenseData e= new ExpenseData();
                expenseslist.clear();
                for(DataSnapshot expsnapshot:dataSnapshot.getChildren()){
                    e=expsnapshot.getValue(ExpenseData.class);
                    expenseslist.add(e);
                }

                Log.d("expenselist inside save",""+expenseslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ExpenseAppFragment frag = new ExpenseAppFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("expenselist", expenseslist);
        Log.d("list from editexp",""+expenseslist);
        //        bundle.putParcelable("list", (Parcelable) listOfExpenses);
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container,frag,"ExpenseAppFragment").commit();

    }
}
