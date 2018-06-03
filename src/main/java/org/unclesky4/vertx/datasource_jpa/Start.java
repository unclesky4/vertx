package org.unclesky4.vertx.datasource_jpa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.unclesky4.vertx.datasource_jpa.config.JpaDataSourceConfig;

public class Start {

	public static void main(String[] args) throws SQLException {
		JpaDataSourceConfig config = JpaDataSourceConfig.getInstance();
		
		ResultSet resultSet = config.getConnection().prepareStatement("select uid,code,email from user").executeQuery();
		while (resultSet.next()) {
			System.out.print(resultSet.getString(1)+ "  ");
			System.out.print(resultSet.getString(2)+"  ");
			System.out.println(resultSet.getString(3));
		}
	}

}
