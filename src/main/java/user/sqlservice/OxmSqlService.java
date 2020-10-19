package user.sqlservice;

import org.springframework.oxm.Unmarshaller;
import user.dao.UserDao;
import user.sqlservice.jaxb.SqlType;
import user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService {

    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();

    private final BaseSqlService baseSqlService = new BaseSqlService();

    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmapFile(String sqlmapFile) {
        oxmSqlReader.setSqlmapFile(sqlmapFile);
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        return baseSqlService.getSql(key);
    }

    @PostConstruct
    public void loadSql() {
        baseSqlService.setSqlReader(oxmSqlReader);
        baseSqlService.setSqlRegistry(sqlRegistry);

        baseSqlService.loadSql();
    }

    private class OxmSqlReader implements SqlReader {

        private Unmarshaller unmarshaller;
        private static final String DEFAULT_SQLMAP_FILE = "/sqlmap-user.xml";
        private String sqlmapFile = DEFAULT_SQLMAP_FILE;

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmapFile(String sqlmapFile) {
            this.sqlmapFile = sqlmapFile;
        }

        @Override
        public void read(SqlRegistry sqlRegistry) {
            try {
                Source source = new StreamSource(UserDao.class.getResourceAsStream(this.sqlmapFile));
                Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(source);

                for (SqlType sql : sqlmap.getSql()) {
                    sqlRegistry.registerSql(sql.getKey(), sql.getValue());
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(this.sqlmapFile + "을 가져올 수 없습니다.", e);
            }
        }
    }
}
