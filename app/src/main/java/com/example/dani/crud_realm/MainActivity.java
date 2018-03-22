package com.example.dani.crud_realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dani.crud_realm.Model.Person;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private EditText txtName,txtAge;
    private Button btnAdd,btnView,btnDelete;
    private TextView txtview;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         realm = Realm.getDefaultInstance(); // opens "myrealm.realm"

        txtName=(EditText)findViewById(R.id.editTextname);
        txtAge=(EditText)findViewById(R.id.editTextage);
        btnAdd=(Button) findViewById(R.id.addbutton);
        btnDelete=(Button) findViewById(R.id.deletebutton);
        btnView=(Button) findViewById(R.id.viewbutton);
        txtview=(TextView)findViewById(R.id.textView);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    save_to_database(txtName.getText().toString().trim(), Integer.parseInt(txtAge.getText().toString().trim()));
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Enter all fields",Toast.LENGTH_SHORT).show();
                }

               emptyfields();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=txtName.getText().toString().trim();

                delete_from_database(name);

               emptyfields();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_database();
            }
        });
    }

    private void delete_from_database(String name) {

        // obtain the results of a query
        final RealmResults<Person> persontodelete =realm.where(Person.class).equalTo("name",name).findAllAsync();


// All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
              //  persontodelete.deleteAllFromRealm();
            try {
                persontodelete.deleteFirstFromRealm();
                persontodelete.deleteLastFromRealm();
                Toast.makeText(MainActivity.this,"Deleted, Please Press View",Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                Toast.makeText(MainActivity.this,"Not Found",Toast.LENGTH_SHORT).show();

            }

            }
        });
    }

    private void View_database() {


        RealmResults<Person> result = realm.where(Person.class).findAllAsync();

        result.load();
        String output="";

        for (Person person: result){
            output+=person.toString();
        }
        txtview.setText(output);

    }

    private void emptyfields() {
        txtName.setText("");
        txtAge.setText("");
    }

    private void save_to_database(final String name, final int age) {
/*
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
            }

        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.

                Toast.makeText(MainActivity.this,"Successfully Saved to Realm",Toast.LENGTH_SHORT).show();
            }

        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.

                Toast.makeText(MainActivity.this,"Failed to Saved",Toast.LENGTH_SHORT).show();

            }
        });

*/
// NEW Way

        realm.beginTransaction();
        Person person = realm.createObject(Person.class); // Create a new object
        person.setName(name);
        person.setAge(age);
        realm.commitTransaction();





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
