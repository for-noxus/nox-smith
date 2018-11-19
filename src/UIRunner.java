import nox.scripts.smith.core.NamedBankAreas;
import nox.scripts.smith.ui.NoxSmithUI;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class UIRunner {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { }
        UIManager.put("ComboBox.selectionBackground", new Color(32, 32, 32));
        UIManager.put("ComboBox.selectionForeground", Color.white);
        UIManager.put("Button.select", new ColorUIResource(66, 66, 66));
        NoxSmithUI ui = new NoxSmithUI(NamedBankAreas.get());
        ui.launch();
    }
}
