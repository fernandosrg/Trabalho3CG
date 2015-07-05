package src;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FilterSizeDialog extends JDialog implements ChangeListener {
	private static final long serialVersionUID = 7330281274942832587L;

	private int filterSize;
	private JSlider slider;
	private JLabel label;
	private JButton button;
	
	public static int showFilterSizeDialog(Frame parent) {
		FilterSizeDialog dialog = new FilterSizeDialog(parent);
		dialog.setVisible(true);
		return dialog.filterSize;
	}
	
	public FilterSizeDialog(Frame parent) {
		super(parent);
		
		filterSize = 3;
		
		setTitle("Choose filter size");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setModalityType(ModalityType.APPLICATION_MODAL);
        
        label = new JLabel();
        updateLabel();
        
		slider = new JSlider(3, 31, filterSize);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(2);
		slider.setSnapToTicks(true);
		slider.addChangeListener(this);
		
		button = new JButton("Apply");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		Container pane = getContentPane();
		
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 0, 10);
		fl.setHgap(10);
		pane.setLayout(fl);
		
		pane.add(slider);
		pane.add(label);
		pane.add(button);
		pack();
	}

	private void updateLabel() {
		label.setText(Integer.toString(filterSize));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		filterSize = slider.getValue();
		updateLabel();
	}
}
