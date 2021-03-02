package JavaSWING;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to format the Date picked by the user on JDatePicker UI components
 */
public class DateLabelFormatter extends AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
     
    /**
     * Convert from String to Date value on set format.
     *
     * @param String
     * @return Date object
     * @throws ParseException
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }
 
    /**
     * Convert from Date value to string with set pattern.
     *
     * @param value
     * @return String
     * @throws ParseException
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar calendar = (Calendar) value;
            return dateFormatter.format(calendar.getTime());
        } else {
        	return null;
        }
    }
}
