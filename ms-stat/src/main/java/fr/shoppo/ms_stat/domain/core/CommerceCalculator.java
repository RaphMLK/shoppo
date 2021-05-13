package fr.shoppo.ms_stat.domain.core;

import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.domain.core.loadable.commerce.CommerceLoadable;
import fr.shoppo.ms_stat.presentation.RealTimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class CommerceCalculator implements RealTimeCalculator {

    Pair<CommerceLoadable,Object> load;
    StatService statService;

    @Override
    public void run() {
        var fn = load.getFirst();
        var p1 = load.getSecond();
        fn.consume(p1,statService);
    }

    @Override
    public <T> RealTimeCalculator load(T toLoad) {
        load = Pair.of(CommerceLoadable.fromClass(toLoad.getClass()),toLoad);
        return this;
    }

    @Autowired
    public void setStatService(StatService statService) {
        this.statService = statService;
    }
}
