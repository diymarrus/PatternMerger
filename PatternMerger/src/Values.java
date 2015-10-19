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
	JTextField fieldOverlapSide;
	private JFormattedTextField fieldOverlapTB;
	private JFormattedTextField fieldnuminRow;
	private JFormattedTextField fieldStartPage;
	private JCheckBox lastrowfirst;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void buttons(PatternMergerGui patternMergerGui) {
		try {
			pm = patternMergerGui;
			GridLayout gridLayout = new GridLayout(2,1);
			this.setLayout(gridLayout);

			//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JPanel innerFile = new JPanel();
			//innerFile.setLayout(new GridLayout(1, 2, 10, 0));

			inputFile = new JFormattedTextField();
			inputFile.setName("input File");
			inputFile.setText("Pfad zur Input Datei");

			JLabel inputFilelable = new JLabel("Input Datei: ");

			innerFile.add(inputFilelable);
			innerFile.add(inputFile);
			inputFile.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {


					pm.setFilename(inputFile.getText());
					pm.showPreview();
				}

			});



			JButton open = new JButton("open File");
			open.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {
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

			NumberFormat numberInstance = NumberFormat.getNumberInstance();
			fieldOverlapSide = new JFormattedTextField(numberInstance);

			fieldOverlapSide.setName("overlapSides");
			fieldOverlapSide.setText(String.valueOf(0.0));
			JLabel Sidenamelable = new JLabel("Überlappung Seite in cm:");
			fieldOverlapSide.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {


					pm.setOverlapSides((int) Math.round(Float.valueOf(fieldOverlapSide.getText())/2.54*72));
					pm.showPreview();
				}

			});

			inner.add(Sidenamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapSide, BorderLayout.CENTER);

			fieldOverlapTB = new JFormattedTextField(numberInstance);
			fieldOverlapTB.setName("overlapTB");
			fieldOverlapTB.setText(String.valueOf(0.0));
			JLabel TBnamelable = new JLabel("Überlappung in cm:");
			fieldOverlapTB.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {


					pm.setOverlapTopBottom((int) Math.round(Float.valueOf(fieldOverlapTB.getText())/2.54*72));
					pm.showPreview();
				}

			});
			inner.add(TBnamelable, BorderLayout.CENTER);
			inner.add(fieldOverlapTB, BorderLayout.CENTER);


			fieldnuminRow = new JFormattedTextField(numberInstance);
			fieldnuminRow.setName("numInRow");
			fieldnuminRow.setText(String.valueOf(1));
			JLabel NiRnamelable = new JLabel("Anzahl der Seiten pro Reihe:");

			inner.add(NiRnamelable, BorderLayout.CENTER);
			inner.add(fieldnuminRow, BorderLayout.CENTER);

			fieldStartPage = new JFormattedTextField(numberInstance);
			fieldStartPage.setName("overlapTB");
			fieldStartPage.setText(String.valueOf(1));
			JLabel startPagelable = new JLabel("Start Seite:");
			fieldStartPage.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {


					pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
					pm.showPreview();
				}

			});
			inner.add(startPagelable, BorderLayout.CENTER);
			inner.add(fieldStartPage, BorderLayout.CENTER);

			lastrowfirst = new JCheckBox("Letzte Reihe zuerst");
			inner.add(lastrowfirst);

			JButton merge = new JButton("update Seitenvorschau");
			merge.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					buttonMergeClicked();

				}

				private void buttonMergeClicked() {
					if(inputFile.getText().equalsIgnoreCase("Pfad zur Input Datei")){
						return;
					}
					pm.setFilename(inputFile.getText());
					pm.setNumPagesinRow(Integer.valueOf(fieldnuminRow.getText()));
					pm.setOverlapSides((int) Math.round(Float.valueOf(fieldOverlapSide.getText())/2.54*72));
					pm.setOverlapTopBottom((int) Math.round(Float.valueOf(fieldOverlapTB.getText())/2.54*72));
					pm.setStartPage(Integer.valueOf(fieldStartPage.getText()));
					pm.showPreview();
					pm.setlastrowfirst(lastrowfirst.isSelected());
				}

			});


			inner.add(merge, BorderLayout.CENTER);
			this.add(inner);
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
		pm.showPreview();
		pm.setlastrowfirst(lastrowfirst.isSelected());
	}



}
