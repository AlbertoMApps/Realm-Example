package appexperts.alberto.com.realmexample.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Country extends RealmObject {
    @PrimaryKey
    private String code;
    private String name;
    private int population;
    private City city;

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {

        return city;
    }

    public Country() { }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}