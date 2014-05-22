import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;

public class Printer implements Printable, ActionListener
	{
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException
		{
		if (page > 0)
			{ /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
			}
		
		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Now we perform our rendering */
		g.drawString("Hello world!", 100, 100);
		g.drawString("Tabbed over text", 200, 100);
		g.drawString("Second line of text", 100, 110);
		g.drawString("Text after a space break of 50", 100, 160);

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
		}

	public void actionPerformed(ActionEvent e)
		{
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok)
			{
			try
				{
				job.print();
				} catch (PrinterException ex)
				{
				/* The job did not successfully complete */
				}
			}
		}

	public static void main(String args[])
		{
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		JFrame f = new JFrame("Printer");
		f.setLocationRelativeTo(null);
		f.addWindowListener(new WindowAdapter()
			{
			public void windowClosing(WindowEvent e)
				{
				System.exit(0);
				}
			});
		JButton printButton = new JButton("Print");
		printButton.addActionListener(new Printer());
		f.add("Center", printButton);
		f.pack();
		f.setVisible(true);
		}
	}
