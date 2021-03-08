import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Values extends JPanel implements PropertyChangeListener{
	JButton	merge;
	PatternMergerGui pm;
	JTextField inputFile;
	private JFormattedTextField fieldOverlapRSide;
	private JFormattedTextField fieldOverlapLSide;
	
	private JFormattedTextField fieldOverlapTop;
	private JFormattedTextField fieldOverlapBottom;

	private JFormattedTextField fieldnuminRow;
	private JFormattedTextField fieldStartPage;
	private JCheckBox lastrowfirst;
	private JFormattedTextField fieldEndPage;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void buttons(PatternMergerGui patternMergerGui) {
		try {
			pm = patternMergerGui;
			GridLayout gridLayout = new GridLayout(3,1);
			this.setLayout(gridLayout);


			JPanel innerFile = new JPanel();

			inputFile = new JFormattedTextField();
			inputFile.setName("Input Datei");
			inputFile.setText("Pfad zur Input Datei");
			inputFile.setColumns(30);
			JLabel inputFilelable = new JLabel("Input Datei: ");

			innerFile.add(inputFilelable);
			innerFile.add(inputFile);
			inputFile.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setFilename(inputFile.getText());
					pm.showPreview();
				}

			});



			JButton open = new JButton("Datei öffnen");
			open.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {
					final JFileChooser fc = new JFileChooser();

					//In response to a button click:
					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						inputFile.setText(file.getAbsolutePath());
						pm.setFilename(file.getAbsolutePath());
						pm.showPreview();
					}
				}

			});

			innerFile.add(open, BorderLayout.PAGE_START);
			this.add(innerFile);
			
			
			//----------------- Eingabe Werte---------------
			JPanel inner = new JPanel();
			inner.setLayout(new FlowLayout());
			NumberFormat overlapFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
			overlapFormat.setMinimumFractionDigits(1);
			overlapFormat.setParseIntegerOnly(false);
			NumberFormat numberInstance = NumberFormat.getNumberInstance();

			//----------------- Linke Überlappung---------------
			fieldOverlapLSide = new JFormattedTextField(overlapFormat);
			fieldOverlapLSide.setColumns(4);
			fieldOverlapLSide.setName("overlapLSides");
			fieldOverlapLSide.setValue(0.0);
			JLabel lSidenamelable = new JLabel("Links cm:");
			fieldOverlapLSide.addPropertyChangeListener("value" , this);
			fieldOverlapLSide.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {

					
					pm.setOverlapLeftSide((int) Math.round(Float.valueOf(fieldOverlapLSide.getText().replace(",", "."))/2.54*72));
					pm.showPreview();
				}

			});

			inner.add(lSidenamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapLSide, BorderLayout.CENTER);
			
			//----------------- Rechte Überlappung---------------
			fieldOverlapRSide = new JFormattedTextField(overlapFormat);
			fieldOverlapRSide.setColumns(4);
			fieldOverlapRSide.setName("overlapRSides");
			fieldOverlapRSide.setValue(0.0);
			JLabel Sidenamelable = new JLabel("Rechts cm:");
			fieldOverlapRSide.addPropertyChangeListener("value" , this);
			fieldOverlapRSide.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {

					
					pm.setOverlapRightSide((int) Math.round(Float.valueOf(fieldOverlapRSide.getText().replace(",", "."))/2.54*72));
					pm.showPreview();
				}

			});

			inner.add(Sidenamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapRSide, BorderLayout.CENTER);

			
			//----------------- obere Überlappung---------------
			fieldOverlapBottom = new JFormattedTextField(overlapFormat);
			fieldOverlapBottom.setColumns(4);
			fieldOverlapBottom.setName("overlapTB");
			fieldOverlapBottom.setValue(0.0);
			fieldOverlapBottom.addPropertyChangeListener("value" , this);
			JLabel bnamelable = new JLabel("Oben cm:");
			fieldOverlapBottom.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setOverlapBottom((int) Math.round(Float.valueOf(fieldOverlapBottom.getText().replace(",", "."))/2.54*72));
					pm.showPreview();
				}

			});
			inner.add(bnamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapBottom, BorderLayout.CENTER);
			
			//----------------- Untere Überlappung---------------
			fieldOverlapTop = new JFormattedTextField(overlapFormat);
			fieldOverlapTop.setColumns(4);
			fieldOverlapTop.setName("overlapTB");
			fieldOverlapTop.setValue(0.0);
			fieldOverlapTop.addPropertyChangeListener("value" , this);
			JLabel tnamelable = new JLabel("Unten cm:");
			fieldOverlapTop.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setOverlapTop((int) Math.round(Float.valueOf(fieldOverlapTop.getText().replace(",", "."))/2.54*72));
					pm.showPreview();
				}

			});
			inner.add(tnamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapTop, BorderLayout.CENTER);
			
			
			fieldnuminRow = new JFormattedTextField(numberInstance);
			fieldnuminRow.setColumns(3);
			fieldnuminRow.setName("numInRow");
			fieldnuminRow.setText(String.valueOf(1));
			fieldnuminRow.addPropertyChangeListener("value" , this);
			JLabel NiRnamelable = new JLabel("Seiten pro Reihe:");
			fieldnuminRow.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
					pm.showPreview();
				}

			});
			
			
			inner.add(NiRnamelable, BorderLayout.CENTER);
			inner.add(fieldnuminRow, BorderLayout.CENTER);
			
			fieldStartPage = new JFormattedTextField(numberInstance);
			fieldStartPage.setColumns(3);
			fieldStartPage.setName("startPage");
			fieldStartPage.setText(String.valueOf(1));
			fieldStartPage.addPropertyChangeListener("value" , this);
			JLabel startPagelable = new JLabel("Start Seite:");
			fieldStartPage.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
					pm.showPreview();
				}

			});
			inner.add(startPagelable, BorderLayout.CENTER);
			inner.add(fieldStartPage, BorderLayout.CENTER);
			
			fieldEndPage = new JFormattedTextField(numberInstance);
			fieldEndPage.setColumns(3);
			fieldEndPage.setName("endPage");
			fieldEndPage.setText(String.valueOf(-1));
			fieldEndPage.addPropertyChangeListener("value" , this);
			JLabel endPagelable = new JLabel("Letzte Seite:");
			fieldEndPage.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setEndPage(Integer.valueOf(fieldEndPage.getText()));
					pm.showPreview();
				}

			});
			inner.add(endPagelable, BorderLayout.CENTER);
			inner.add(fieldEndPage, BorderLayout.CENTER);

			lastrowfirst = new JCheckBox("Letzte Reihe zuerst");
			inner.add(lastrowfirst);
			
			JPanel thirdrow = new JPanel();
			thirdrow.setLayout(new FlowLayout());
			JButton merge = new JButton("Update Seitenvorschau");
			merge.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonUpdateClicked();

				}

				private void buttonUpdateClicked() {
					if(inputFile.getText().equalsIgnoreCase("Pfad zur Input Datei")){
						return;
					}
					pm.setFilename(inputFile.getText());
					pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
					pm.setOverlapRightSide((int) Math.round(Float.valueOf(fieldOverlapRSide.getText().replace(",", "."))/2.54*72));
					pm.setOverlapLeftSide((int) Math.round(Float.valueOf(fieldOverlapLSide.getText().replace(",", "."))/2.54*72));
					pm.setOverlapTop((int) Math.round(Float.valueOf(fieldOverlapTop.getText().replace(",", "."))/2.54*72));
					pm.setOverlapBottom((int) Math.round(Float.valueOf(fieldOverlapBottom.getText().replace(",", "."))/2.54*72));
					pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
					pm.setEndPage(Integer.valueOf(fieldEndPage.getText()));
					pm.showPreview();
					pm.setlastrowfirst(lastrowfirst.isSelected());
				}

			});


			thirdrow.add(merge, BorderLayout.CENTER);
			this.add(inner);
			this.add(thirdrow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		}

	}
	public void propertyChange(PropertyChangeEvent e) {
	    Object source = e.getSource();
	    if (source == fieldOverlapRSide) {
	    	pm.setOverlapRightSide((int) Math.round(Float.valueOf(fieldOverlapRSide.getText().replace(",", "."))/2.54*72));
	    } else if (source == fieldOverlapLSide) {
	    	pm.setOverlapLeftSide((int) Math.round(Float.valueOf(fieldOverlapLSide.getText().replace(",", "."))/2.54*72));
	    }
	    else if (source == fieldOverlapTop) {
	    	pm.setOverlapTop((int) Math.round(Float.valueOf(fieldOverlapTop.getText().replace(",", "."))/2.54*72));
	    } 
	    else if (source == fieldOverlapBottom) {
	    	pm.setOverlapBottom((int) Math.round(Float.valueOf(fieldOverlapBottom.getText().replace(",", "."))/2.54*72));
	    }
	    else if (source == fieldnuminRow) {
	    	pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
	    }
	    else if (source == fieldStartPage) {
	    	pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
	    }
	    else if (source == fieldEndPage) {
	    	pm.setEndPage(Integer.valueOf(fieldEndPage.getText()));
	    }
	}
	public void update() {
		pm.setFilename(inputFile.getText());
		pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
		pm.setOverlapRightSide((int) Math.round(Float.valueOf(fieldOverlapRSide.getText().replace(",", "."))/2.54*72));
		pm.setOverlapLeftSide((int) Math.round(Float.valueOf(fieldOverlapLSide.getText().replace(",", "."))/2.54*72));
		pm.setOverlapTop((int) Math.round(Float.valueOf(fieldOverlapTop.getText().replace(",", "."))/2.54*72));
		pm.setOverlapBottom((int) Math.round(Float.valueOf(fieldOverlapBottom.getText().replace(",", "."))/2.54*72));
		pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
		pm.setEndPage(Integer.valueOf(fieldEndPage.getText()));
		pm.setlastrowfirst(lastrowfirst.isSelected());
		pm.showPreview();
		
	}



}
