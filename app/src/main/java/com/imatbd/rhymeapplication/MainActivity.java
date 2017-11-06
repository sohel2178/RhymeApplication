package com.imatbd.rhymeapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imatbd.rhymeapplication.Model.Dog;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName,etAge;
    private Button btnAdd,btnCountDog,btnAddPerson;
    private TextView tvDogCounter;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        realm =Realm.getDefaultInstance();



    }

    private void initView() {
        etName = findViewById(R.id.dog_name);
        etAge = findViewById(R.id.age);
        btnAdd = findViewById(R.id.add);
        btnAddPerson = findViewById(R.id.add_person);

        tvDogCounter = findViewById(R.id.dog_counter);
        btnCountDog = findViewById(R.id.count_dog);

        btnAdd.setOnClickListener(this);
        btnCountDog.setOnClickListener(this);
        btnAddPerson.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.equals(btnAdd)){
            String dogName = etName.getText().toString();
            String dogAge = etAge.getText().toString();

            int age = Integer.parseInt(dogAge);

            Dog dog = new Dog();
            dog.setName(dogName);
            dog.setAge(age);

            insertDog(dog);


        }else if(view.equals(btnCountDog)){

            countDogs();

        }else if(view.equals(btnAddPerson)){
            startAddNewPersonActivity();
        }





    }

    private void startAddNewPersonActivity() {
        startActivity(new Intent(getApplicationContext(),PersonActivity.class));
    }

    private void countDogs() {
        RealmResults<Dog> doglist = realm.where(Dog.class)
                .equalTo("name","Sohel")
                .or()
                .equalTo("name","Rony")
                .findAll();

        tvDogCounter.setText("Dog found in tha database "+doglist.size());
    }

    private void insertDog(final Dog dog) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Dog.class).max("id");

                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }

                dog.setId(nextId);

                realm.insert(dog);

                clearField();
            }
        });
    }

    private void clearField() {
        etName.setText("");
        etAge.setText("");
    }
}
