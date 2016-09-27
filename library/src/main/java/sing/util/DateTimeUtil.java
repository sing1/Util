package sing.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimeUtil {

    private static Calendar calendar = Calendar.getInstance();
    private static int years = calendar.get(Calendar.YEAR);
    private static int months = calendar.get(Calendar.MONTH);
    private static int days = calendar.get(Calendar.DAY_OF_MONTH);
    private static int hours = calendar.get(Calendar.HOUR_OF_DAY);
    private static int minutes = calendar.get(Calendar.MINUTE);

    /**
     * choose time
     *
     * @param context
     * @param dataCallBack default callback
     * @param hour         default hour,if hour = -1, the default for the current time
     * @param minute       default minute,if minute = -1, the default for the minute time
     */
    public static void showTime(Context context, final DataCallBack dataCallBack, int hour, int minute) {
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = (hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay) + " : " + (minute < 10 ? "0" + minute : "" + minute);
                        dataCallBack.getData(time);
                    }
                }, hour == -1 ? hours : hour, minute == -1 ? minutes : minute, true);
        timePickerDialog.show();
    }

    /**
     * choose date
     *
     * @param context
     * @param dataCallBack default callback
     * @param year         default year,if year = -1, the default for the current year
     * @param month        default month,if month = -1, the default for the current month
     * @param day          default day,if day = -1, the default for the current day
     */
    public static void showDate(Context context, final DataCallBack dataCallBack, int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String time = year + "-" + (monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth);
                dataCallBack.getData(time);
            }
        }, year == -1 ? years : year, month == -1 ? months : month - 1, day == -1 ? days : day);
        datePickerDialog.show();
    }

    /**
     * choose date and time
     *
     * @param context
     * @param dataCallBack default callback
     * @param hour         default hour,if hour = -1, the default for the current time
     * @param minute       default minute,if minute = -1, the default for the minute time
     * @param year         default year,if year = -1, the default for the current year
     * @param month        default month,if month = -1, the default for the current month
     * @param day          default day,if day = -1, the default for the current day
     */
    public static void showDialogPicker(Context context, final DataCallBack dataCallBack,
                                 int hour, int minute, int year, int month, int day) {
        final StringBuffer time = new StringBuffer();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(" " + (hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay) + " : " + (minute < 10 ? "0" + minute : "" + minute));
                dataCallBack.getData(time.toString());
            }
        }, hour == -1 ? hours : hour, minute == -1 ? minutes : minute, true) {
            @Override
            protected void onStop() {
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                time.append(year + "-" + (monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth));
                timePickerDialog.show();
            }
        }, year == -1 ? years : year, month == -1 ? months : month - 1, day == -1 ? days : day) {
            @Override
            protected void onStop() {
                /**
                 * 点击确定和取消按钮时，会出发onTimeSet；
                 * 在dialog的onStop（比如dialog dismiss时）中，也调用了onTimeSet方法。
                 * 所以说复写对话框注掉supper.onStop()就行了，
                 */
            }
        };
        datePickerDialog.show();
    }

    public interface DataCallBack {
        void getData(String date);
    }
}