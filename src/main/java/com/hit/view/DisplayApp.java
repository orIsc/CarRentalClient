package com.hit.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;
import com.hit.dao.*;
import com.hit.dm.Car;
import com.hit.model.AppModel;
import com.hit.server.Server;
import javax.swing.JScrollPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisplayApp {

	private PropertyChangeSupport support;
	private JFrame mainFrame;
	private JTextField searchBar;
	private IDao iDao = DaoFileImpl.getInstance("/Users/orissacci/Documents/workspace/CarRentalProject/src/main/resources/DataModel.json");
	private List<Car> carList = new ArrayList<Car>();
	private List<String[]> row = new ArrayList<>();
 	private int tableSize = 0;
	private JTable table;
	private DefaultTableModel model;
	private JFrame frame;
	private Car car = new Car("", "", 0, 0);
	private JRadioButton companyRadioButton;
	private JRadioButton priceRadioButton;
	private JRadioButton modelRadioButton;
	private JRadioButton yearRadioButton;
	private JFrame updateFrame;
	private Car updatedCar;
	
	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DisplayApp(PropertyChangeSupport support) {
		this.support= support;
		start();
	}

	public void addPropertyChangeListener (PropertyChangeListener listener){
	    this.support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
	    this.support.removePropertyChangeListener(listener);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.addWindowListener(new WindowAdapter() {

		});
		mainFrame.getContentPane().setBackground(new Color(64, 64, 64));
		mainFrame.setBounds(100, 100, 908, 568);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AppModel appModel = new AppModel();
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(255, 215, 0));
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(new Color(255, 215, 0));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(255, 215, 0));
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, 694, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
						.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
						.addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		
		model = new DefaultTableModel();
		model.addColumn("company");
		model.addColumn("model");
		model.addColumn("year");
		model.addColumn("price");
		
		carList = iDao.getAll();
		
		for(Car car : carList) {
			row.add(new String[] {car.getCompany(), car.getModel(), 
					Integer.toString(car.getYear()), Integer.toString(car.getPrice())});
		}
		for (int i = 0; i< row.size(); i++) {
	        model.addRow(row.get(i));
	        tableSize++;        	
	    }
		
		table = new JTable(model);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		table.setRowHeight(32);
		table.setGridColor(Color.BLACK);
		table.setBackground(new Color(65, 105, 225));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(UIManager.getColor("Button.light"));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tablePanel.add(scrollPane);
		GroupLayout gl_tablePanel = new GroupLayout(tablePanel);
		gl_tablePanel.setHorizontalGroup(
			gl_tablePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_tablePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_tablePanel.setVerticalGroup(
			gl_tablePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tablePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
					.addContainerGap())
		);
		tablePanel.setLayout(gl_tablePanel);
		
			
		JButton searchButton = new JButton("Search");
		searchButton.setBackground(new Color(100, 149, 237));
		searchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String pat = searchBar.getText();
				if(pat != "") {
					if(companyRadioButton.isSelected()) {
						carList = appModel.searchCarByCompany(pat);			
					}
					else if(modelRadioButton.isSelected()) {
						carList = appModel.searchCarByModel(pat);
					}
					else if(yearRadioButton.isSelected()) {
						carList = appModel.searchCarByYear(pat);
					}
					else if(priceRadioButton.isSelected()) {
						carList = appModel.searchCarByPrice(pat);
					}
				}else {
					carList = appModel.getAll();
				}
				model.setRowCount(0);
				row.clear();
				for(Car car : carList) {
					row.add(new String[] {car.getCompany(), car.getModel(), 
							Integer.toString(car.getYear()), Integer.toString(car.getPrice())});
				}
				for (int i = 0; i< row.size(); i++) {
			        model.addRow(row.get(i));
			        tableSize++;        	
			    }
			}
		});
		
		JButton sortListButton = new JButton("Sort list");
		sortListButton.setBackground(new Color(28, 150, 255));
		sortListButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		sortListButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(companyRadioButton.isSelected()) {
					carList = appModel.sortCarByCompany();			
				}
				else if(modelRadioButton.isSelected()) {
					carList = appModel.sortCarByModel();
				}
				else if(yearRadioButton.isSelected()) {
					carList = appModel.sortCarByYear();
				}
				else if(priceRadioButton.isSelected()) {
					carList = appModel.sortCarByPrice();
				}
				model.setRowCount(0);
				row.clear();
				for(Car car : carList) {
					row.add(new String[] {car.getCompany(), car.getModel(), 
							Integer.toString(car.getYear()), Integer.toString(car.getPrice())});
				}
				for (int i = 0; i< row.size(); i++) {
			        model.addRow(row.get(i));
			        tableSize++;        	
			    }
			}
			});
		JButton addCarButton = new JButton("Add car");
		addCarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(true);			
			}
		});
		addCarButton.setBackground(new Color(30, 144, 255));
		addCarButton.setForeground(new Color(0, 0, 0));
		addCarButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		
		JButton removeCarButton = new JButton("Rent car");
		removeCarButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		removeCarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				   if(table.getSelectedRow() != -1) {
		               // remove selected row from the model
					   String req = "";
					   for(int i=0; i<table.getColumnCount(); i++) {
						   req += table.getValueAt(table.getSelectedRow(), i) + ",";		
					   }
					   System.out.println(req);
					   String[] token = req.split(",");
					   car.setCompany(token[0]);
					   car.setModel(token[1]);
					   car.setYear(Integer.parseInt(token[2]));
					   car.setPrice(Integer.parseInt(token[3]));
					   System.out.println(car);
		               model.removeRow(table.getSelectedRow());
		               tableSize--;
		               appModel.deleteCar(car);
				   }else{
			            JOptionPane.showMessageDialog(null, "Error");
				   }
			}
		});
		
		JButton updateCarButton = new JButton("Update car");
		updateCarButton.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		updateCarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				   updateFrame.setVisible(true);	
				   if(table.getSelectedRow() != -1) {
		               // update selected row from the model
					   String req = "";
					   for(int i=0; i<table.getColumnCount(); i++) {
						   req += table.getValueAt(table.getSelectedRow(), i) + ",";		
					   }
					   System.out.println(req);
					   String[] token = req.split(",");
					   car.setCompany(token[0]);
					   car.setModel(token[1]);
					   car.setYear(Integer.parseInt(token[2]));
					   car.setPrice(Integer.parseInt(token[3]));
					   System.out.println(car);
					   
				   }else{
			            JOptionPane.showMessageDialog(null, "Error");
				   }
			}
		});
		GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
		gl_buttonsPanel.setHorizontalGroup(
			gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonsPanel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonsPanel.createSequentialGroup()
							.addComponent(updateCarButton, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_buttonsPanel.createSequentialGroup()
							.addGroup(gl_buttonsPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(removeCarButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 163, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_buttonsPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(addCarButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(searchButton, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
									.addComponent(sortListButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addGap(17))))
		);
		gl_buttonsPanel.setVerticalGroup(
			gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sortListButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(addCarButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(removeCarButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(updateCarButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		buttonsPanel.setLayout(gl_buttonsPanel);
	
		searchBar = new JTextField();
		searchBar.setBackground(new Color(255, 250, 205));
		searchBar.setColumns(10);
		searchBar.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		JTextPane txtpnSearch = new JTextPane();
		txtpnSearch.setForeground(UIManager.getColor("List.selectionBackground"));
		txtpnSearch.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		txtpnSearch.setText("Search ");
		txtpnSearch.setBackground(new Color(255, 215, 0));
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnFilters = new JTextPane();
		txtpnFilters.setForeground(UIManager.getColor("List.selectionBackground"));
		txtpnFilters.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		txtpnFilters.setText("filters");
		txtpnFilters.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnWelcomToCar = new JTextPane();
		txtpnWelcomToCar.setForeground(UIManager.getColor("List.selectionBackground"));
		txtpnWelcomToCar.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
		txtpnWelcomToCar.setText("Car Rental app");
		txtpnWelcomToCar.setBackground(new Color(255, 215, 0));
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topPanel.createSequentialGroup()
							.addGap(31)
							.addComponent(txtpnSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnWelcomToCar, GroupLayout.PREFERRED_SIZE, 470, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnFilters, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
						.addComponent(filtersPanel, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_topPanel.createSequentialGroup()
							.addComponent(txtpnWelcomToCar, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_topPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtpnSearch, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
							.addGap(22))
						.addGroup(gl_topPanel.createSequentialGroup()
							.addComponent(txtpnFilters, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(filtersPanel, GroupLayout.PREFERRED_SIZE, 84, Short.MAX_VALUE)
							.addContainerGap())))
		);
		filtersPanel.setLayout(new MigLayout("", "[grow][][]", "[grow][][][][]"));
		
		companyRadioButton = new JRadioButton("company");
		filtersPanel.add(companyRadioButton, "cell 1 3");
		
		priceRadioButton = new JRadioButton("price");
		filtersPanel.add(priceRadioButton, "cell 1 2,aligny bottom");
		
		modelRadioButton = new JRadioButton("model");
		filtersPanel.add(modelRadioButton, "cell 0 2");
		
		yearRadioButton = new JRadioButton("year");
		filtersPanel.add(yearRadioButton, "cell 0 3");
		topPanel.setLayout(gl_topPanel);
		mainFrame.getContentPane().setLayout(groupLayout);
		
		ButtonGroup group = new ButtonGroup();
		group.add(companyRadioButton);
		group.add(priceRadioButton);
		group.add(modelRadioButton);
		group.add(yearRadioButton);
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 215, 0));
		
		JTextArea priceTextArea = new JTextArea();
		priceTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextPane txtpnCompany = new JTextPane();
		txtpnCompany.setText("company :");
		txtpnCompany.setForeground(new Color(0, 0, 0));
		txtpnCompany.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnCompany.setToolTipText("");
		txtpnCompany.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnModel = new JTextPane();
		txtpnModel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnModel.setText("model :");
		txtpnModel.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnYear = new JTextPane();
		txtpnYear.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnYear.setText("year :");
		txtpnYear.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnPrice = new JTextPane();
		txtpnPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnPrice.setText("price :");
		txtpnPrice.setBackground(new Color(255, 215, 0));
		
		JTextArea yearTextArea = new JTextArea();
		yearTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextArea modelTextArea = new JTextArea();
		modelTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextArea companyTextArea = new JTextArea();
		companyTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		frame.getContentPane().setLayout(new MigLayout("", "[115px,center][115px,center]", "[40px,center][40px,center][40px,center][40px,center][]"));
		frame.getContentPane().add(txtpnYear, "cell 0 2,grow");
		frame.getContentPane().add(txtpnPrice, "cell 0 3,grow");
		frame.getContentPane().add(txtpnModel, "cell 0 1,grow");
		frame.getContentPane().add(txtpnCompany, "cell 0 0,grow");
		frame.getContentPane().add(yearTextArea, "cell 1 2,grow");
		frame.getContentPane().add(modelTextArea, "cell 1 1,grow");
		frame.getContentPane().add(priceTextArea, "cell 1 3,grow");
		frame.getContentPane().add(companyTextArea, "cell 1 0,grow");
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Car car = new Car(companyTextArea.getText(), modelTextArea.getText(), 
						Integer.parseInt(yearTextArea.getText()), Integer.parseInt(priceTextArea.getText()));	
				appModel.saveCar(car);
				frame.setVisible(false);
				String[] rowData = new String[] {car.getCompany(), car.getModel(), 
						Integer.toString(car.getYear()), Integer.toString(car.getPrice())};
				model.addRow(rowData);
				tableSize++;
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.setBackground(new Color(65, 105, 225));
		frame.getContentPane().add(btnNewButton, "cell 0 4");
		frame.setBounds(100, 100, 269, 271);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		updateFrame = new JFrame();
		updateFrame.getContentPane().setBackground(new Color(255, 215, 0));
		
		JTextArea priceTextArea2 = new JTextArea();
		priceTextArea2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextPane txtpnCompany2 = new JTextPane();
		txtpnCompany2.setText("company :");
		txtpnCompany2.setForeground(new Color(0, 0, 0));
		txtpnCompany2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnCompany2.setToolTipText("");
		txtpnCompany2.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnModel2 = new JTextPane();
		txtpnModel2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnModel2.setText("model :");
		txtpnModel2.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnYear2 = new JTextPane();
		txtpnYear2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnYear2.setText("year :");
		txtpnYear2.setBackground(new Color(255, 215, 0));
		
		JTextPane txtpnPrice2 = new JTextPane();
		txtpnPrice2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		txtpnPrice2.setText("price :");
		txtpnPrice2.setBackground(new Color(255, 215, 0));
		
		JTextArea yearTextArea2 = new JTextArea();
		yearTextArea2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextArea modelTextArea2 = new JTextArea();
		modelTextArea2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JTextArea companyTextArea2 = new JTextArea();
		companyTextArea2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		updateFrame.getContentPane().setLayout(new MigLayout("", "[115px,center][115px,center]", "[40px,center][40px,center][40px,center][40px,center][]"));
		updateFrame.getContentPane().add(txtpnYear2, "cell 0 2,grow");
		updateFrame.getContentPane().add(txtpnPrice2, "cell 0 3,grow");
		updateFrame.getContentPane().add(txtpnModel2, "cell 0 1,grow");
		updateFrame.getContentPane().add(txtpnCompany2, "cell 0 0,grow");
		updateFrame.getContentPane().add(yearTextArea2, "cell 1 2,grow");
		updateFrame.getContentPane().add(modelTextArea2, "cell 1 1,grow");
		updateFrame.getContentPane().add(priceTextArea2, "cell 1 3,grow");
		updateFrame.getContentPane().add(companyTextArea2, "cell 1 0,grow");
		
		JButton updateButton = new JButton("Update");
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updatedCar = new Car(companyTextArea2.getText(), modelTextArea2.getText(), 
						Integer.parseInt(yearTextArea2.getText()), Integer.parseInt(priceTextArea2.getText()));	
				updateFrame.setVisible(false);
				String[] rowData = new String[] {car.getCompany(), car.getModel(), 
						Integer.toString(car.getYear()), Integer.toString(car.getPrice())};
				appModel.updateCar(car, updatedCar);
				model.setRowCount(0);
				row.clear();
				for(Car c : carList) {
					if(c.isEqual(car)) {
						row.add(new String[] {updatedCar.getCompany(), updatedCar.getModel(), 
								Integer.toString(updatedCar.getYear()), Integer.toString(updatedCar.getPrice())});
					}
					else {
						row.add(new String[] {c.getCompany(), c.getModel(), 
							Integer.toString(c.getYear()), Integer.toString(c.getPrice())});
					}
				}
				for (int i = 0; i< row.size(); i++) {
			        model.addRow(row.get(i));
			        tableSize++;        	
			    }
			}
		});
		updateButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		updateButton.setBackground(new Color(65, 105, 225));
		updateFrame.getContentPane().add(updateButton, "cell 0 4");
		updateFrame.setBounds(100, 100, 269, 271);
		updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
