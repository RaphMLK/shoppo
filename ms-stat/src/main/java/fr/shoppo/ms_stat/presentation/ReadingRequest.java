package fr.shoppo.ms_stat.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.domain.bo.Stat;
import fr.shoppo.ms_stat.domain.bo.input.ReadingInput;
import fr.shoppo.ms_stat.domain.bo.stat_id.BaseId;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
* I will be using to be call for sending informations about our stats
*
* */
@Component
public class ReadingRequest {
    final static DateFormat DATE = new SimpleDateFormat("yyyyMMdd");

    StatService statService;

    @RabbitListener(queues = "${mq.queue.stat.read}")
    public String requestingData(ReadingInput input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AtomicReference<Stat> response = new AtomicReference<>(new Stat());
        var cal = Calendar.getInstance();
        Date today = cal.getTime();

        return mapper.writeValueAsString(IntStream.range(0,30)
                .mapToObj(j -> {
                    var date = cal.getTime();
                    statService.findByCustomId(BaseId.of(input.getUserType(),input.format(),date))
                            .ifPresentOrElse(response::set,() -> response.set(new Stat()));
                    cal.add(Calendar.DAY_OF_MONTH,-1);
                    return Pair.of(DATE.format(date),response.get());
                }).collect(Collectors.toMap(Pair::getFirst,Pair::getSecond)));
    }

    @Autowired
    public void setStatService(StatService statService) {
        this.statService = statService;
    }
}
