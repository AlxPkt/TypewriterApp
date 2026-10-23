import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    public static void saveToTxt(StyledDocument doc, String filepath) throws Exception {
        if (!filepath.toLowerCase().endsWith(".txt")) filepath += ".txt";

        try (FileWriter fw = new FileWriter(filepath)) {
            for (int i = 0; i < doc.getDefaultRootElement().getElementCount(); i++) {
                javax.swing.text.Element p = doc.getDefaultRootElement().getElement(i);
                int indent = (int) StyleConstants.getLeftIndent(p.getAttributes());

                int spaces = 0;
                for (ElementType type : ElementType.values()) {
                    if (type.getSwingIndent() == indent) { spaces = type.getTxtSpaces(); break; }
                }

                int start = p.getStartOffset();
                int end = p.getEndOffset();
                String text = doc.getText(start, end - start);

                for (int s = 0; s < spaces; s++) fw.write(" ");
                fw.write(text);
            }
        }
    }

    public static void openFromTxt(StyledDocument doc, DocumentType docType, String filepath) throws Exception {
        doc.remove(0, doc.getLength());
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        ElementType[] validTypes = ElementType.getValuesFor(docType);

        for (String line : lines) {
            int spaceCount = 0;
            while (spaceCount < line.length() && line.charAt(spaceCount) == ' ') spaceCount++;

            ElementType matchedType = validTypes[0];
            for (ElementType type : validTypes) {
                if (Math.abs(type.getTxtSpaces() - spaceCount) <= 2 && spaceCount > 0) {
                    matchedType = type; break;
                }
            }

            String text = line.substring(spaceCount);
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setLeftIndent(attrs, matchedType.getSwingIndent());
            StyleConstants.setFirstLineIndent(attrs, matchedType.getFirstLineIndent());
            attrs.addAttribute("ElementType", matchedType.name());

            int len = doc.getLength();
            doc.insertString(len, text + "\n", attrs);
            doc.setParagraphAttributes(len, text.length() + 1, attrs, false);
        }
    }
}