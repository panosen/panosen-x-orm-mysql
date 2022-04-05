package com.panosen.orm.mysql;

import com.panosen.orm.PropertiesLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoaderTest {

    @Test
    public void loadDefaultProperties() throws IOException {

        Properties expectedProperties = prepareExpectedProperties();

        Properties actualProperties = PropertiesLoader.loadBuiltInProperties();

        Assert.assertEquals(expectedProperties.size(), actualProperties.size());

        for (Map.Entry<Object, Object> actual : actualProperties.entrySet()) {
            Assert.assertEquals(expectedProperties.getProperty(actual.getKey().toString()), actual.getValue().toString());
        }
    }

    @Test
    public void loadProperties() throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties properties = propertiesLoader.loadDataSourceProperties();
        Assert.assertEquals(2, properties.size());

        Assert.assertEquals("pong", properties.getProperty("ping"));
        Assert.assertEquals("org.h2.Driver", properties.getProperty("driverClassName"));
    }

    @Test
    public void testLoadPropertiesFromLibrary() throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties properties = propertiesLoader.loadDataSourceProperties("library");
        Assert.assertEquals(4, properties.size());

        Assert.assertEquals("pong-from-library", properties.getProperty("ping"));
    }

    @Test
    public void testLoadPropertiesFromSchool() throws IOException {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        Properties properties = propertiesLoader.loadDataSourceProperties("school");
        Assert.assertEquals(5, properties.size());

        Assert.assertEquals("pong-from-school", properties.getProperty("ping"));
    }


    // region org.apache.tomcat.jdbc.pool.DataSourceFactory

    protected static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
    protected static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
    protected static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
    protected static final String PROP_DEFAULTCATALOG = "defaultCatalog";

    protected static final String PROP_DRIVERCLASSNAME = "driverClassName";
    protected static final String PROP_PASSWORD = "password";
    protected static final String PROP_URL = "url";
    protected static final String PROP_USERNAME = "username";

    protected static final String PROP_MAXACTIVE = "maxActive";
    protected static final String PROP_MAXIDLE = "maxIdle";
    protected static final String PROP_MINIDLE = "minIdle";
    protected static final String PROP_INITIALSIZE = "initialSize";
    protected static final String PROP_MAXWAIT = "maxWait";
    protected static final String PROP_MAXAGE = "maxAge";

    protected static final String PROP_TESTONBORROW = "testOnBorrow";
    protected static final String PROP_TESTONRETURN = "testOnReturn";
    protected static final String PROP_TESTWHILEIDLE = "testWhileIdle";
    protected static final String PROP_TESTONCONNECT = "testOnConnect";
    protected static final String PROP_VALIDATIONQUERY = "validationQuery";
    protected static final String PROP_VALIDATIONQUERY_TIMEOUT = "validationQueryTimeout";
    protected static final String PROP_VALIDATOR_CLASS_NAME = "validatorClassName";

    protected static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
    protected static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
    protected static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";

    protected static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";

    protected static final String PROP_REMOVEABANDONED = "removeAbandoned";
    protected static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
    protected static final String PROP_LOGABANDONED = "logAbandoned";
    protected static final String PROP_ABANDONWHENPERCENTAGEFULL = "abandonWhenPercentageFull";

    protected static final String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
    protected static final String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
    protected static final String PROP_CONNECTIONPROPERTIES = "connectionProperties";

    protected static final String PROP_INITSQL = "initSQL";
    protected static final String PROP_INTERCEPTORS = "jdbcInterceptors";
    protected static final String PROP_VALIDATIONINTERVAL = "validationInterval";
    protected static final String PROP_JMX_ENABLED = "jmxEnabled";
    protected static final String PROP_FAIR_QUEUE = "fairQueue";

    protected static final String PROP_USE_EQUALS = "useEquals";
    protected static final String PROP_USE_CON_LOCK = "useLock";

    protected static final String PROP_DATASOURCE = "dataSource";
    protected static final String PROP_DATASOURCE_JNDI = "dataSourceJNDI";

    protected static final String PROP_SUSPECT_TIMEOUT = "suspectTimeout";

    protected static final String PROP_ALTERNATE_USERNAME_ALLOWED = "alternateUsernameAllowed";

    protected static final String PROP_COMMITONRETURN = "commitOnReturn";
    protected static final String PROP_ROLLBACKONRETURN = "rollbackOnReturn";

    protected static final String PROP_USEDISPOSABLECONNECTIONFACADE = "useDisposableConnectionFacade";

    protected static final String PROP_LOGVALIDATIONERRORS = "logValidationErrors";

    protected static final String PROP_PROPAGATEINTERRUPTSTATE = "propagateInterruptState";

    protected static final String PROP_IGNOREEXCEPTIONONPRELOAD = "ignoreExceptionOnPreLoad";

    protected static final String PROP_USESTATEMENTFACADE = "useStatementFacade";

    // endregion


    public Properties prepareExpectedProperties() {
        Properties properties = new Properties();

        properties.setProperty(PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver");

        properties.setProperty(PROP_TESTWHILEIDLE, "false");
        properties.setProperty(PROP_TESTONBORROW, "true");
        properties.setProperty(PROP_TESTONRETURN, "false");
        properties.setProperty(PROP_DEFAULTAUTOCOMMIT, "true");
        properties.setProperty(PROP_LOGVALIDATIONERRORS, "false");

        properties.setProperty(PROP_VALIDATIONQUERY, "SELECT 1");
        properties.setProperty(PROP_VALIDATIONQUERY_TIMEOUT, "250");
        properties.setProperty(PROP_VALIDATIONINTERVAL, "30000");

        properties.setProperty(PROP_TIMEBETWEENEVICTIONRUNSMILLIS, "5000");
        properties.setProperty(PROP_MINEVICTABLEIDLETIMEMILLIS, "30000");

        properties.setProperty(PROP_MAXAGE, "28000000");
        properties.setProperty(PROP_MAXACTIVE, "100");
        properties.setProperty(PROP_MINIDLE, "1");
        properties.setProperty(PROP_MAXWAIT, "4000");
        properties.setProperty(PROP_INITIALSIZE, "1");

        properties.setProperty(PROP_REMOVEABANDONEDTIMEOUT, "65");
        properties.setProperty(PROP_REMOVEABANDONED, "true");
        properties.setProperty(PROP_LOGABANDONED, "false");

        properties.setProperty(PROP_CONNECTIONPROPERTIES, "sendTimeAsDateTime=false;sendStringParametersAsUnicode=false;rewriteBatchedStatements=true;allowMultiQueries=true;useUnicode=true;characterEncoding=UTF-8;useSSL=false;socketTimeout=100000;connectTimeout=1050;loginTimeout=2;allowPublicKeyRetrieval=true");

        properties.setProperty(PROP_JMX_ENABLED, "true");

        properties.setProperty(PROP_INTERCEPTORS, "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;");

        return properties;
    }
}