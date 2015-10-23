import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFParseException;
import com.sun.pdfview.PagePanel;

public class PatternMergerGui {

	static JFrame mainFrame = new JFrame("PatternMerger");
	static PagePanel panel = new PagePanel();
	JButton merge;
	private static int endPage;
	private static boolean lastrowfirst = false;
	private static BufferedImage finalImage;
	private static PDFFile pdffile;
	private static  Rectangle rect;
	private static RandomAccessFile raf;
	static JLabel picLabel;

 
	static String filename; 
	static int overlapSides;
	static int overlapTopBottom;
	static int numPagesinRow = 1;
	static int startPage;
	static private int skipPages;
	private static int numPages;
	private static int numRows;
	static Values values = new Values();

	public void creatGUI(){

		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(1200, 800);

		//Menüleiste
		JMenuBar menueleiste = new JMenuBar();

		
		values.buttons(this);

		menueleiste.setBorder(new LineBorder(Color.red));

		mainFrame.add(values, BorderLayout.PAGE_START);

		picLabel  = new JLabel(new ImageIcon());

		mainFrame.add(picLabel,BorderLayout.CENTER);

		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1, 2));

		JButton button = new JButton("Zusammenkleben");
		inner.add(button);
		button.addActionListener(new CombineActionListener(mainFrame));
		JButton save = new JButton("Speichern");
		inner.add(save);
		save.addActionListener(new SaveActionListener(mainFrame));
		mainFrame.add(inner,BorderLayout.PAGE_END);
		//mainFrame.setJMenuBar(menueleiste);
		mainFrame.setVisible(true);


	}

	private static void combine() {
		
		if(numPagesinRow == 1){
			int ok = okcancel("Es ist nur eine Seite pro Reihe angegeben, trotzdem fortfahren?" );
			if(ok != 0){
				return;
			}
		}
	
		try {


			int x = 0;

			int y = 0;
			if(lastrowfirst){
				y=finalImage.getHeight()-rect.height;
			}
			int lstartPage = startPage;
			for(int i = 0; i < numPages; i++){

				for(int j = 0; j < numPagesinRow; j++){
					if(lstartPage > numPages || startPage > endPage){
						break;
					}

					PDFPage page=pdffile.getPage(lstartPage);

			

					//System.out.println("Using Page " + page.getPageNumber() + " start " + lstartPage + " " + numPages);
					Image img = page.getImage(
							rect.width, 
							rect.height, 
							rect, 
							null, 
							true, 
							true 
							);

					picLabel.setIcon(new ImageIcon(img));

					BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
					Graphics g = bufferedImage.createGraphics();

					g.drawImage(img, 0, 0, null);
					g.dispose();
					

					finalImage.setRGB(x, y, rect.width, rect.height, 
							bufferedImage.getRGB(0, 0, rect.width, rect.height, null, 0, bufferedImage.getWidth()), 
							0, bufferedImage.getWidth());
					raf.close();
					x = x+rect.width;
					lstartPage++;
				}

				
				x =0;
				if(lastrowfirst){
					y = y-rect.height;
				}else{
					y = y+rect.height;
				}
			}
			System.out.println("read " + lstartPage + " pages");
			double ratio = rect.getWidth() / rect.getHeight();
			picLabel.setIcon(new ImageIcon(finalImage.getScaledInstance((int) Math.round(ratio*picLabel.getHeight()), picLabel.getHeight(), 0)));
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void write()
			throws DocumentException, FileNotFoundException, BadElementException, IOException {
		Document document = new Document();
		com.itextpdf.text.Rectangle recta = new com.itextpdf.text.Rectangle(finalImage.getWidth(), finalImage.getHeight());
		document.setPageSize(recta);
		String outputFilname = filename.subSequence(0, filename.lastIndexOf(".")) + "-PatternMerger.pdf";
		File output = new File(outputFilname);
		if(output.exists()){
			int i = okcancel("Die Datei " + outputFilname + "existiert bereits. Soll sie überschrieben werden?" );
			
			if (i != 0)
			{
				//Change Name.
				//Create a file chooser
				final JFileChooser fc = new JFileChooser(output);

				//In response to a button click:
				int returnVal = fc.showOpenDialog(mainFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					outputFilname = file.getName();
				}else{
					return;
				}
			}}
		com.itextpdf.text.Rectangle pagesize = document.getPageSize();
		if(pagesize.getWidth() > 14400 || pagesize.getHeight() > 14400){
			int i = okcancel("Dokument zu groß für ein PDF ("+pagesize.getWidth() + "/" + pagesize.getHeight()+"). Als jpg speichern?" );

			if (i == 0)
			{
				File jpgFile = new File(
						outputFilname.substring(0, outputFilname.lastIndexOf("."))+".png");
				ImageIO.write(finalImage, "jpg", jpgFile);

				System.out.println("wrote JPG to " + jpgFile);
			}else{
				return;
			}
		}

		if(pagesize.getWidth() > PageSize.A0.getWidth() || pagesize.getHeight() > PageSize.A0.getHeight()){

			JOptionPane.showMessageDialog(mainFrame, "Das Dokument ist größer als A0");

		}

		PdfWriter.getInstance(document,
				new FileOutputStream(outputFilname));
		document.open();
		document.newPage();
		
		com.itextpdf.text.Image nimage = com.itextpdf.text.Image.getInstance( finalImage, null);
		nimage.setAbsolutePosition(10, 10);
		
		document.add(nimage);

		mainFrame.add(panel);
		document.close();
		System.out.println("wrote PDF");
	}
	public static int okcancel(String theMessage) {
		int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
				"alert", JOptionPane.OK_CANCEL_OPTION);
		return result;
	}


	static class CombineActionListener implements ActionListener {
		CombineActionListener(Component component) {
		}

		public synchronized void actionPerformed(ActionEvent actionEvent) {
			values.update();
			picLabel.setIcon(new ImageIcon("./waiting.gif"));
			combine();
			


		}
	}
	static class SaveActionListener implements ActionListener {
		SaveActionListener(Component component) {
		}

		public synchronized void actionPerformed(ActionEvent actionEvent) {
			try {
				write();
			} catch (DocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}
	}


	public void setFilename(String filename) {
		PatternMergerGui.filename = filename;
	}

	public void setOverlapSides(int overlapSides) {
		PatternMergerGui.overlapSides = overlapSides;
	}

	public void setOverlapTopBottom(int overlapTopBottom) {
		PatternMergerGui.overlapTopBottom = overlapTopBottom;
	}

	public void setNumPagesinRow(int numPagesinRow) {
		PatternMergerGui.numPagesinRow = numPagesinRow;
	}

	public void setStartPage(int startPage) {
		PatternMergerGui.startPage = startPage;
	}

	public void showPreview() {

		prepair();
	}

	public void prepair(){
		if(raf != null){
			try {
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		skipPages = startPage -1;
		if(filename == null || filename == ""){
			return;
		}
		File inputFile = new File(filename);


		try {
			raf = new RandomAccessFile(inputFile,"r");


			FileChannel channel=raf.getChannel();
			ByteBuffer buf=channel.map(FileChannel.MapMode.READ_ONLY,0,channel.size());
			try{pdffile=new PDFFile(buf);}
			catch(PDFParseException pe){
				JOptionPane.showMessageDialog(mainFrame, "Datei ist kein PDF Dokument");
				return;
			}

			numPages = pdffile.getNumPages();
			if(endPage == -1){
				endPage = numPages;
			}
			numRows= (int) Math.ceil((double)(numPages-skipPages)/numPagesinRow);
		
			PDFPage hpage=pdffile.getPage(startPage);

			finalImage = new BufferedImage((int) hpage.getWidth()*numPagesinRow, (int) hpage.getHeight()*numRows, BufferedImage.TYPE_INT_RGB);
			//white background
			Graphics g1 = finalImage.getGraphics();
			g1.setColor(Color.white);
			g1.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());
			System.out.println(hpage.getBBox().getWidth()-2*overlapSides + " " + hpage.getBBox().getWidth() + " " + overlapSides);
			//part to copie
			rect = new Rectangle(overlapSides, overlapTopBottom,
					(int) hpage.getBBox().getWidth()-2*overlapSides,
					(int) hpage.getBBox().getHeight()-2*overlapTopBottom);
			//hole page for preview
			Rectangle pagerect = new Rectangle(0, 0,
					(int) hpage.getBBox().getWidth(),
					(int) hpage.getBBox().getHeight());


			Image himg = hpage.getImage(
					pagerect.width, 
					pagerect.height, 
					pagerect, 
					null, 
					true, 
					true 
					);


			BufferedImage bufferedImage = new BufferedImage(pagerect.width, pagerect.height, BufferedImage.TYPE_INT_RGB);
			Graphics g = bufferedImage.createGraphics();
			g.drawImage(himg, 0, 0, null);
			//zeichen auswahlbereich
			Graphics gh = himg.getGraphics();
			gh.setColor(Color.RED);
			gh.drawRect(overlapSides, overlapTopBottom, rect.width, rect.height);
			g.dispose();
			
			
			picLabel.setIcon(new ImageIcon(himg));


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setlastrowfirst(boolean selected) {
		lastrowfirst = selected;

	}

	public void setEndPage(Integer givenendPage) {
		
		endPage = givenendPage;
	}
}
