package lyc.foreignexchangerate;

/**
 * Created by kk on 2017/5/21.
 */

public class Currencydata {

    String currency;
    double value;

    public Currencydata(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
