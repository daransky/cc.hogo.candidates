package com.hogo.portal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.daro.common.ui.UIError;

public abstract class AbstractDBModel<T> implements DBModel<T> {

	protected static Connection	connection;
	
	public static Connection getConnection() { 
		return connection;
	}
	
	public static class DBIterator<T> implements Iterator<T>, AutoCloseable {
		final ResultSet rs;
		final Function<ResultSet, T> row2e;

		public DBIterator(ResultSet rs, Function<ResultSet, T> row2e) {
			this.rs = rs;
			this.row2e = row2e;
		}

		@Override
		public boolean hasNext() {
			try {
				return rs.next();
			} catch (SQLException e) {
				UIError.showError("DB Fehler", e);
				return false;
			}
		}

		@Override
		public T next() {
			return row2e.apply(rs);
		}

		@Override
		public void close() throws Exception {
			if (!rs.isClosed())
				rs.close();
		}
	}

	protected PreparedStatement select, update, updateRead, delete, insert;
	protected Function<ResultSet, T> row2e;
	protected BiConsumer<T, Statement> e2row;

	void closeStatement(PreparedStatement stmt) throws Exception {
		if (stmt != null && !stmt.isClosed())
			stmt.close();
	}

	public AbstractDBModel(Function<ResultSet, T> row2entry, BiConsumer<T, Statement> entry2row) {
		row2e = row2entry;
		e2row = entry2row;
	}
	
	@Override
	public void close() throws Exception {
		closeStatement(select);
		closeStatement(insert);
		closeStatement(delete);
		closeStatement(update);
		closeStatement(updateRead);
	}

	@Override
	public Collection<T> select() throws SQLException {
		Collection<T> result = new LinkedList<>();
		read().forEachRemaining(e -> result.add(e));
		return result;
	}

	@Override
	public Iterator<T> read() throws SQLException {
		return new DBIterator<>(select.executeQuery(), row2e);
	}

	public void add(T e) throws SQLException {
		if (insert == null)
			return;
		e2row.accept(e, insert);
		insert.executeUpdate();
	}

	public T read(long id) throws SQLException {
		if (updateRead == null)
			return null;
		updateRead.setLong(1, id);
		ResultSet rs = updateRead.executeQuery();
		return rs.next() ? row2e.apply(rs) : null;
	}

	public void update(T e, BiConsumer<T, PreparedStatement> e2r) throws SQLException {
		if (update == null)
			return;
		if (e2r == null)
			e2row.accept(e, update);
		else
			e2r.accept(e, update);

		update.executeUpdate();
	}

	public void update(T e) throws SQLException {
		if (update == null)
			return;
		e2row.accept(e, update);
		update.executeUpdate();
	}

	public void delete(long id) throws SQLException {
		if (delete == null)
			return;
		delete.setLong(1, id);
		delete.executeUpdate();
	}
	
	public void delete(Object id) throws SQLException {
		if (delete == null)
			return;
		delete.setObject(1, id);
		delete.executeUpdate();
	}
}
