package fr.shoppo.ms_stat.domain.bo.stat_content;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDatedContent extends BaseContent {
    final static DateFormat DATE = new SimpleDateFormat("yyyyMMdd");
    final static DateFormat TIME = new SimpleDateFormat("HH:mm:ss.SSS");

    String saveDate;
    String saveTime;

    public static BaseContent of(String name, Object value){
        var content = new BaseDatedContent();

        var now = new Date();

        content.setName(name);
        content.setValue(value);
        content.setSaveDate(DATE.format(now));
        content.setSaveTime(TIME.format(now));

        return content;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

}
