package appexperts.alberto.com.realmexample.Model;

import io.realm.RealmObject;

/**
 * Created by alber on 23/02/2016.
 */
public class City extends RealmObject {
    private  String cityCode;
    private String cityName;
    private String address;

    public City() {
//        this.cityCode = cityCode;
//        this.cityName = cityName;
//        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setCityCode(String cityCode) {

        this.cityCode = cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
