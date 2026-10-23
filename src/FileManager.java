import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import java.io.FileWriter;

public class FileManager {
    public static void saveToTxt(StyledDocument doc, String filepath) throws Exception {
        if (!filepath.toLowerCase().endsWith(".txt")) filepath += ".txt";

        try (FileWriter fw = new FileWriter(filepath)) {
            for (int i = 0; i < doc.getDefaultRootElement().getElementCount(); i++) {
                javax.swing.text.Element p = doc.getDefaultRootElement().getElement(i);
                int indent = (int) StyleConstants.getLeftIndent(p.getAttributes());

                int spaces = 0;
                for (ElementType type : ElementType.values()) {
                    if (type.getSwingIndent() == indent) {
                        spaces = type.getTxtSpaces();
                        break;
                    }
                }

                int start = p.getStartOffset();
                int end = p.getEndOffset();
                String text = doc.getText(start, end - start);

                for (int s = 0; s < spaces; s++) fw.write(" ");
                fw.write(text);
            }
        }
    }
}