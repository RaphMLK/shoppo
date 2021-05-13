package fr.shoppo.ms_stat.domain;

import fr.shoppo.ms_stat.domain.bo.Stat;
import fr.shoppo.ms_stat.domain.bo.stat_id.StatId;

import java.util.Optional;

public interface StatService {
    void saveStat(Stat stat);
    Optional<Stat> findByCustomId(StatId id);
}
