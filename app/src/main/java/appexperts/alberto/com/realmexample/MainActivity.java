package appexperts.alberto.com.realmexample;

/***
 * https://realm.io/es/docs/java/latest/#many-to-one
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import appexperts.alberto.com.realmexample.Model.City;
import appexperts.alberto.com.realmexample.Model.Country;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    RealmConfiguration realmConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //realmConfiguration
        realmConfiguration = new RealmConfiguration.Builder(this).build();

        try {
            //Use of Realm realm for Android
            realm = Realm.getInstance(this);
            //If you wanna add another Realm to your app you need to create an unique name configured
            Realm myOtherRealm = Realm.getInstance(new RealmConfiguration.Builder(this)
                            .name("myOtherRealm.realm")
                            .build()
            );

            clearDB(realm);

            //create cities first
            realm.beginTransaction();
            City city1 = realm.createObject(City.class);
            City city2 = realm.createObject(City.class);
            City city3 = realm.createObject(City.class);
            City city4 = realm.createObject(City.class);
            realm.commitTransaction();
            //add data of cities
            addDataIntoCity(city1, "MA", "Madrid", "Spain street");
            addDataIntoCity(city2, "LI", "Lisboa", "Portugal street");
            addDataIntoCity(city3, "OS", "Oslo", "Norways street");
            addDataIntoCity(city4, "LO", "London", "Avenons Road");
            //add data of countries
            addDataIntoCountry("NO", 125455, "Norway", city3); //FK city...
            addDataIntoCountry("PO", 125455, "Portugal", city2);
            addDataIntoCountry("SP", 125455, "Spain", city1);
            addDataIntoCountry("UK", 125455, "United Kingdom", city4);

            //Queries
            RealmQuery<Country> query = realm.where(Country.class);
            RealmResults<Country> result = query.findAll();
            //        RealmResults<Country> results1 = realm.where(Country.class)
            //                        .findAllSorted("name", true);
            //        RealmResults<Country> results2 = realm.where(Country.class)
            //                        .greaterThan("population", 100000000)
            //                        .findAll();

            for (Country c : result) {
                Log.d("Data retreived, city: ", c.getName() + ",  capital: " + c.getCity().getCityName() );
                //Toast.makeText(getBaseContext(), c.getName(), Toast.LENGTH_SHORT);
            }
            //        for (int i = 0; i < result1.size(); i++) {
            //            City c = result1.get(i);
            //            Log.d("results1: ", c.getCityName());
            //        }
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


//Methods class for tables classes in the db

    private void clearDB(Realm realm) { //Clear the db data
        realm.beginTransaction();
        realm.allObjects(Country.class).clear(); //specify the class you want to clear
        realm.allObjects(City.class).clear(); //specify the class you want to clear
        realm.commitTransaction();
    }
    private void addDataIntoCountry(final String codeCountry , final int population, final String countryName, final City city) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { //do the beginTransaction, cancel in case of error and commit automatically.
                Country country = realm.createObject(Country.class); //It creates the constructor of the model class
                country.setName(countryName);
                country.setPopulation(population);
                country.setCode(codeCountry);
                country.setCity(city);
            }
        });
    }

    private void addDataIntoCity(final City c, final String code, final String cityName, final String street) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { //do the beginTransaction, cancel in case of error and commit automatically.
                //City c = realm.createObject(City.class); //It creates the constructor of the model class
                c.setCityCode(code);
                c.setCityName(cityName);
                c.setAddress(street);
            }
        });
    }
}
