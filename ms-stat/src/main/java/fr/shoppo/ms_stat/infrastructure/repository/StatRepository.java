package fr.shoppo.ms_stat.infrastructure.repository;

import fr.shoppo.ms_stat.domain.bo.Stat;
import fr.shoppo.ms_stat.domain.bo.stat_id.StatId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StatRepository extends MongoRepository<Stat, String> {
    Optional<Stat> findByCustomId(StatId id);
}
