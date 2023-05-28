import java.util.*;

public class Speed {
    public double walk = 4.0;
    public double car = 70.0;
    public double bus = 25.0;
    public double metro = 60.0;
    String[] holidays = {
            "01.01", // Новый Год
            "02.01", // Новогодний выходной день
            "08.03", // Международный женский день
            "20.03", // Наурыз мейрамы
            "21.03", // Наурыз мейрамы часть 2
            "22.03", // Наурыз мейрамы часть 3
            "01.05", // Праздник единства народа Казахстана
            "07.05", // День защитника Отечества
            "09.05", // День Победы
            "30.08", // День Конституции Республики Казахстан
            "16.12", // День Независимости Республики Казахстан
            "17.12"  // День Независимости Республики Казахстан 2
    };

    public double checkDate(String date){
        if(Arrays.asList(holidays).contains(date)) return 1.2;
        return 1;
    }
    public double checkTime(String time){
        double minutes = convertToMinutes(time);
        //12:00-14:00
        if(minutes>=720 && minutes<=840) return 1.4;
        //07:00-09:00                       //18:00-20:00
        if((minutes>=420 && minutes<=540)||(minutes>=1080 && minutes<=1200)) return 1.6;
        return 1;
    }
    public double convertToMinutes(String time) {
        String[] parts = time.split(":");
        double hours = Integer.parseInt(parts[0]);
        double minutes = Integer.parseInt(parts[1]);
        return hours * 60.0 + minutes;
    }
}