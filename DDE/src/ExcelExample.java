/*
 * Copyright 2009 www.pretty-tools.com. All rights reserved.
 */

import com.pretty_tools.dde.ClipboardFormat;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.DDEMLException;
import com.pretty_tools.dde.client.DDEClientConversation;

/**
 * Excel Example.
 *
 * @author Alexander Kozlov
 */
public class ExcelExample
{
    public static void main(String[] args)
    {
        try
        {
            // DDE client
            final DDEClientConversation conversation = new DDEClientConversation();
            // We can use UNICODE format if server prefers it
            //conversation.setTextFormat(ClipboardFormat.CF_UNICODETEXT);

            conversation.setTimeout(3000);
            // Establish conversation with opened and active workbook
            conversation.connect("Excel", "Sheet1");
            // if you have several opened files, you can establish conversation using file path
            //conversation.connect("Excel", "C:\\Book1.xlsx");
            // or you can also specify Sheet
            //conversation.connect("Excel", "C:\\[Book1.xlsx]Sheet2");
            try
            {
                // Requesting A1 value
                System.out.println("A1 value: " + conversation.request("R1C1"));
                // Changing cell A1 value to "We did it!"
                conversation.poke("R1C1", "We did it!");
                conversation.poke("R2C2", "We did it again!".getBytes(), ClipboardFormat.CF_TEXT);
                // Fill several cells of the same row, using \t as separator
                conversation.poke("R4", "Fill\tthe\trow");
                // Fill several cells of the same column, using \n as separator
                conversation.poke("C4", "Fill\nthe\ncolumn");
                // Fill several cells with matrix, using \t as column separator and \n as row separator
                conversation.poke("R5C5:R7C7", "1\t2\t3\n4\t5\t6\n7\t8\t9");
                // Run macro with name Macro1
                //conversation.execute("[run(\"Macro1\")]");
                // Sending "close()" command
                conversation.execute("[close()]");
                // or we can use byte array to send command
                //conversation.execute("[close()]\0".getBytes());
            }
            finally
            {
                conversation.disconnect();
            }
        }
        catch (DDEMLException e)
        {
            System.out.println("DDEMLException: 0x" + Integer.toHexString(e.getErrorCode()) + " " + e.getMessage());
        }
        catch (DDEException e)
        {
            System.out.println("DDEClientException: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }
}