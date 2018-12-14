package com.hogo.portal.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;

public interface DBModel<T> extends AutoCloseable {

	Collection<T> select() throws SQLException;

	Iterator<T> read() throws SQLException;

	void add(T e) throws SQLException;

	T read(long id) throws SQLException;

	void update(T e, BiConsumer<T, PreparedStatement> e2r) throws SQLException;

	void update(T e) throws SQLException;

	void delete(long id) throws SQLException;

}
