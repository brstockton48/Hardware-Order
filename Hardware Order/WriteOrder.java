//AUTHOR: [Billy Stockton]
//COURSE: CPT 187
//PURPOSE: [The purpose of the WriteOrder Class is to write 
//the customers order selections to a new file]
//STARTDATE: [9/21/2020]

package edu.CPT187.stockton.exercise5;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class WriteOrder 
{
	//Non-Constant Class Attributes
	private boolean fileFoundFlag=false;
	private String masterFileName="";
	private int recordCount=0;

	//Non-Default Constructor
	WriteOrder(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}//End of non-default Constructor

	//Setters
	public void setWriteOrder(int borrowedItemID, String borrowedItemName, double borrowedItemPrice, 
			int borrowedQuantity, double borrowedOrderCost)
	{
		try
		{
			//this instantiation statement will attempt to open the file
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName,true));
			//if the file is found and opened successfully, the remaining try block code is executed
			//this printf statement writes the borrowed values to the file, separated by tabs
			filePW.printf("%n%d\t%s\t%f\t%d\t%f",borrowedItemID, borrowedItemName, borrowedItemPrice,
					borrowedQuantity, borrowedOrderCost);
			//set flag to "Found"
			fileFoundFlag=true;
			//Close the PrintWriter/File
			recordCount++;
			filePW.close();
		}
		catch (IOException ex)
		{
			//if the file is not found or opened, catch is executed which sets the flag to "not found"
			fileFoundFlag=false;
		}
	}
	//Getters
	//file found flag
	public boolean getFileFoundFlag()
	{
		return fileFoundFlag;
	}//End of getFileFoundFlag
	//Gets file Name
	public String getFileName()
	{
		return masterFileName;
	}//End of getFileName
	//Gets RecordCount
	public int getRecordCount()
	{
		return recordCount;
	}//End of getRecordCount

}//End of WriteOrder Supportive Class
