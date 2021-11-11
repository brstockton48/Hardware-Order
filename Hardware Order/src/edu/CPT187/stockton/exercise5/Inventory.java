//AUTHOR: [Billy Stockton]
//COURSE: CPT 187
//PURPOSE: [The purpose of the Inventory Class is to 
//is to keep track and manage the inventory from the accessed
//accessed file]
//STARTDATE: [9/21/2020]
package edu.CPT187.stockton.exercise5;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Inventory 
{
	//Class Constants
	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.25, 0.15, 0.0};
	private final String[] PRIZE_NAMES = {"Laptop", "Ipad", "Big Screen TV"};
	private final int MAX_RECORDS = 45;
	private final int NOT_FOUND = -1;
	private final int ONE = 1;
	private final int RESET_VALUE = 0;

	//Non-Constant Class Attributes
	private int[] itemIDs = new int [MAX_RECORDS];
	private String[] itemNames = new String [MAX_RECORDS];
	private double[] itemPrices = new double [MAX_RECORDS];
	private int[] orderQuantities = new int [MAX_RECORDS];
	private double[] orderTotals = new double [MAX_RECORDS];
	private int[] inStockCounts = new int [MAX_RECORDS];
	private int itemSearchIndex=0;
	private int recordCount=0;

	//declare and initialize the Random object for class support
	private Random prizeGenerator = new Random();

	//The inventory class constructor
	public Inventory()
	{
	}//End of Inventory Class Constructor
	//Setters
	//Reduces stock by quantity sold
	public void setReduceStock(int borrowedHowMany)
	{
		inStockCounts[itemSearchIndex] = inStockCounts[itemSearchIndex] - borrowedHowMany;
	}//End of setReduceStock
	//Try/Catch structure to assign array values from file
	public void setLoadItems(String borrowedFileName)
	{
		try
		{
			//Attempts to open file
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			recordCount = RESET_VALUE;//Record count set to 0
			//.hasNext will test for the End of File
			//recordCount will be tested against the maximum number of records
			while (infile.hasNext() == true && recordCount< MAX_RECORDS)
			{
				//These assignment statements assign field values to array elements
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				inStockCounts[recordCount] = infile.nextInt();
				recordCount++;
			}//End of while for field assignment
			//Close the Scanner/File
			infile.close();	
			setBubbleSort();
		}
		catch(IOException ex)
		{
			//if the file is not found or opened, catch is executed; which sets the flag to "not found"
			recordCount = NOT_FOUND;
		}//End of Catch
	}//End of setLoadItems
	//Try/Catch structure to assign array values from file
	public void setLoadItems(String borrowedFileName, int borrowedSize)
	{
		try
		{
			//Attempts to open file
			Scanner infile = new Scanner(new FileInputStream(borrowedFileName));
			recordCount = RESET_VALUE;//Record count set to 0
			//.hasNext will test for the End of File
			//recordCount will be tested against the maximum number of records
			while (infile.hasNext() == true && recordCount< borrowedSize && borrowedSize<MAX_RECORDS)
			{
				//These assignment statements assign field values to array elements
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				orderQuantities[recordCount] = infile.nextInt();
				orderTotals[recordCount] = infile.nextDouble();
				//inStockCounts[recordCount] = infile.nextInt();
				recordCount++;
			}//End of while for field assignment
			//Close the Scanner/File
			infile.close();	
		}
		catch(IOException ex)
		{
			//if the file is not found or opened, catch is executed; which sets the flag to "not found"
			recordCount = NOT_FOUND;
		}//End of Catch
	}//End of setLoadItems
	//Sets search index
	public void setSearchIndex(int borrowedID)
	{
		itemSearchIndex = getSearchResults(borrowedID);
	}//End of setSearchIndex
	//Sorter
	public void setBubbleSort()
	{
		int localIndex=0;
		int localLast=0;
		int ZERO=0;
		boolean localSwap=false;
		localLast=recordCount-ONE;
		while(localLast>ZERO)
		{
			localIndex=ZERO;
			localSwap=false;
			while(localIndex<localLast)
			{
				if(itemIDs[localIndex]>itemIDs[localIndex+ONE])
				{
					setSwapArrayElements(localIndex);
					localSwap=true;
				}
				localIndex++;
			}//End of Inner while
			if(localSwap==false)
			{
				localLast=ZERO;
			}
			else
			{
				localLast--;
			}
		}//End of Outer While
	}//End of setBubbleSort
	//Swaps array elements for all field arrays
	public void setSwapArrayElements(int borrowedIndex)
	{
		int localItemID=0;
		String localItemName="";
		double localItemPrice=0.0;
		int localItemCount=0;

		localItemID = itemIDs[borrowedIndex];
		itemIDs[borrowedIndex]=itemIDs[borrowedIndex+ONE];
		itemIDs[borrowedIndex+ONE]=localItemID;

		localItemName = itemNames[borrowedIndex];
		itemNames[borrowedIndex]=itemNames[borrowedIndex+ONE];
		itemNames[borrowedIndex+ONE]=localItemName;

		localItemPrice = itemPrices[borrowedIndex];
		itemPrices[borrowedIndex]=itemPrices[borrowedIndex+ONE];
		itemPrices[borrowedIndex+ONE]=localItemPrice;

		localItemCount = inStockCounts[borrowedIndex];
		inStockCounts[borrowedIndex]=inStockCounts[borrowedIndex+ONE];
		inStockCounts[borrowedIndex+ONE]=localItemCount;
	}//End of setSwapArrayElements
	//End of Setters
	//Returns in Stock Counts
	public int[] getInStockCounts()
	{
		return inStockCounts;
	}//End of getInStockCounts
	//Returns the array of Item IDs
	public int[] getItemIDs()
	{
		return itemIDs;
	}//End of getItemIDs
	//Returns the array of item names
	public String[] getItemNames()
	{
		return itemNames;
	}//End of getItemNames
	//Returns the array of item prices	
	public double[] getItemPrices()
	{
		return itemPrices;
	}//End of getItemPrices
	//Returns array of Discount Names
	public String[] getDiscountNames()
	{
		return DISCOUNT_NAMES;
	}//End of getDiscountNames
	//Returns array of Discount Rates
	public double[] getDiscountRates()
	{
		return DISCOUNT_RATES;
	}//End of getDiscountRates
	//Returns Order Quantities
	public int[] getOrderQuantities()
	{
		return orderQuantities;
	}//End of getOrderQuantities
	//Returns Order Totals
	public double[] getOrderTotals()
	{
		return orderTotals;
	}//End of getOrderTotals
	//Returns array of Prize Names
	public String[] getPrizeNames()
	{
		return PRIZE_NAMES;
	}//End of getPrizeNames
	//Returns a random prize from PRIZE_NAMES array
	public int getRandomNumber()
	{
		return prizeGenerator.nextInt(PRIZE_NAMES.length);
	}//End of getRandomNumber
	//Returns Max Record Integer
	public int getMaxRecords()
	{
		return MAX_RECORDS;
	}//End of GetMaxRecords
	//Returns Item Search Index
	public int getItemSearchIndex()
	{
		return itemSearchIndex;
	}//End of getItemSearchIndex
	//Returns record count
	public int getRecordCount()
	{
		return recordCount;
	}//End of getRecordCount
	//Calculated Grand Total of all orders
	public double getGrandTotal()
	{
		int localIndex=0;
		double localGrandTotal=0.0;
		while(localIndex<recordCount)
		{
			localGrandTotal = localGrandTotal+orderTotals[localIndex];
			localIndex++;
		}
		return localGrandTotal;
	}//End of getGrandTotal
	//Returns search results
	public int getSearchResults(int borrowedBorrowedID)
	{
		int TWO=2;
		int localFirst=0;
		int localMid=0;
		int localLast=0;
		boolean localFound=false;
		localLast=recordCount-ONE;
		while(localFirst <= localLast && localFound == false)
		{
			localMid=(localFirst+localLast)/TWO;
			if(itemIDs[localMid]==borrowedBorrowedID)
			{
				localFound=true;
			}
			else if (itemIDs[localMid]<borrowedBorrowedID)
			{
				localFirst = localMid+ONE;
			}
			else
			{
				localLast = localMid-ONE;
			}
			//localFound=false;
		}//End of While
		if(localFound==false)
		{
			localMid=NOT_FOUND;
		}
		return localMid;
	}//End of getSearchResults

}//End of Inventory Supportive Class
