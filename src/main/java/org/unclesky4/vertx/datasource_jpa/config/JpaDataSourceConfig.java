package org.unclesky4.vertx.datasource_jpa.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.hibernate.HikariConnectionProvider;

public class JpaDataSourceConfig {

	private HikariConnectionProvider connectionProvider = null;
	
	private static volatile JpaDataSourceConfig jpaDataSourceConfig = null;
	
	private JpaDataSourceConfig() {}
	
	public static JpaDataSourceConfig getInstance() {
		if (jpaDataSourceConfig == null) {
			synchronized (JpaDataSourceConfig.class) {
				if (jpaDataSourceConfig == null) {
					jpaDataSourceConfig = new JpaDataSourceConfig();
					
					Map<String, String> propsMap = new HashMap<>();
					propsMap.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
					propsMap.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/Learnease?useSSL=false");
					propsMap.put("hibernate.connection.username", "uncle");
					propsMap.put("hibernate.connection.password", "uncle");
					propsMap.put("hibernate.connection.datasource", "javax.persistence.jtaDataSource");
					propsMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
					propsMap.put("hibernate.show_sql", "true");
					propsMap.put("hibernate.hbm2ddl.auto", "update");
					propsMap.put("hibernate.connection.autocommit", "false");
					
					jpaDataSourceConfig.connectionProvider = new HikariConnectionProvider();
					jpaDataSourceConfig.connectionProvider.configure(propsMap);
				}
			}
		}
		return jpaDataSourceConfig;
	}
	
	public Connection getConnection() throws SQLException {
		return jpaDataSourceConfig.connectionProvider.getConnection();
	}
	
	public void close(Connection connection) throws SQLException {
		jpaDataSourceConfig.connectionProvider.closeConnection(connection);
	}
	
	public void stop() {
		jpaDataSourceConfig.connectionProvider.stop();
	}
}
