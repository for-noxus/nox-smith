package nox.scripts.smith.ui;

import nox.scripts.smith.NoxSmith;
import nox.scripts.smith.core.Constants;
import nox.scripts.smith.core.NamedBankArea;
import nox.scripts.smith.core.ScriptSettings;
import nox.scripts.smith.core.enums.Bar;
import nox.scripts.smith.core.enums.SmithItem;
import org.osbot.rs07.api.map.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class NoxSmithUI extends JDialog {
    private JPanel contentPane;
    private JPanel setupPanel;
    private JLabel helpTextToRun;
    private JPanel helpPanel;
    private JComboBox metalDropdown;
    private JPanel metalPanel;
    private JPanel smithItemPanel;
    private JLabel smithItemText;
    private JComboBox smithItemDropdown;
    private JLabel metalText;
    private JPanel superheatPanel;
    private JLabel superheatText;
    private JCheckBox superheatCheckbox;
    public JButton startButton;
    private JLabel helpTextSmeltingLocations;
    private JLabel helpTextSmithingLocations;
    private JLabel locationValidationText;

    private NamedBankArea[] supportedLocations;
    private NamedBankArea playerLocation;

    public NoxSmithUI(NamedBankArea[] supportedLocations)  {
        this(supportedLocations, null);
    }

    public NoxSmithUI(NamedBankArea[] supportedLocations, Component startComponent)  {
        this.supportedLocations = supportedLocations;
        setContentPane(contentPane);
        setModal(true);
        setModalityType(ModalityType.MODELESS);
        setLocationRelativeTo(startComponent);
        setTitle("Noxian Smither + Smelter -- V1.0");
        initComponents();
    }

    private void initComponents() {

        String smeltingText = helpTextSmeltingLocations.getText().replace(Constants.SMELTING_LOCATIONS_FILLER,
                Arrays.stream(supportedLocations).filter(NamedBankArea::isSmelting).map(this::getSupportedLocationFormattedString).reduce(String::concat).get());

        String smithingText = helpTextSmeltingLocations.getText().replace(Constants.SMELTING_LOCATIONS_FILLER,
                Arrays.stream(supportedLocations).filter(NamedBankArea::isSmithing).map(this::getSupportedLocationFormattedString).reduce(String::concat).get());

        helpTextSmeltingLocations.setText(smeltingText);
        helpTextSmithingLocations.setText(smithingText);

        metalDropdown.setModel(new DefaultComboBoxModel(Bar.values()));
        smithItemDropdown.setModel(new DefaultComboBoxModel(SmithItem.values()));

        smithItemDropdown.insertItemAt(Constants.SMITH_ITEM_NULL_VALUE, 0);
        smithItemDropdown.setSelectedIndex(0);}

    private String getSupportedLocationFormattedString(NamedBankArea toFormat) {
        return String.format("<li>%s</li", toFormat.getName());
    }

    public void setLocationValidationText(NamedBankArea nearestBank, Position playerPosition) {
        playerLocation = nearestBank;

        smithItemDropdown.setEnabled(playerLocation.isSmithing());

        if (playerLocation.getArea().getRandomPosition().distance(playerPosition) < 35) {
            if (playerLocation.isSmithing() && smithItemDropdown.getSelectedItem().toString().equals(Constants.SMITH_ITEM_NULL_VALUE)) {
                locationValidationText.setForeground(Color.YELLOW);
                locationValidationText.setText("<html>Select an item to smith!</html>");
                startButton.setEnabled(false);
            } else {
                locationValidationText.setForeground(Color.green);
                locationValidationText.setText(String.format("<html>Banking successfully set to %s.</html>", nearestBank.getName()));
                startButton.setEnabled(true);
            }
        } else {
            locationValidationText.setForeground(Color.YELLOW);
            locationValidationText.setText("<html>Too far from a valid Bank!</html>");
            startButton.setEnabled(false);
        }
        pack();
    }

    public void launch() {
        pack();
        setVisible(true);
    }

    public ScriptSettings extractSettings() {
        Bar selectedMetal = Arrays.stream(Bar.values()).filter(f -> f.getFriendlyName().equals(metalDropdown.getSelectedItem().toString())).findFirst().get();
        SmithItem selectedSmithItem = Arrays.stream(SmithItem.values()).filter(f -> f.getFriendlyName().equals(smithItemDropdown.getSelectedItem().toString())).findFirst().orElse(null);

        ScriptSettings settings = new ScriptSettings(selectedMetal, selectedSmithItem, superheatCheckbox.isSelected());
        settings.setBankArea(playerLocation);

        return settings;
    }
}
