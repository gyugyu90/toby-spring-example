package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import user.sqlservice.OxmSqlService;
import user.sqlservice.SqlMapConfig;
import user.sqlservice.SqlRegistry;
import user.sqlservice.SqlService;
import user.sqlservice.updatable.EmbeddedDbSqlRegistry;

import javax.sql.DataSource;

@Configuration
public class SqlServiceContext {

    @Autowired
    SqlMapConfig sqlMapConfig;

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        sqlService.setSqlMap(sqlMapConfig.getSqlMapResource());
        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("user.sqlservice.jaxb");
        return jaxb2Marshaller;
    }

    @Bean
    public DataSource embeddedDatabase() {
        EmbeddedDatabase database = new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:sqlRegistrySchema.sql")
                .build();
        return database;
    }

}
