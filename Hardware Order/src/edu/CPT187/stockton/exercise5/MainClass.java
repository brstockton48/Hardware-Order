//AUTHOR: [Billy Stockton]
//COURSE: CPT 187
//PURPOSE: [To track each customer's hardware order and
//provide an order report for each item and a final report]
//A random prize will be given away with each order.]
//STARTDATE: [9/21/2020]
package edu.CPT187.stockton.exercise5;
import java.util.Scanner;

public class MainClass 
{
	public static final char[] MENU_CHARS = {'A', 'B','Q'};
	public static final String[] MENU_OPTIONS = {"Load Inventory", "Create Order", "Quit"};
	public static final char[] SUB_MENU_CHARS = {'A', 'B', 'C', 'D'};	
	public static final String INVENTORY_FILE_NAME = "MasterOrderFile.dat";

	public static void main(String[] args) 
	{
		//declare and initialize Scanner 
		Scanner input = new Scanner (System.in);

		//local variables
		String userName = "";
		char menuSelection = ' ';

		//Create one instance from the supportive class using an instantiation statement
		Order currentOrder = new Order();	
		Inventory currentInventory = new Inventory();
		//instantiation statement for non-default constructor
		WriteOrder orders = new WriteOrder(INVENTORY_FILE_NAME);
		displayWelcomeBanner();
		userName=getUserName(input);
		menuSelection=validateMainMenu(input);
		while(menuSelection != 'Q')
		{
			if(menuSelection=='A') 
			{
				currentInventory.setLoadItems(getFileName(input));
				if(currentInventory.getRecordCount()<=0)
				{
					displayNotOpen();
				}
				else 
				{
					displayRecordReport(currentInventory.getRecordCount());
				}
			}
			else
			{
				currentInventory.setSearchIndex(validateSearchValue(input));
				if(currentInventory.getItemSearchIndex()<0)
				{
					displayNotFound();
				}
				else
				{
					currentOrder.setLastItemSelectedIndex(currentInventory.getItemSearchIndex());
					currentOrder.setItemID(currentInventory.getItemIDs());
					currentOrder.setItemName(currentInventory.getItemNames());
					currentOrder.setItemPrice(currentInventory.getItemPrices());
					currentOrder.setHowMany(validateHowMany(input));
					if(currentOrder.getInStockCount(currentInventory.getInStockCounts())<
							currentOrder.getHowMany())
					{
						displayOutOfStock();
					}
					else
					{
						currentOrder.setDiscountType(validateDiscountMenu(input, currentInventory.getDiscountNames(), 
								currentInventory.getDiscountRates()));
						currentOrder.setDiscountName(currentInventory.getDiscountNames());
						currentOrder.setDiscountRate(currentInventory.getDiscountRates());
						currentOrder.setDecreaseInStock(currentInventory);
						currentOrder.setPrizeName(currentInventory.getPrizeNames(), currentInventory.getRandomNumber());
						orders.setWriteOrder(currentOrder.getItemID(), currentOrder.getItemName(),
								currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getTotalCost());
						if(currentOrder.getDiscountRate()>0.0)
						{
							displayOrderReport(userName, currentOrder.getItemName(),currentOrder.getItemPrice(),
									currentOrder.getHowMany(), currentOrder.getDiscountName(), currentOrder.getDiscountRate(),
									currentOrder.getDiscountAmt(),currentOrder.getDiscountPrice(), currentOrder.getSubTotal(),
									currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), 
									currentOrder.getPrizeName(),currentOrder.getInStockCount(currentInventory.getInStockCounts()));
						}
						else
						{
							displayOrderReport(userName, currentOrder.getItemName(),currentOrder.getItemPrice(),
									currentOrder.getHowMany(),currentOrder.getSubTotal(), currentOrder.getTaxRate(), 
									currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName(),
									currentOrder.getInStockCount(currentInventory.getInStockCounts()));
						}//End of Final Else
					}
				}
			}
			menuSelection=validateMainMenu(input);//LCV
		}//End of Outer While
		if(orders.getRecordCount()>0)
		{
			currentInventory.setLoadItems(orders.getFileName(),orders.getRecordCount());
			displayFinalReport(currentInventory.getItemIDs(), currentInventory.getItemNames(), currentInventory.getItemPrices(),
					currentInventory.getOrderQuantities(), currentInventory.getOrderTotals(), currentInventory.getRecordCount(),
					currentInventory.getGrandTotal());
		}
		displayFarewellMessage();

		//Close Scanner
		input.close();
	}//End of Main Method
	//Void Methods
	//This void method displays the welcome banner
	public static void displayWelcomeBanner()
	{
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.println("             Welcome to Awesome Hardware!");
		System.out.println("       We have a variety of product to choose from");
		System.out.println("  Simply follow the menu prompts and order by item number");
		System.out.println("         Let us know if you have any Questions");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
	}//end of welcome banner
	//This void method displays the main menu
	public static void displayMainMenu()
	{
		int localIndex=0;
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("MAIN MENU");
		while(localIndex<MENU_OPTIONS.length)
		{
			System.out.printf("%s%s%s%s%n","",MENU_CHARS[localIndex]," for ",MENU_OPTIONS[localIndex]);
			localIndex++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of Main Menu Display
	//Displays record report
	public static void displayRecordReport(int borrowedRecordCount)
	{
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RECORD REPORT");
		System.out.printf("%d%s%n",borrowedRecordCount," records processed");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of displayRecordReport
	//Displays file error when file name was not found or could not be opened
	public static void displayNotOpen()
	{
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("FILE ERROR");
		System.out.println("The file named was not found or could not be opened");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of displayNotOpen
	//Displays error when search value entered is not found
	public static void displayNotFound()
	{
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("NOT FOUND ERROR");
		System.out.println("The search value entered was not found");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of displayNotFound
	//This void menu displays out of stock message when number selected
	//is greater than inventory on hand
	public static void displayOutOfStock()
	{
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("OUT OF STOCK ERROR");
		System.out.println("The quantity entered is greater than the quantity in stock");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of out of stock display
	//This void method displays the discount menu
	public static void displayDiscountMenu(String[]borrowedDiscountNames, double[]borrowedDiscountRates)
	{
		int localIndex=0;
		System.out.printf("%n%s%n","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("DISCOUNT MENU");
		while(localIndex<borrowedDiscountNames.length)
		{
			System.out.printf("%c%s%-25s%5.1f%s%n",SUB_MENU_CHARS[localIndex]," for ",
					borrowedDiscountNames[localIndex],borrowedDiscountRates[localIndex]*100," %");
			localIndex++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//End of Discount Menu Display
	//This void method displays the order report when there is no discount
	public static void displayOrderReport(String userName, String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, 
			double borrowedSubTotal, double borrowedTaxRate, double borrowedTaxAmt, double borrowedTotalCost, 
			String borrowedPrizeName, int borrowedInStockCounts)
	{
		System.out.printf("%n%s%n","~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.println("ORDER REPORT");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.printf("%-25s%-9s%n","Customer Name:",userName);
		System.out.printf("%n%-25s%-25s%n","Item Name:",borrowedItemName,"");
		System.out.printf("%-25s%-1s%10.2f%n","Item Price:","$",borrowedItemPrice,"");
		System.out.printf("%n%-32s%1d%n","Quantity:",borrowedHowMany,"");
		System.out.printf("%n%-25s%-1s%10.2f%n","Subtotal:","$",borrowedSubTotal,"");
		System.out.printf("%-31s%4.1f%2s%n","Tax Rate:",borrowedTaxRate*100,"%");
		System.out.printf("%-25s%-1s%10.2f%n","Tax Amount:","$",borrowedTaxAmt,"");
		System.out.printf("%-25s%-1s%10.2f%n","Order Total:","$",borrowedTotalCost,"");
		System.out.printf("%n%-25s%-25s%n","Prize:",borrowedPrizeName,"");
		System.out.printf("%n%s%d%s%s%s%n","Buy more now: Only ",borrowedInStockCounts," ",borrowedItemName," left in-stock!");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
	}//End of Order Report Display
	//Overloaded Method for order report when there is a discount 
	public static void displayOrderReport(String userName, String borrowedItemName, double borrowedItemPrice, 
			int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate, double borrowedDiscountAmt, 
			double borrowedDiscountPrice, double borrowedSubTotal, double borrowedTaxRate, double borrowedTaxAmt, 
			double borrowedTotalCost, String borrowedPrizeName, int borrowedInStockCounts)
	{
		System.out.printf("%n%s%n","~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.println("ORDER REPORT");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.printf("%-25s%-9s%n","Customer Name:",userName);
		System.out.printf("%n%-25s%-25s%n","Item Name:",borrowedItemName,"");
		System.out.printf("%-25s%-1s%10.2f%n","Item Price:","$",borrowedItemPrice,"");
		System.out.printf("%n%-25s%-25s%n","Discount Name: ",borrowedDiscountName,"");
		System.out.printf("%-31s%4.1f%2s%n","Discount Rate:",borrowedDiscountRate*100,"%");
		System.out.printf("%-25s%-1s%10.2f%n","Discount Amount:","$",borrowedDiscountAmt,"");
		System.out.printf("%-25s%-1s%10.2f%n","Discount Price:","$",borrowedDiscountPrice,"");
		System.out.printf("%n%-32s%1d%n","Quantity:",borrowedHowMany,"");
		System.out.printf("%n%-25s%-1s%10.2f%n","Subtotal:","$",borrowedSubTotal,"");
		System.out.printf("%-31s%4.1f%2s%n","Tax Rate:",borrowedTaxRate*100,"%");
		System.out.printf("%-25s%-1s%10.2f%n","Tax Amount:","$",borrowedTaxAmt,"");
		System.out.printf("%-25s%-1s%10.2f%n","Order Total:","$",borrowedTotalCost,"");
		System.out.printf("%n%-25s%-25s%n","Prize:",borrowedPrizeName,"");
		System.out.printf("%n%s%d%s%s%s%n","Buy more now: Only ",borrowedInStockCounts," ",borrowedItemName," left in-stock!");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
	}//End of Order Report Display
	//This void method displays the final report
	public static void displayFinalReport(int[] borrowedItemIDs, String[] borrowedItemNames, double[] borrowedItemPrices,
			int[] borrowedOrderQuantities, double[] borrowedOrderTotals, int borrowedRecordCount, double borrowedGrandTotal)
	{
		int localIndex=0;
		System.out.printf("%n%s%n","~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.println("FINAL REPORT");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.printf("%-6s%-26s%-12s%-8s%-8s"," ID","NAME","PRICE","QTY","TOTAL");
		while(localIndex<borrowedRecordCount)
		{
			System.out.printf("%n%3d%-3s%-26s%-2s%5.2f%5s%2d%6s%-2s%5.2f",borrowedItemIDs[localIndex]," ",
					borrowedItemNames[localIndex],"$",borrowedItemPrices[localIndex],"",borrowedOrderQuantities[localIndex],"",
					"$",borrowedOrderTotals[localIndex]);
			localIndex++;
		}	
		System.out.printf("%n%n%s","GRAND TOTAL");
		System.out.printf("%n%-4s%-6.2f%n","$",borrowedGrandTotal);
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");		
	}//End of Final Report Display		
	//This void method displays the farewell message
	public static void displayFarewellMessage()
	{
		System.out.printf("%n%s%n","~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
		System.out.println("     We hope you found everything you were looking for");
		System.out.println("       Your satisfaction is our number one priority");
		System.out.println("               Have a great evening!");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~~");
	}//End of Farewell Message
	//End of Void Methods
	//VR Methods
	//This VR method returns user name
	public static String getUserName(Scanner borrowedInput)
	{
		String localUserName="";	
		System.out.println("Please enter your first name ");
		localUserName=borrowedInput.next();
		return localUserName;		
	}//End of getUserName
	//This VR method validates Main Menu
	public static char validateMainMenu(Scanner borrowedInput)
	{
		char localSelection = ' ';
		displayMainMenu();
		System.out.println("Enter your selection here ");
		localSelection=borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection !='A' && localSelection !='B' && localSelection !='Q')
		{
			System.out.println("That was an invalid selection, please try again");
			displayMainMenu();
			localSelection=borrowedInput.next().toUpperCase().charAt(0);
		}
		return localSelection;			
	}//end of validateMainMenu
	//This VR method returns the user entered file name
	public static String getFileName(Scanner borrowedInput)
	{
		String localFileName="";
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Enter the file name with extension (i.e. file.txt): ");
		localFileName=borrowedInput.next();
		return localFileName;		
	}//End of getFileName
	//This VR method validates search value
	public static int validateSearchValue(Scanner borrowedInput)
	{
		int localSearchValue=0;
		System.out.println("Enter the search value ");
		localSearchValue = borrowedInput.nextInt();
		while(localSearchValue<0)
		{
			System.out.println("That was an invalid selection, please try again");
			localSearchValue = borrowedInput.nextInt();
		}
		return localSearchValue;
	}//End of validateSearchValue
	//This VR method validates howMany
	public static String validateHowMany(Scanner borrowedInput)
	{
		String localHowMany="";
		System.out.println("Please enter quantity ");
		localHowMany = borrowedInput.next();
		while(Integer.parseInt(localHowMany)<=0)
		{
			System.out.println("That was an invalid selection, please try again");
			localHowMany = borrowedInput.next();
		}
		return localHowMany;
	}//End of validateHowMany
	//This VR method validates Discount Menu
	public static char validateDiscountMenu(Scanner borrowedInput, String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		char localSelection = 0;
		displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
		System.out.println("Enter your selection here ");
		localSelection=borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection !='A' && localSelection !='B' && localSelection !='C')
		{
			System.out.println("That was an invalid selection, please try again");
			displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
			localSelection=borrowedInput.next().toUpperCase().charAt(0);
		}
		return localSelection;	
	}//End of validateDiscountMenu

}//End of MainClass
