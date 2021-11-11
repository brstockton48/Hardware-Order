//AUTHOR: [Billy Stockton]
//COURSE: CPT 187
//PURPOSE: [The purpose of the Order Class is to calculate 
//and provide details for the hardware order reports
//STARTDATE: [9/21/2020]
package edu.CPT187.stockton.exercise5;

public class Order 
{
	//Class Constants
	private final double TAX_RATE = .075;

	//Non-Constant Class Attributes
	private int discountType=0;
	private int itemID=0;
	private String itemName="";
	private double itemPrice=0.0;
	private String discountName="";
	private double discountRate=0.0;
	private int howMany=0;
	private int lastItemSelectedIndex=0;
	private String prizeName="";

	//The Order Class Constructor
	public Order()
	{
	}//End of Order Class Constructor
	//Setters
	//Set Index
	public void setLastItemSelectedIndex(int borrowedSearchIndex)
	{
		lastItemSelectedIndex = borrowedSearchIndex;
	}//End of setLastItemSelectedIndex
	//Sets itemName
	public void setItemID(int[] borrowedItemIDs)
	{
		itemID = borrowedItemIDs[lastItemSelectedIndex];
	}//End of setItemID
	public void setItemName(String[] borrowedItemNames)
	{
		itemName = borrowedItemNames[lastItemSelectedIndex];
	}//End of setItemName
	//Sets itemPrice
	public void setItemPrice(double[] borrowedItemPrices)
	{
		itemPrice = borrowedItemPrices[lastItemSelectedIndex];
	}//End of setItemPrice
	//Sets number of items ordered based on user input from menu
	public void setHowMany(String borrowedHowMany)
	{
		howMany=Integer.parseInt(borrowedHowMany);
	}//End of setHowMany
	//sets discount type based on user input from menu
	public void setDiscountType(char borrowedMenuSelection)
	{
		discountType = borrowedMenuSelection - 'A';
	}//End of setDiscountType
	//Sets Discount Name
	public void setDiscountName(String[] borrowedDiscountNames)
	{
		discountName = borrowedDiscountNames[discountType];
	}//End of setDiscountName
	//sets discountRate based on user input from menu
	public void setDiscountRate(double[] borrowedDiscountRate)
	{
		discountRate = borrowedDiscountRate[discountType];
	}//End of setDiscountRate
	//Sets prize name
	public void setPrizeName(String[]borrowedPrizeNames, int borrowedPrizeIndex)
	{
		prizeName = borrowedPrizeNames[borrowedPrizeIndex];
	}//End of Set Prize Name
	//adjusts stock based on passed object from inventory class
	public void setDecreaseInStock(Inventory borrowedInventoryObject)
	{
		borrowedInventoryObject.setReduceStock(howMany);
	}//End of setDecreaseInStock
	//End of Setters
	//Getters
	//Returns in stock counts
	public int getInStockCount(int[] borrowedInStockCounts)
	{
		return borrowedInStockCounts[lastItemSelectedIndex];
	}//End of getInStockCount
	//Return Item ID
	public int getItemID()
	{
		return itemID;
	}//End of getItemID
	//Returns array of item names
	public String getItemName()
	{
		return itemName;
	}//End of getItemName
	//Returns item price
	public double getItemPrice()
	{
		return itemPrice;
	}//End of getItemPrice
	//returns quantity of item ordered
	public int getHowMany()
	{
		return howMany;
	}//End of getHowMany
	//returns discount name
	public String getDiscountName()
	{
		return discountName;
	}//End of getDiscountName
	//Returns discount rate
	public double getDiscountRate()
	{
		return discountRate;
	}//End of getDiscountRate
	//returns calculated discount amounts
	public double getDiscountAmt()
	{
		return discountRate*itemPrice;
	}//End of getDiscountAmt
	//returns calculated discount price
	public double getDiscountPrice()
	{
		return itemPrice-getDiscountAmt();
	}//End of getDiscountPrice
	//returns prize name
	public String getPrizeName()
	{
		return prizeName;
	}//End of getPrizeName
	//returns calculated sub total
	public double getSubTotal()
	{
		return howMany*getDiscountPrice();
	}//End of getSubTotal
	//returns tax rate
	public double getTaxRate()
	{
		return TAX_RATE;
	}//End of getTaxRate
	//returns calculated tax amount
	public double getTaxAmt()
	{
		return getSubTotal()*TAX_RATE;
	}//End of getTaxAmt
	//returns calculated total cost
	public double getTotalCost()
	{
		return getSubTotal() + getTaxAmt();
	}//End of getTotalCost
}//End of Order Supportive Class
