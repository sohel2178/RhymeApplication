package com.imatbd.rhymeapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.imatbd.rhymeapplication.Model.Dog;
import com.imatbd.rhymeapplication.Model.Person;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private Button btnAdd,btnGetLastPerson;
    private Spinner dogSpinner;
    private TextView tvPersonName,tvDogName;

    private ArrayAdapter<Dog> spinnerAdapter;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);



        realm = Realm.getDefaultInstance();


        spinnerAdapter = new ArrayAdapter<Dog>(this,android.R.layout.simple_dropdown_item_1line);
        initView();

        getAllData();


    }

    private void getAllData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Dog> doglist =
                        realm.where(Dog.class)
                        .findAll();

                for(Dog x :doglist){
                    spinnerAdapter.add(x);
                }

                spinnerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.name);
        btnAdd = findViewById(R.id.add);
        btnGetLastPerson = findViewById(R.id.get_person);
        tvPersonName = findViewById(R.id.person_name);
        tvDogName = findViewById(R.id.dog_name);
        dogSpinner = findViewById(R.id.dog_spinner);
        dogSpinner.setAdapter(spinnerAdapter);


        btnAdd.setOnClickListener(this);
        btnGetLastPerson.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {



        if(view.equals(btnAdd)){

            String name = etName.getText().toString();
            Dog dog = spinnerAdapter.getItem(dogSpinner.getSelectedItemPosition());

            Person person = new Person();
            person.setName(name);
            person.setDog(dog);
            addPerson(person);
        }else if(view.equals(btnGetLastPerson)){
            getLastPerson();
        }

    }

    private void getLastPerson() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Person> personList = realm.where(Person.class)
                        .findAllSorted("id");

                Person person = personList.last();

                tvPersonName.setText(person.getName());
                tvDogName.setText(person.getDog().getName());
            }
        });
    }

    private void addPerson(final Person person) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Number currentIdNum = realm.where(Dog.class).max("id");

                int nextId;

                if(currentIdNum==null){
                    nextId=1;
                }else {
                    nextId = currentIdNum.intValue()+1;
                }

                person.setId(nextId);

                realm.insertOrUpdate(person);

            }
        });
    }


}
