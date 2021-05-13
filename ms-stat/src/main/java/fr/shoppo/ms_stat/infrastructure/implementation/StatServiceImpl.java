package fr.shoppo.ms_stat.infrastructure.implementation;

import fr.shoppo.ms_stat.domain.StatService;
import fr.shoppo.ms_stat.domain.bo.Stat;
import fr.shoppo.ms_stat.domain.bo.stat_id.StatId;
import fr.shoppo.ms_stat.infrastructure.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {

    StatRepository repository;

    @Override
    public void saveStat(Stat stat) {
        var staFromSave = repository.findByCustomId(stat.getCustomId());
        staFromSave.ifPresent(fromSave -> stat.setId(fromSave.getId()));
        repository.save(stat);
    }

    @Override
    public Optional<Stat> findByCustomId(StatId id){
      return repository.findByCustomId(id);
    }

    @Autowired
    public void setRepository(StatRepository repository) {
        this.repository = repository;
    }
}
