package com.stylefeng.guns.modular.custom.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class StringToURLTypeHandler extends BaseTypeHandler<URL> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, URL parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setURL(i, parameter);
	}

	@Override
	public URL getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String filePath = rs.getString(columnName);
		try {
			return new URL(filePath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public URL getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String filePath = rs.getString(columnIndex);
		try {
			return new URL(filePath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public URL getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String filePath = cs.getString(columnIndex);
		try {
			return new URL(filePath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
