package edu.ju.ssc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.util.SSCUtils;

public class SemanticSimilarityCalculatorGUI {
	private static final int HEIGHT = 360;
	private static final int WIDTH = 640;
	private JFrame frame;
	private JTextArea logArea;
	private JFileChooser fileChooser;
	private File loadedFile;
	private JButton startAppButton;
	private ActionListener actor;

	public SemanticSimilarityCalculatorGUI() {
		actor = new ActionPerformer();
		fileChooser = new JFileChooser(FileUtils.getFile("."));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	private void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame(Constants.APP_TITLE);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame.setJMenuBar(addMenuBar());
		buildTextArea();
		buildStartAppButton();
		startAppButton.setEnabled(false);
		logArea.append("Welcome to " + Constants.APP_TITLE + StringUtils.SPACE
				+ Constants.APP_VERSION + "\n");

		// Make sure we have nice window decorations.
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
			SwingUtilities.updateComponentTreeUI(fileChooser);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			JFrame.setDefaultLookAndFeelDecorated(true);
		}

		// Display the window.
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	private JMenuBar addMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		menubar.add(helpMenu);

		/**
		 * FILE MENU
		 */

		JMenuItem openFile = new JMenuItem("Open File");
		openFile.setMnemonic('O');
		openFile.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F, InputEvent.CTRL_MASK));
		openFile.addActionListener(actor);
		fileMenu.add(openFile);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		exit.addActionListener(actor);
		fileMenu.add(exit);

		/**
		 * The Help Menu
		 */
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(actor);
		helpMenu.add(about);

		return menubar;
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		if (isMacOSX()) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		SwingUtilities.invokeLater(() -> {
			new SemanticSimilarityCalculatorGUI().createAndShowGUI();
		});

	}

	private void openFile(File file) {
		loadedFile = file;
	}

	private File getFile(boolean open) {
		File file = null;
		int returnVal;
		if (open) {
			returnVal = fileChooser.showOpenDialog(frame);
		} else {
			returnVal = fileChooser.showSaveDialog(frame);
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (open && !checkFile(file)) {
				file = null;
			} else {
				startAppButton.setEnabled(true);
			}
		}
		return file;
	}

	private boolean checkFile(File file) {
		if (file.isFile()) {
			fileChooser.setCurrentDirectory(file.getParentFile());
			return true;
		} else {
			String message = "File Not Found: " + file.getAbsolutePath();
			displayError("File Not Found Error", message);
			return false;
		}
	}

	private void displayError(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	private void buildStartAppButton() {
		if (startAppButton == null) {
			JPanel buttonPanel = new JPanel();
			startAppButton = new JButton("Start");
			buttonPanel.add(startAppButton);
			frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			startAppButton.addActionListener(actor);
		}
	}

	private void buildTextArea() {
		logArea = new JTextArea(10, 20);
		JScrollPane scrollPane = new JScrollPane(logArea);
		logArea.setMargin(new Insets(5, 5, 5, 5));
		logArea.setEditable(false);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	private static void exit() {
		// ask if they're sure?
		System.exit(-1);
	}

	private static boolean isMacOSX() {
		return System.getProperty("os.name").toLowerCase()
				.startsWith("mac os x");
	}

	private class ActionPerformer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String com = e.getActionCommand();

			switch (com) {
			case "Open File": {
				File file = getFile(true);
				if (file != null) {
					openFile(file);
					logArea.append("\nInput data file selected: "
							+ file.getAbsolutePath());
				}
				break;
			}
			case "Start":
				new Thread(
						() -> {
							logArea.append("\nComputation started.\nIt may take several minutes...");
							startAppButton.setEnabled(false);
							long t1 = System.currentTimeMillis();

							SemanticSimilarityCalculationDemo
									.updateConfiguration(loadedFile
											.getAbsolutePath());
							try {
								SemanticSimilarityCalculationDemo
										.startApplication();
							} catch (SSCException ex) {
								logArea.append(StringUtils.LF
										+ Constants.DEBUG_EXCEPTION_MSG);
							}

							long t2 = System.currentTimeMillis();
							String duration = SSCUtils.getDuration(Math
									.round((t2 - t1) / 1000.0));
							logArea.append("\nComputation completed.");
							logArea.append("\nTotal time taken: " + duration
									+ "\n");
							logArea.append(String.format(Constants.FORMAT_STR,
									StringUtils.leftPad(StringUtils.EMPTY,
											logArea.getColumns() * 3,
											Constants.DASH_STR)));
							startAppButton.setEnabled(true);
						}).start();

				break;
			case "About":
				JOptionPane.showMessageDialog(frame, Constants.APP_TITLE
						+ "\nVersion: " + Constants.APP_VERSION
						+ Constants.AUTHOR_INFO, Constants.APP_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case "Exit":
				exit();
				break;
			default:
				System.err.println("Unknown Action: " + e);
				break;
			}
		}
	}

}
