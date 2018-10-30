package com.zobgo.common.crawler.config;

import com.zobgo.common.crawler.utils.FileUtil;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration manager.
 * Usage:
 * Config.getString("config_name")
 * Config.getInt("config_name", default_value)
 * Our configurations are stored in several files, both stored in src/main/resources/conf/.
 * common.conf contains environment-independent configurations. It will always be loaded.
 * prod.conf/staging.conf/dev.conf contains configurations that are different in each environment. Only one of them
 * will be loaded.
 * Environment variable PANDORA_ACTIVE_CONFIG determines which one will be loaded. If it is not specified, we will
 * load dev.conf
 */
@Slf4j
public class Config {
    // Valid log names
    public static final String DEV = "dev";
    public static final String STAGING = "staging";
    public static final String PROD = "prod";
    public static final String TEST = "test";
    // Singleton.
    private static Config instance;
    public  static String activeConfig = null;
    private CompositeConfiguration configuration;

    // Loads log files. Panics if anything goes wrong.
    private Config() {
        // Find active configuration from environment variable.
//        activeConfig = System.getProperty("spring_profiles_active");
        if (activeConfig == null) {
            activeConfig = System.getProperty("spring.profiles.active");
            log.info("System.getProperty(\"spring.profiles.active\") = " + System.getProperty("spring.profiles.active"));
            log.info("System.getenv(\"spring.profiles.active\") = " + System.getenv("spring.profiles.active"));
            if (activeConfig == null) {
                log.error("spring.profiles.active not exists", new Exception());
                activeConfig = DEV;
            }
        }
        loadActiveConfig();
    }

    private static Config getInstance(){
        if (instance == null){
            synchronized (Config.class){
                if (instance == null){
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    // Get active configuration.
    public static String getActiveConfig() {
        return activeConfig;
    }

    public static void loadTestConfig() {
        activeConfig = TEST;
        getInstance().loadActiveConfig();
//        ConnectionProvider.initDBSource();
    }

    // Get a string type log. Returns null if not found.
    public static String getString(String key) {
        return getInstance().configuration.getString(key);
    }

    // Get a string type log. Returns defaultValue if not found.
    public static String getString(String key, String defaultValue) {
        return getInstance().configuration.getString(key, defaultValue);
    }

    // Get a string type log. Panic and exit if not found.
    public static String getCriticalString(String key) {
        String value = getInstance().configuration.getString(key);
        if (value == null) {
            log.error("Cannot find configuration: " + key, new Exception());
            System.exit(-1);
        }
        return value;
    }

    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return getInstance().configuration.getBigDecimal(key, defaultValue);
    }

    // Get a int type log. Returns defaultValue if not found.
    public static int getInt(String key, int defaultValue) {
        return getInstance().configuration.getInt(key, defaultValue);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue){
        return getInstance().configuration.getBoolean(key,defaultValue);
    }

    // Get a int type log. Returns defaultValue if not found.
    public static Long getLong(String key) {
        return getInstance().configuration.getLong(key,null);
    }

    public static Long getLong(String key, Long defautValue) {
        return getInstance().configuration.getLong(key,defautValue);
    }

    private void loadActiveConfig() {
        // Verify active configuration. Panic if active configuration is invalid.
        HashSet<String> validConfigs = new HashSet<>();
        validConfigs.add(DEV);
        validConfigs.add(STAGING);
        validConfigs.add(PROD);
        validConfigs.add(TEST);
        if (!validConfigs.contains(activeConfig)) {
            log.error("Invalid configuration name: " + activeConfig, new Exception());
            System.exit(-1);
        }

        log.info("start config file");
        // Load common configuration and active configuration.
        configuration = new CompositeConfiguration();
        configuration.addConfiguration(loadConf("common"));
        configuration.addConfiguration(loadConf(activeConfig));
        log.info("end config file");
    }

    public static void setActiveConfig(String env){
        HashSet<String> validConfigs = new HashSet<>();
        validConfigs.add(DEV);
        validConfigs.add(STAGING);
        validConfigs.add(PROD);
        validConfigs.add(TEST);
        if (!validConfigs.contains(env)) {
            log.error("Invalid configuration name: " + env, new Exception());
            return;
        }
        activeConfig = env;
    }

    // Loads classpath:/conf/<configName>.conf
    // Panics if loading failed.
    private Configuration loadConf(String configName) {
        PropertiesConfiguration conf = new PropertiesConfiguration();
        InputStream is = null;
        try {
            log.info("/conf/" + configName + ".conf");
            is = Config.class.getResourceAsStream("/conf/" + configName + ".conf");
            conf.load(is);
        } catch (ConfigurationException e) {
            log.error("Cannot load configuration " + configName, e);
            FileUtil.safeClose(is);
            System.exit(-1);
        } finally {
            FileUtil.safeClose(is);
        }
        return conf;
    }
}
