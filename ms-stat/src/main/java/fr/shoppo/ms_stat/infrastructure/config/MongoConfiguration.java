package fr.shoppo.ms_stat.infrastructure.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "fr.shoppo.ms_stat.infrastructure")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    String databaseName;
    String mongoUrl;

    @Override
    @NonNull
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    @NonNull
    public MongoTemplate mongoTemplate(
            @NonNull MongoDatabaseFactory databaseFactory,
            @NonNull MappingMongoConverter converter
    ) {
        var mongoTemplate = super.mongoTemplate(databaseFactory, converter);

        mongoTemplate.indexOps("stat").ensureIndex(new Index("customId", Sort.Direction.ASC).unique());

        return mongoTemplate;
    }

    @Value("${mongo.database.name}")
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Value("${mongo.database.url}")
    public void setMongoUrl(String mongoUrl) {
        this.mongoUrl = mongoUrl;
    }
}
