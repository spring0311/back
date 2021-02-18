package cc.mrbird.febs.system.entity;

import com.openhtmltopdf.extend.NamespaceHandler;
import com.openhtmltopdf.extend.UserAgentCallback;
import com.openhtmltopdf.extend.UserInterface;
import com.openhtmltopdf.layout.BoxBuilder;
import com.openhtmltopdf.layout.LayoutContext;
import com.openhtmltopdf.layout.SharedContext;
import com.openhtmltopdf.render.BlockBox;
import com.openhtmltopdf.render.Box;
import com.openhtmltopdf.render.RenderingContext;
import com.openhtmltopdf.render.ViewportBox;
import com.openhtmltopdf.simple.extend.XhtmlNamespaceHandler;
import com.openhtmltopdf.swing.*;
import com.openhtmltopdf.util.Configuration;
import com.openhtmltopdf.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author weiZiHao
 * @date 2021/2/18
 */
public class Java2DRenderer {
    private static final int DEFAULT_HEIGHT = 1000;
    private static final int DEFAULT_DOTS_PER_POINT = 1;
    private static final int DEFAULT_DOTS_PER_PIXEL = 1;
    private static final int DEFAULT_IMAGE_TYPE = 1;
    private SharedContext sharedContext;
    private Java2DOutputDevice outputDevice;
    private Document doc;
    private Box root;
    private float dotsPerPoint;
    private BufferedImage outputImage;
    private int bufferedImageType;
    private boolean rendered;
    private String sourceDocument;
    private String sourceDocumentBase;
    private int width;
    private int height;
    private static final int NO_HEIGHT = -1;
    private Map renderingHints;

    private Java2DRenderer() {
        this.bufferedImageType = 1;
    }

    private Java2DRenderer(float dotsPerPoint, int dotsPerPixel) {
        this();
        this.init(dotsPerPoint, dotsPerPixel);
    }

    public Java2DRenderer(String url, String baseUrl, int width, int height) {
        this(1.0F, 1);
        this.sourceDocument = url;
        this.sourceDocumentBase = baseUrl;
        this.width = width;
        this.height = height;
    }

    public Java2DRenderer(File file, int width, int height) throws IOException {
        this(file.toURI().toURL().toExternalForm(), width, height);
    }

    public Java2DRenderer(Document doc, int width, int height) {
        this(1.0F, 1);
        this.doc = doc;
        this.width = width;
        this.height = height;
    }

    public Java2DRenderer(Document doc, String baseUrl, int width, int height) {
        this(doc, width, height);
        this.sourceDocumentBase = baseUrl;
    }

    public Java2DRenderer(File file, int width) throws IOException {
        this(file.toURI().toURL().toExternalForm(), width);
    }

    public Java2DRenderer(String url, int width) {
        this((String) url, url, width, -1);
    }

    public Java2DRenderer(String url, String baseurl, int width) {
        this((String) url, baseurl, width, -1);
    }

    public Java2DRenderer(String url, int width, int height) {
        this(url, url, width, height);
    }

    public void setRenderingHints(Map hints) {
        this.renderingHints = hints;
    }

    public void setBufferedImageType(int bufferedImageType) {
        this.bufferedImageType = bufferedImageType;
    }

    public SharedContext getSharedContext() {
        return this.sharedContext;
    }

    public BufferedImage getImage() {
        if (!this.rendered) {
            this.setDocument(this.doc == null ? this.loadDocument(this.sourceDocument) : this.doc, this.sourceDocumentBase, new XhtmlNamespaceHandler());
            this.layout(this.width);
            this.height = this.height == -1 ? this.root.getHeight() : this.height;
            this.outputImage = this.createBufferedImage(this.width, this.height);
            this.outputDevice = new Java2DOutputDevice(this.outputImage);
            Graphics2D newG = (Graphics2D) this.outputImage.getGraphics();
            newG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            /*Font font = new Font("宋体",Font.PLAIN,);*/
            if (this.renderingHints != null) {
                newG.addRenderingHints(this.renderingHints);
            }

            RenderingContext rc = this.sharedContext.newRenderingContextInstance();
            rc.setFontContext(new Java2DFontContext(newG));
            rc.setOutputDevice(this.outputDevice);
            this.sharedContext.getTextRenderer().setup(rc.getFontContext());
            this.root.getLayer().paint(rc);
            newG.dispose();
            this.rendered = true;
        }

        return this.outputImage;
    }

    protected BufferedImage createBufferedImage(int width, int height) {
        BufferedImage image = ImageUtil.createCompatibleBufferedImage(width, height, this.bufferedImageType);
        ImageUtil.clearImage(image);
        return image;
    }

    private void setDocument(Document doc, String url, NamespaceHandler nsh) {
        this.doc = doc;
        this.sharedContext.reset();
        if (Configuration.isTrue("xr.cache.stylesheets", true)) {
            this.sharedContext.getCss().flushStyleSheets();
        } else {
            this.sharedContext.getCss().flushAllStyleSheets();
        }

        this.sharedContext.setBaseURL(url);
        this.sharedContext.setNamespaceHandler(nsh);
        this.sharedContext.getCss().setDocumentContext(this.sharedContext, this.sharedContext.getNamespaceHandler(), doc, new Java2DRenderer.NullUserInterface());
    }

    private void layout(int width) {
        Rectangle rect = new Rectangle(0, 0, width, 1000);
        this.sharedContext.setTempCanvas(rect);
        LayoutContext c = this.newLayoutContext();
        BlockBox root = BoxBuilder.createRootBox(c, this.doc);
        root.setContainingBlock(new ViewportBox(rect));
        root.layout(c);
        this.root = root;
    }

    private Document loadDocument(String uri) {
        return this.sharedContext.getUac().getXMLResource(uri).getDocument();
    }

    private LayoutContext newLayoutContext() {
        LayoutContext result = this.sharedContext.newLayoutContextInstance();
        result.setFontContext(new Java2DFontContext(this.outputDevice.getGraphics()));
        this.sharedContext.getTextRenderer().setup(result.getFontContext());
        return result;
    }

    private void init(float dotsPerPoint, int dotsPerPixel) {
        this.dotsPerPoint = dotsPerPoint;
        this.outputImage = ImageUtil.createCompatibleBufferedImage(1, 1);
        this.outputDevice = new Java2DOutputDevice(this.outputImage);
        UserAgentCallback userAgent = new NaiveUserAgent();
        this.sharedContext = new SharedContext(userAgent);
        this.sharedContext.registerWithThread();
        AWTFontResolver fontResolver = new AWTFontResolver();
        this.sharedContext.setFontResolver(fontResolver);
        SwingReplacedElementFactory replacedElementFactory = new SwingReplacedElementFactory();
        this.sharedContext.setReplacedElementFactory(replacedElementFactory);
        this.sharedContext.setTextRenderer(new Java2DTextRenderer());
        this.sharedContext.setDPI(72.0F * this.dotsPerPoint);
        this.sharedContext.setDotsPerPixel(dotsPerPixel);
        this.sharedContext.setPrint(false);
        this.sharedContext.setInteractive(false);
    }

    public void cleanup() {
        this.sharedContext.removeFromThread();
    }

    private static final class NullUserInterface implements UserInterface {
        private NullUserInterface() {
        }

        public boolean isHover(Element e) {
            return false;
        }

        public boolean isActive(Element e) {
            return false;
        }

        public boolean isFocus(Element e) {
            return false;
        }
    }
}
