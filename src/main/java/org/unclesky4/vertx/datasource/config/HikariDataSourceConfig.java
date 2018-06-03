package org.unclesky4.vertx.datasource.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceConfig {
	
	private HikariDataSource ds = null;
	
	private static volatile HikariDataSourceConfig hikariDataSourceConfig = null;
	
	private HikariDataSourceConfig() {}
	
	public static HikariDataSourceConfig getInstance() {
		if (hikariDataSourceConfig == null) {
			synchronized (HikariDataSourceConfig.class) {
				if (hikariDataSourceConfig == null) {
					hikariDataSourceConfig = new HikariDataSourceConfig();
					
					HikariConfig config = new HikariConfig();
					config.setDriverClassName("com.mysql.jdbc.Driver");
					config.setJdbcUrl("jdbc:mysql://localhost:3306/Learnease?useSSL=false");
					config.setUsername("uncle");
					config.setPassword("uncle");
					config.setAutoCommit(false);
					config.addDataSourceProperty("cachePrepStmts", "true");
					config.addDataSourceProperty("prepStmtCacheSize", "250");
					config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
					
					config.addDataSourceProperty("useServerPrepStmts", "true");
					config.addDataSourceProperty("useLocalSessionState", "true");
					config.addDataSourceProperty("rewriteBatchedStatements", "true");
					config.addDataSourceProperty("cacheResultSetMetadata", "true");
					
					config.addDataSourceProperty("cacheServerConfiguration", "true");
					config.addDataSourceProperty("cacheResultSetMetadata", "true");
					config.addDataSourceProperty("maintainTimeStats", "false");
					
					
					hikariDataSourceConfig.ds = new HikariDataSource(config);
				}
			}
		}
		return hikariDataSourceConfig;
	}
	
	public static void shutdown() {
		getInstance().ds.close();
	}
	
	public Connection getConnection() {
		try {
			return getInstance().ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
