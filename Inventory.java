package Finance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * This class is a gui application for the FIFO and LIFO methods
 * in Financial Accounting.
 * 
 * @author Elvin Torres
 *
 */
public class Inventory extends JFrame{
	//fields,components and classes
	float costOfGoodsSold;
	float totalUnits, totalUnitsSold, merchandiseInv;
	ArrayList<Purchase> purchases;
	ArrayList<Sale> sales;
	JLabel purchaseL, saleL;
	JTextField purchaseT, saleT;
	JRadioButton FIFO, LIFO;
	JPanel main, buttonP, heading;
	JButton calculateB, exitB;
	DecimalFormat formatter = new DecimalFormat("##,###.##");

	public Inventory(){
		//sets up the frame
		super("FIFO & LIFO");

		//sets the look and feel of the gui to the windows os
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//builds the panels
		buildPanel();
		//initializes the fields
		costOfGoodsSold = 0;
		totalUnits = 0;
		totalUnitsSold = 0;
		merchandiseInv = 0;
		
		//adds the panels in the content pane
		setLayout(new BorderLayout());

		//registers action listeners
		calculateB.addActionListener(new BListener());
		exitB.addActionListener(new BListener());

		add(main, BorderLayout.CENTER);
		add(buttonP, BorderLayout.SOUTH);

		//packs the components
		pack();
		//sets the location of the window
		setLocation(300,300);
	}

	private void buildPanel() {
		//initializing labels
		purchaseL = new JLabel("# of Purchases");
		saleL = new JLabel("# of Sales");
		purchaseL.setHorizontalAlignment(JLabel.CENTER);
		saleL.setHorizontalAlignment(JLabel.CENTER);

		//initializing text fields
		purchaseT = new JTextField(10);
		saleT = new JTextField(10);

		//initializing radio buttons
		FIFO = new JRadioButton("FIFO");
		LIFO = new JRadioButton("LIFO");

		//grouping the radion buttons
		ButtonGroup g = new ButtonGroup();
		g.add(FIFO);
		g.add(LIFO);

		//initializing the panels
		main = new JPanel();
		main.setLayout(new GridLayout(0,2));

		buttonP = new JPanel();
		buttonP.setLayout(new FlowLayout(FlowLayout.CENTER));

		heading = new JPanel();
		heading.setLayout(new FlowLayout(FlowLayout.CENTER));

		//initializing the buttons
		calculateB = new JButton("Calculate");
		exitB = new JButton("Exit");

		//adds the components to the panels
		main.add(purchaseL);
		main.add(purchaseT);
		main.add(saleL);
		main.add(saleT);
		main.add(FIFO);
		main.add(LIFO);

		buttonP.add(calculateB);
		buttonP.add(exitB);


	}
	/**
	 * Inner action listener for the cal buttona and exit button
	 * 
	 * @author Elvin Torres
	 *
	 */
	private class BListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == calculateB){
				if(FIFO.isSelected() || LIFO.isSelected()){
					purchases = new ArrayList<Purchase>(10);
					sales = new ArrayList<Sale>(10);
					int numPurchases = Integer.parseInt(purchaseT.getText());
					int numSales = Integer.parseInt(saleT.getText());
					float u = 0;
					float c = 0;
					int d = 0;
					//----------------------PROMPTS THE USER FOR INFO---------------------
					for(int i = 0; i<numPurchases; i++){
						u = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Units","purchase #"+(i+1), JOptionPane.QUESTION_MESSAGE));
						c = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Cost per Unit", "purchase #"+(i+1), JOptionPane.QUESTION_MESSAGE));
						//d = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Date", "purchase #"+(i+1), JOptionPane.QUESTION_MESSAGE));

						purchases.add(new Purchase(u, c, d));
						totalUnits += u;
					}
					//prompts for the sales and stores them in the array list
					for(int i = 0; i<numSales; i++){
						u = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter the Units", "Sale#"+(i+1), JOptionPane.QUESTION_MESSAGE));
						//d = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Date", "Sale#"+(i+1), JOptionPane.QUESTION_MESSAGE));

						sales.add(new Sale(u, d));
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select A Method.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				//--------------------------------------------------------------------

				/*
				 * FIFO calculation
				 */
				if(FIFO.isSelected()){
					//calculates the total units and checks for the amount of units left (FIFO)
					for(int i = 0; i<sales.size(); i++){		//i is for the sale's index and j is for the purchases
						for(int j =0; j<purchases.size(); j++){
							//if the sale's units is less then the units available of the first in
							if(sales.get(i).getUnits() != 0){
								if(sales.get(i).getUnits()<purchases.get(j).getUnits()){
									//updates the inventory and counts the total units left and total units sold
									costOfGoodsSold += sales.get(i).getUnits() * purchases.get(j).getCPU();
									totalUnits -= sales.get(i).getUnits();
									totalUnitsSold += sales.get(i).getUnits();
									purchases.get(j).setUnits(purchases.get(j).getUnits() - sales.get(i).getUnits());
									sales.get(i).setUnits(0);
								}
								else if((sales.get(i).getUnits() > purchases.get(j).getUnits()) && purchases.get(j).getUnits() != 0){
									costOfGoodsSold += purchases.get(j).getUnits()*purchases.get(j).getCPU();
									totalUnits -= purchases.get(j).getUnits();
									totalUnitsSold += purchases.get(j).getUnits();	
									sales.get(i).setUnits(sales.get(i).getUnits() - purchases.get(j).getUnits());
									purchases.get(j).setUnits(0);
								}
								else if(sales.get(i).getUnits() == purchases.get(j).getUnits()){		//if the units sold is the same as the inventory available
									costOfGoodsSold += purchases.get(j).getUnits()*purchases.get(j).getCPU();
									totalUnits -= purchases.get(j).getUnits();
									totalUnitsSold += purchases.get(j).getUnits();	
									purchases.get(j).setUnits(0);
									sales.get(i).setUnits(0);
								}
							}
							else{
								break;
							}
						}
					}
				}
				//////////////////// LIFO Calculation
				else if(LIFO.isSelected()){
					for(int i = 0; i<sales.size(); i++){		//i is for the sale's index and j is for the purchases
						for(int j =purchases.size()-1; j>-1; j--){
							//if the sale's units is less then the units available of the first in
							if(sales.get(i).getUnits() != 0){
								if(sales.get(i).getUnits()<purchases.get(j).getUnits()){
									//updates the inventory and counts the total units left and total units sold
									costOfGoodsSold += sales.get(i).getUnits() * purchases.get(j).getCPU();
									totalUnits -= sales.get(i).getUnits();
									totalUnitsSold += sales.get(i).getUnits();
									purchases.get(j).setUnits(purchases.get(j).getUnits() - sales.get(i).getUnits());
									sales.get(i).setUnits(0);
								}
								else if((sales.get(i).getUnits() > purchases.get(j).getUnits()) && purchases.get(j).getUnits() != 0){
									costOfGoodsSold += purchases.get(j).getUnits()*purchases.get(j).getCPU();
									totalUnits -= purchases.get(j).getUnits();
									totalUnitsSold += purchases.get(j).getUnits();	
									sales.get(i).setUnits(sales.get(i).getUnits() - purchases.get(j).getUnits());
									purchases.get(j).setUnits(0);
								}
								else if(sales.get(i).getUnits() == purchases.get(j).getUnits()){		//if the units sold is the same as the inventory available
									costOfGoodsSold += purchases.get(j).getUnits()*purchases.get(j).getCPU();
									totalUnits -= purchases.get(j).getUnits();
									totalUnitsSold += purchases.get(j).getUnits();	
									purchases.get(j).setUnits(0);
									sales.get(i).setUnits(0);
								}
							}
							else{
								break;
							}
						}
					}
				}
				//calculates the remaining merchandise inventory
				for(int i = 0; i<purchases.size(); i++){
					if(purchases.get(i).getUnits() != 0){
						merchandiseInv += purchases.get(i).getUnits()*purchases.get(i).getCPU();
						//To debug the inventory units
						//System.out.println("Purchase #"+(i+1)+" Units left:" + purchases.get(i).getUnits());
					}
				}
				if(FIFO.isSelected() || LIFO.isSelected())
					JOptionPane.showMessageDialog(null, "Total units remaining:  "+totalUnits +"\nUnits Sold:  "+totalUnitsSold+"\nMerchandise Inventory:  $"+formatter.format(merchandiseInv)+"\nCost of Goods Sold:  $"+formatter.format(costOfGoodsSold));
					//resets everything
					purchases = null;
					sales = null;
					costOfGoodsSold = 0;
					totalUnits = 0;
					totalUnitsSold = 0;
					merchandiseInv = 0;
			}
			else if(e.getSource() == exitB){
				System.exit(0);
			}
		}

	}
	public static void main(String[] arg){
		new Inventory();
	}
}
