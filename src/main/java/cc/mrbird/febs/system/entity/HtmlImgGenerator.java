package cc.mrbird.febs.system.entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * html转图片
 *
 * @author weiZiHao
 * @date 2020/12/23
 */
public class HtmlImgGenerator {
    private JEditorPane editorPane;
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

    public static Map<String, String> types = new HashMap<String, String>();

    static {
        types.put("gif", "gif");
        types.put("jpg", "jpg");
        types.put("jpeg", "jpg");
        types.put("png", "png");
    }

    public static String formatForExtension(String extension) {
        String type = (String) types.get(extension);
        if (type == null)
            return "png";

        return type;
    }

    public static String formatForFilename(String fileName) {
        int dotIndex = fileName.lastIndexOf(46);
        if (dotIndex < 0)
            return "png";

        String ext = fileName.substring(dotIndex + 1);
        return formatForExtension(ext);
    }


    public HtmlImgGenerator() {
        editorPane = createJEditorPane();
    }


    public void setSize(Dimension dimension) {
        editorPane.setPreferredSize(dimension);
    }

//    public void loadUrl(URL url) {
//        try {
//            editorPane.setPage(url);
//        } catch (IOException e) {
//            throw new RuntimeException(String.format("Exception while loading %s", url), e);
//        }
//    }

    public void loadUrl(String url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadHtml(String html) {
        editorPane.setEditable(false);
        editorPane.setText(html);
        editorPane.setContentType("text/html");
        onDocumentLoad();
    }


    public void saveAsImage(String file) {
        saveAsImage(new File(file));
    }

    public void saveAsImage(File file) {
        BufferedImage image = getBufferedImage();

        BufferedImage bufferedImageToWrite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImageToWrite.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

        final String formatName = formatForFilename(file.getName());
        try {
            if (!ImageIO.write(bufferedImageToWrite, formatName, file))
                throw new IOException("No formatter for specified file type [" + formatName + "] available");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
        }
    }

    protected void onDocumentLoad() {
    }

    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public BufferedImage getBufferedImage() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(editorPane.getPreferredSize());
        frame.setUndecorated(true);
        frame.add(editorPane);
        frame.pack();

        Dimension prefSize = frame.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, prefSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img.getGraphics();

        frame.setVisible(true);
        frame.paint(graphics);
        frame.setVisible(false);
        frame.dispose();

        return img;
    }

    protected JEditorPane createJEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setSize(getDefaultSize());
        editorPane.setEditable(false);
        EditorKit kit = new HTMLEditorKit();
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html");

        return editorPane;
    }

}
