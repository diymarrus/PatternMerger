import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Values extends JPanel {
	JButton	merge;
	PatternMergerGui pm;
	JTextField inputFile;
	private JFormattedTextField fieldOverlapSide;
	private JFormattedTextField fieldOverlapTB;
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

			//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JPanel innerFile = new JPanel();
			//innerFile.setLayout(new GridLayout(1, 2, 10, 0));

			inputFile = new JFormattedTextField();
			inputFile.setName("input File");
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



			JButton open = new JButton("open File");
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
			NumberFormat overlapFormat = NumberFormat.getNumberInstance();
			overlapFormat.setMinimumFractionDigits(1);
			

			NumberFormat numberInstance = NumberFormat.getNumberInstance();
			fieldOverlapSide = new JFormattedTextField(overlapFormat);
			fieldOverlapSide.setColumns(4);
			fieldOverlapSide.setName("overlapSides");
			fieldOverlapSide.setValue(0.0);
			JLabel Sidenamelable = new JLabel("Überlappung Seite in cm:");
			fieldOverlapSide.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setOverlapSides((int) Math.round(Float.valueOf(fieldOverlapSide.getText())/2.54*72));
					pm.showPreview();
				}

			});

			inner.add(Sidenamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapSide, BorderLayout.CENTER);
			
			fieldOverlapTB = new JFormattedTextField(overlapFormat);
			fieldOverlapTB.setColumns(4);
			fieldOverlapTB.setName("overlapTB");
			fieldOverlapTB.setValue(0.0);
			JLabel TBnamelable = new JLabel("Überlappung in cm:");
			fieldOverlapTB.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					newFieldInput();

				}

				private void newFieldInput() {


					pm.setOverlapTopBottom((int) Math.round(Float.valueOf(fieldOverlapTB.getText())/2.54*72));
					pm.showPreview();
				}

			});
			inner.add(TBnamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapTB, BorderLayout.CENTER);

			
			fieldnuminRow = new JFormattedTextField(numberInstance);
			fieldnuminRow.setColumns(3);
			fieldnuminRow.setName("numInRow");
			fieldnuminRow.setText(String.valueOf(1));
			JLabel NiRnamelable = new JLabel("Anzahl der Seiten pro Reihe:");
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
			JButton merge = new JButton("update Seitenvorschau");
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
					pm.setOverlapSides((int) Math.round(Float.valueOf(fieldOverlapSide.getText())/2.54*72));
					pm.setOverlapTopBottom((int) Math.round(Float.valueOf(fieldOverlapTB.getText())/2.54*72));
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

	public void update() {
		pm.setFilename(inputFile.getText());
		pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
		pm.setOverlapSides((int) Math.round(Float.valueOf(fieldOverlapSide.getText())/2.54*72));
		pm.setOverlapTopBottom((int) Math.round(Float.valueOf(fieldOverlapTB.getText())/2.54*72));
		pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
		pm.setEndPage(Integer.valueOf(fieldEndPage.getText()));
		pm.showPreview();
		pm.setlastrowfirst(lastrowfirst.isSelected());
	}



}
