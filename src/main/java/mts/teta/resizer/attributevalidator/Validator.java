package mts.teta.resizer.attributevalidator;

import mts.teta.resizer.imageprocessor.BadAttributesException;

import java.io.File;

public class Validator {
    private static final int STANDART_VALUE_QUALITY = 92;
    private ConsoleAttributes instance;

    public Validator(ConsoleAttributes instance) {
        this.instance = instance;
    }

    public void validateAllFields() throws BadAttributesException {
        new Parser().parseAllAttributes();
        validateFiles();
        validateResizeFields();
        validateCropFields();
        validateBlurField();
        validateValue();
        validateOutputFormat();
    }

    private void validateCropFields() throws BadAttributesException {
        if (instance.cropHeigth == null && instance.cropWidth == null && instance.cropX == null && instance.cropY == null) {
            return;
        }
        if (instance.cropHeigth == null || instance.cropWidth == null || instance.cropX == null || instance.cropY == null) {
            throw new BadAttributesException("not enough crop parameters");
        }
        if (instance.cropHeigth < 0 || instance.cropWidth < 0 || instance.cropX < 0 || instance.cropY < 0) {
            throw new BadAttributesException("invalid crop parameters");
        }
    }

    private void validateResizeFields() throws BadAttributesException {
        if (instance.resizeWidth == null && instance.getResizeHeight() == null) return;
        if (instance.resizeWidth == null || instance.resizeHeight == null) {
            throw new BadAttributesException("not enough resize parameters");
        }
        if (instance.resizeHeight < 0 || instance.resizeWidth < 0) {
            throw new BadAttributesException("invalid resize parameters");
        }

    }

    private void validateBlurField() throws BadAttributesException {
        if (instance.radius != null && instance.radius <= 0) {
            throw new BadAttributesException("blur radius invalid!");
        }
    }

    private void validateFiles() throws BadAttributesException {
        if (instance.inputFile == null || instance.outputFile == null) {
            throw new BadAttributesException("files not found!");
        }
    }

    private void validateValue() throws BadAttributesException {
        if (instance.value != null && (instance.value < 1 || instance.value > 100)) {
            throw new BadAttributesException("Please check params!");
        }
        if (instance.value == null) {
            instance.value = STANDART_VALUE_QUALITY;
        }
    }

    private void validateOutputFormat() throws BadAttributesException {
        if (instance.format == null) {
            instance.format = "JPEG";
        }
        instance.format = instance.format.toLowerCase();
        if (!(instance.format.equals("jpeg") || instance.format.equals("png"))) {
            throw new BadAttributesException("format not JPEG or PNG!");
        }
    }

    private class Parser {
        void parseAllAttributes() throws BadAttributesException {
            parseFile();
            parseBlurAttribute();
            parseCropAttributes();
            parseQualityAttribute();
            parseResizeAttributes();
        }

        private void parseFile() throws BadAttributesException {
            if (instance.fileNames != null) {
                if (instance.fileNames.length != 2) {
                    throw new BadAttributesException("files not specified!");
                }
                try {
                    instance.inputFile = new File(instance.fileNames[0]);
                    instance.outputFile = new File(instance.fileNames[1]);
                } catch (Exception e) {
                    throw new BadAttributesException("uncorrect name of file!");
                }
            }
        }

        private void parseResizeAttributes() throws BadAttributesException {
            if (instance.resizeAttributes != null) {
                if (instance.resizeAttributes.length != 2) {
                    throw new BadAttributesException("resize attributes count != 2");
                }
                try {
                    instance.resizeWidth = Integer.parseInt(instance.resizeAttributes[0]);
                    instance.resizeHeight = Integer.parseInt(instance.resizeAttributes[1]);
                } catch (NumberFormatException e) {
                    throw new BadAttributesException("uncorrect resize attributes!");
                }
            }
        }

        private void parseQualityAttribute() throws BadAttributesException {
            if (instance.qualityAttribute != null) {
                try {
                    instance.value = Integer.parseInt(instance.qualityAttribute);
                } catch (NumberFormatException e) {
                    throw new BadAttributesException("uncorrect quality attribute");
                }
            }
        }

        private void parseCropAttributes() throws BadAttributesException {
            if (instance.cropAttributes != null) {
                if (instance.cropAttributes.length != 4) {
                    throw new BadAttributesException("crop attributes count != 4");
                }
                try {
                    instance.cropWidth = Integer.parseInt(instance.cropAttributes[0]);
                    instance.cropHeigth = Integer.parseInt(instance.cropAttributes[1]);
                    instance.cropX = Integer.parseInt(instance.cropAttributes[2]);
                    instance.cropY = Integer.parseInt(instance.cropAttributes[3]);
                } catch (NumberFormatException e) {
                    throw new BadAttributesException("uncorrect crop attributes!");
                }
            }
        }

        private void parseBlurAttribute() throws BadAttributesException {
            if (instance.blurAttribute != null) {
                try {
                    instance.radius = Integer.parseInt(instance.blurAttribute);
                } catch (NumberFormatException e) {
                    throw new BadAttributesException("uncorrect blur radius!");
                }
            }
        }

    }
}
