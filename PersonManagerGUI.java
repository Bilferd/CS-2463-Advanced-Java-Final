//Jayden Wyatt
//CS 2463
//Final Project

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

public class PersonManagerGUI extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Person> listModel = new DefaultListModel<>();
    private JList<Person> personList = new JList<>(listModel);

    private ArrayList<Person> people = new ArrayList<>();
    private File currentFile = null;
    private boolean modified = false;
	private JMenuItem saveAsItem;
	private JMenuItem saveItem;
	private boolean editing = false;
	

    public PersonManagerGUI() {
        setTitle("Person Manager");
        setSize(600, 400);
        setLayout(new BorderLayout());

        add(new JScrollPane(personList), BorderLayout.CENTER);

        add(createButtons(), BorderLayout.SOUTH);
        setJMenuBar(createMenu());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        setVisible(true);
    }
	
	private JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem newItem = new JMenuItem("New");
		JMenuItem openItem = new JMenuItem("Open...");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As...");
		JMenuItem exitItem = new JMenuItem("Exit");

		newItem.addActionListener(e -> newFile());
		openItem.addActionListener(e -> openFile());
		saveItem.addActionListener(e -> saveFile());
		saveAsItem.addActionListener(e -> saveFileAs());
		exitItem.addActionListener(e -> exitApplication());

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(exitItem);

		bar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("About");
		helpItem.addActionListener(e ->
			JOptionPane.showMessageDialog(this, "Person Manager\nOCCC Assignment")
		);

		helpMenu.add(helpItem);
		bar.add(helpMenu);
		
		editing = false;
		updateSaveState();
		return bar;
	}
	
	private void updateSaveState() {
		boolean enabled = !editing;
		saveItem.setEnabled(enabled);
		saveAsItem.setEnabled(enabled);
	}
	
	private void newFile() {
		if (modified) {
			int choice = JOptionPane.showConfirmDialog(
				this,
				"Save current file before creating a new one?",
				"Confirm",
				JOptionPane.YES_NO_CANCEL_OPTION
			);

			if (choice == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (choice == JOptionPane.CANCEL_OPTION) {
				return; // stop creating new file
			}
		}

		// Clear data
		people.clear();
		listModel.clear();
		currentFile = null;
		modified = false;
	}
	
	private void saveFile() {

		if (editing) {
			JOptionPane.showMessageDialog(this, "Finish editing before saving.");
			return;
		}

		if (currentFile == null) {
			saveFileAs();
			return;
		}

		try (ObjectOutputStream out =
				new ObjectOutputStream(new FileOutputStream(currentFile))) {

			out.writeObject(people);
			modified = false;

		} catch (IOException e) {
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
		}
	}
	
	private void saveFileAs() {
		JFileChooser chooser = new JFileChooser();

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			currentFile = chooser.getSelectedFile();
			saveFile();
		}
		
		if (editing) {
			JOptionPane.showMessageDialog(this, "Finish editing before saving.");
			return;
		}
	}
	
	private void openFile() {
		JFileChooser chooser = new JFileChooser();
		
		if (modified) {
			int choice = JOptionPane.showConfirmDialog(
				this,
				"Save before opening a new file?",
				"Confirm",
				JOptionPane.YES_NO_CANCEL_OPTION
			);

			if (choice == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (choice == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try (ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(chooser.getSelectedFile()))) {

				people = (ArrayList<Person>) in.readObject();

				listModel.clear();
				for (Person p : people) {
					listModel.addElement(p);
				}

				currentFile = chooser.getSelectedFile();
				modified = false;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error loading file");
			}
		}
	}
	
	private void addPerson() {
		String[] options = {"Person", "RegisteredPerson", "OCCCPerson"};

		String type = (String) JOptionPane.showInputDialog(
			this,
			"Select Person Type",
			"Type",
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);

		if (type == null) return; 
		
		editing = true;
		updateSaveState();
		
		JTextField first = new JTextField();
		JTextField last = new JTextField();
		JTextField day = new JTextField();
		JTextField month = new JTextField();
		JTextField year = new JTextField();
		JTextField govID = new JTextField();
		JTextField studentID = new JTextField();

		JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(new JLabel("First Name")); panel.add(first);
		panel.add(new JLabel("Last Name")); panel.add(last);
		panel.add(new JLabel("Day")); panel.add(day);
		panel.add(new JLabel("Month")); panel.add(month);
		panel.add(new JLabel("Year")); panel.add(year);
		
		if (type.equals("RegisteredPerson") || type.equals("OCCCPerson")) {
			panel.add(new JLabel("Gov ID")); panel.add(govID);
		}

		if (type.equals("OCCCPerson")) {
			panel.add(new JLabel("Student ID")); panel.add(studentID);
		}

		int result = JOptionPane.showConfirmDialog(this, panel, "Add Person",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			try {
				int d = Integer.parseInt(day.getText());
				int m = Integer.parseInt(month.getText());
				int y = Integer.parseInt(year.getText());

				OCCCDate dob = new OCCCDate(d, m, y);

				Person p;

				if (type.equals("Person")) {
					p = new Person(first.getText(), last.getText(), dob);

				} else if (type.equals("RegisteredPerson")) {
					p = new RegisteredPerson(
						first.getText(), last.getText(), dob, govID.getText()
					);

				} else { // OCCCPerson
					p = new OCCCPerson(
						first.getText(), last.getText(), dob,
						govID.getText(), studentID.getText()
					);
				}

				people.add(p);
				listModel.addElement(p);
				modified = true;
			} catch (InvalidOCCCDateException ex) {
				JOptionPane.showMessageDialog(this, "Invalid Date!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid Input");
			}
		}
		
		editing = false;
		updateSaveState();
	}
	
	private void deletePerson() {
		int index = personList.getSelectedIndex();
		if (index >= 0) {
			people.remove(index);
			listModel.remove(index);
			modified = true;
		}
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();

		JButton addBtn = new JButton("Add");
		JButton deleteBtn = new JButton("Delete");

		addBtn.addActionListener(e -> addPerson());
		deleteBtn.addActionListener(e -> deletePerson());

		panel.add(addBtn);
		panel.add(deleteBtn);

		return panel;
	}
	
	private void exitApplication() {
		if (modified) {
			int choice = JOptionPane.showConfirmDialog(
				this, "Save before exit?", "Confirm",
				JOptionPane.YES_NO_CANCEL_OPTION
			);

			if (choice == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (choice == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PersonManagerGUI());
	}
}

    