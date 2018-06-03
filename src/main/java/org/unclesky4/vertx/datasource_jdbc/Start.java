package org.unclesky4.vertx.datasource_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unclesky4.vertx.datasource_jdbc.config.HikariDataSourceConfig;


public class Start {

	public static void main(String[] args) throws SQLException {
		HikariDataSourceConfig config = HikariDataSourceConfig.getInstance();
		if (config == null) {
			System.out.println("========= null ========");
		}else {
			Connection connection = config.getConnection();
			System.out.println(connection.getCatalog());
			PreparedStatement statement = connection.prepareStatement("select uid,code,email from user");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.print(resultSet.getString(1)+ "  ");
				System.out.print(resultSet.getString(2)+"  ");
				System.out.println(resultSet.getString(3));
			}
		}
	}

}
