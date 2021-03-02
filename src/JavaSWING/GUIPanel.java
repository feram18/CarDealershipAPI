package JavaSWING;

import java.sql.SQLException;

/**
 * The Interface GUIPanel defines the methods to be
 * implemented across the GUI Panels.
 */
public interface GUIPanel {

	void populateComboBoxes();
	void search() throws SQLException;
	void add() throws SQLException;
	void populateToUpdate() throws SQLException;
	void update() throws SQLException;
	void delete() throws SQLException;
	void clearFields();
	void addToQuery();
	void populateToAdd() throws SQLException;
}
