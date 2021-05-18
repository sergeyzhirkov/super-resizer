package mts.teta.resizer.imageprocessor;

import marvin.image.MarvinImage;
import marvinplugins.MarvinPluginCollection;
import mts.teta.resizer.attributevalidator.ConsoleAttributes;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

public class ImageProcessor {
    private static final double STANDART_FULL_QUALITY = 100.001;
    private static final int STANDART_SCALE = 1;
    private ConsoleAttributes att;

    public void processImage(BufferedImage inputImage, ConsoleAttributes attributes) throws Exception {

        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.att = attributes;

        Optional<BufferedImage> bufImage = Optional.of(inputImage);
        bufImage.map(imageProcessor::resize)
                .map(imageProcessor::crop)
                .map(imageProcessor::blur)
                .map(imageProcessor::compressAndOutput);

    }

    private BufferedImage resize(BufferedImage inImage) {
        if (att.getResizeWidth() != null) {
            try {
                return Thumbnails.of(inImage).forceSize(att.getResizeWidth(), att.getResizeHeight()).asBufferedImage();
            } catch (IOException e) {
                System.out.println("Resize operation cannot be performed, continue without it");
            }
        }
        return inImage;
    }

    private BufferedImage crop(BufferedImage inImage) {
        if (att.getCropX() != null) {
            boolean isEnoughWidth = att.getCropX() + att.getCropWidth() < inImage.getWidth();
            boolean isEnoughHeigth = att.getCropY() + att.getCropHeigth() < inImage.getHeight();
            if (isEnoughWidth && isEnoughHeigth) {
                return inImage.getSubimage(
                        att.getCropX(),
                        att.getCropY(),
                        att.getCropWidth(),
                        att.getCropHeigth());
            } else {
                System.out.println("Crop operation cannot be performed, continue without it");
            }
        }

        // Реализация через BufferedImage getSubimage(...)
        // работает значительно быстрее, чем через MarvinPluginCollection.crop(...)
        // но в задании рекомендуют библиотеку Марвин, поэтому этот вариант в комментах:

//        if (att.getCropX() != null) {
//            MarvinImage marvinImage = new MarvinImage(inImage);
//            MarvinPluginCollection.crop(
//                    marvinImage.clone(),
//                    marvinImage,
//                    att.getCropX(),
//                    att.getCropY(),
//                    att.getCropWidth(),
//                    att.getCropHeigth());
//            marvinImage.update();
//            return marvinImage.getBufferedImage();
//        }

        return inImage;
    }

    private BufferedImage blur(BufferedImage inImage) {
        if (att.getRadius() != null) {
            MarvinImage marvinImage = new MarvinImage(inImage);
            MarvinPluginCollection.gaussianBlur(marvinImage.clone(), marvinImage, att.getRadius());
            marvinImage.update();
            return marvinImage.getBufferedImage();
        }
        return inImage;
    }

    private BufferedImage compressAndOutput(BufferedImage inImage) {
        try {
            Thumbnails.of(inImage)
                    .outputFormat(att.getFormat())
                    .scale(STANDART_SCALE)
                    .outputQuality(att.getValue() / STANDART_FULL_QUALITY)
                    .toFile(att.getOutputFile());
        } catch (IOException e) {
            System.out.println("Output operation cannot be performed, continue without it");
        }
        return inImage;
    }

}
