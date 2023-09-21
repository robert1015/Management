import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductName {
    private final String value;
    ProductName(String value) throws Exception {
        if(isValidName(value))
            this.value = value;
        else
            throw new Exception("null");
    }
    private boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^(?!.*  )[a-zA-Z0-9. ,]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public String getValue() {
        return value;
    }
}