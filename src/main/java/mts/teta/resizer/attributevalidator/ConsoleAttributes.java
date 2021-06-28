package mts.teta.resizer.attributevalidator;

import picocli.CommandLine;

import java.io.File;

public class ConsoleAttributes {
    @CommandLine.Parameters(arity = "0..2", hidden = true)
    String[] fileNames;

    protected File inputFile;
    protected File outputFile;

    @CommandLine.Option(names = "--resize", arity = "0..2", hideParamSyntax = true, paramLabel = "width height", split = " ", description = "resize the image")
    String[] resizeAttributes;

    protected Integer resizeWidth;
    protected Integer resizeHeight;

    @CommandLine.Option(names = "--quality", paramLabel = "value", description = "JPEG/PNG compression level")
    String qualityAttribute;

    protected Integer value;

    @CommandLine.Option(names = "--crop", arity = "0..4", hideParamSyntax = true, paramLabel = "width height x y", description = "cut out one rectangular area of the image")
    String[] cropAttributes;

    protected Integer cropWidth;
    protected Integer cropHeigth;
    protected Integer cropX;
    protected Integer cropY;

    @CommandLine.Option(names = "--blur", paramLabel = "{radius}", description = "reduce image noise detail levels")
    String blurAttribute;

    protected Integer radius;

    @CommandLine.Option(names = "--format", paramLabel = "\"outputFormat\"", description = "the image format type")
    protected String format;

    @CommandLine.Option(names = "--help", usageHelp = true, hidden = true)
    private boolean helpRequested;

    @CommandLine.Option(names = "--version", versionHelp = true, hidden = true)
    private boolean versionRequested;

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setResizeWidth(Integer resizeWidth) {
        this.resizeWidth = resizeWidth;
    }

    public void setResizeHeight(Integer resizeHeight) {
        this.resizeHeight = resizeHeight;
    }

    public void setQuality(Integer value) {
        this.value = value;
    }

    public void setCropWidth(Integer cropWidth) {
        this.cropWidth = cropWidth;
    }

    public void setCropHeigth(Integer cropHeigth) {
        this.cropHeigth = cropHeigth;
    }

    public void setCropX(Integer cropX) {
        this.cropX = cropX;
    }

    public void setCropY(Integer cropY) {
        this.cropY = cropY;
    }

    public void setBlurRadius(Integer radius) {
        this.radius = radius;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public Integer getResizeHeight() {
        return resizeHeight;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getCropWidth() {
        return cropWidth;
    }

    public Integer getCropHeigth() {
        return cropHeigth;
    }

    public Integer getCropX() {
        return cropX;
    }

    public Integer getCropY() {
        return cropY;
    }

    public Integer getRadius() {
        return radius;
    }

    public String getFormat() {
        return format;
    }
}
