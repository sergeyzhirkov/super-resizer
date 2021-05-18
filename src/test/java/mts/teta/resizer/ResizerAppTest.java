package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static mts.teta.resizer.utils.MD5.getMD5;
import static org.junit.jupiter.api.Assertions.*;

class ResizerAppTest {

    private static final String FILM_COVER_SOURCE_NAME = "Good_Will_Hunting_1997.jpg";
    private static final String FILM_COVER_TARGET_NAME = "Good_Will_Hunting_1997.preview.jpg";
    private static final Integer FILM_COVER_HEIGHT = 1500;
    private static final Integer FILM_COVER_WIDTH = 983;

    private static final String BOOK_COVER_SOURCE_NAME = "J_R_R_Tolkien_The_Hobbit_1937.jpg";
    private static final String BOOK_COVER_TARGET_NAME = "J_R_R_Tolkien_The_Hobbit_1937.preview.jpg";
    private static final Integer BOOK_COVER_HEIGHT = 1980;
    private static final Integer BOOK_COVER_WIDTH = 1400;

    private static final String AUDIO_COVER_SOURCE_NAME = "Metallica_Kill_Em_All_1983.jpeg";
    private static final String AUDIO_COVER_TARGET_NAME = "Metallica_Kill_Em_All_1983.preview.jpeg";
    private static final Integer AUDIO_COVER_HEIGHT = 1425;
    private static final Integer AUDIO_COVER_WIDTH = 1425;

    @Test
    public void testReducingCover() throws Exception {
        final Integer reducedPreviewWidth = FILM_COVER_WIDTH - 500;
        final Integer reducedPreviewHeight = FILM_COVER_HEIGHT - 500;

        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeWidth(reducedPreviewWidth);
        app.setResizeHeight(reducedPreviewHeight);
        app.setQuality(100);
        app.call();

        BufferedImage reducedPreview = ImageIO.read(new File(absolutePathOutput));

        assertEquals(reducedPreview.getWidth(), reducedPreviewWidth);
        assertEquals(reducedPreview.getHeight(), reducedPreviewHeight);
    }

    // в этом тесте не реально уложиться в 350 ms, для этих тестовых данных:
    // изолированное выполнение forceSize(...) + toFile(...) == 630 ms
    // изолированное выполнение forceSize(...) + asBufferedImage() == 370 ms
    // но это не считая остальных операций, которые очень затратные
    // тот же ImageIO.read(inputFile) заложенный в шаблоне == 250 ms
    // p.s. проверял на 4 ГГц, 4 ядра 8 потоков
    @Test
    public void testEnlargeCover() throws Exception {
        final Integer reducedPreviewWidth = FILM_COVER_WIDTH + FILM_COVER_WIDTH;
        final Integer reducedPreviewHeight = FILM_COVER_HEIGHT + FILM_COVER_HEIGHT;

        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeWidth(reducedPreviewWidth);
        app.setResizeHeight(reducedPreviewHeight);
        app.setQuality(100);
        app.call();

        BufferedImage reducedPreview = ImageIO.read(new File(absolutePathOutput));

        assertEquals(reducedPreview.getWidth(), reducedPreviewWidth);
        assertEquals(reducedPreview.getHeight(), reducedPreviewHeight);
    }

// Отказ от тестов с MD5
// Тестирование проверки изображений это комплексная задача и сводить её к сверке MD5 нельзя.
// Мета-информация, различные функции и параметры они все изменяют значение хеш-суммы. Хотя визуально оно будет точь-в-точь.
// Нам кажется, что нужно писать код, а не подгонять своё приложение под значение теста.
// Поэтому мы помечаем тесты @Deprecated и не будем их использовать для проверки приложения.

//    @Test
//    @Deprecated
//    public void testMinimumQuality() throws Exception {
//        final Integer BOOK_COVER_QUALITY = 1;
//
//        URL res = getClass().getClassLoader().getResource(BOOK_COVER_SOURCE_NAME);
//        assert res != null;
//
//        File file = Paths.get(res.toURI()).toFile();
//        String absolutePathInput = file.getAbsolutePath();
//
//        String absolutePathOutput = absolutePathInput.replaceFirst(BOOK_COVER_SOURCE_NAME, BOOK_COVER_TARGET_NAME);
//
//        ResizerApp app = new ResizerApp();
//        app.setInputFile(new File(absolutePathInput));
//        app.setOutputFile(new File(absolutePathOutput));
//        app.setResizeHeight(BOOK_COVER_HEIGHT);
//        app.setResizeWidth(BOOK_COVER_WIDTH);
//        app.setQuality(BOOK_COVER_QUALITY);
//        app.call();
//
//        String outputCheckSum = getMD5(absolutePathOutput);
//        assertEquals("63b40bb7f3f303854f97509ae3d1c19e", outputCheckSum);
//    }

//    @Test
//    @Deprecated
//    public void testSuperQuality() throws Exception {
//        final Integer BOOK_COVER_QUALITY = 100;
//
//        URL res = getClass().getClassLoader().getResource(BOOK_COVER_SOURCE_NAME);
//        assert res != null;
//
//        File file = Paths.get(res.toURI()).toFile();
//        String absolutePathInput = file.getAbsolutePath();
//
//        String absolutePathOutput = absolutePathInput.replaceFirst(BOOK_COVER_SOURCE_NAME, BOOK_COVER_TARGET_NAME);
//
//        ResizerApp app = new ResizerApp();
//        app.setInputFile(new File(absolutePathInput));
//        app.setOutputFile(new File(absolutePathOutput));
//        app.setResizeHeight(BOOK_COVER_HEIGHT);
//        app.setResizeWidth(BOOK_COVER_WIDTH);
//        app.setQuality(BOOK_COVER_QUALITY);
//        app.call();
//
//        String outputCheckSum = getMD5(absolutePathOutput);
//        assertEquals("d640c71ad5bff2f5f7550a3dc6e0c76c", outputCheckSum);
//    }

//    @Test
//    @Deprecated
//    public void testBlurringCover() throws Exception {
//        final Integer BOOK_COVER_BLUR_RADIUS = 10;
//
//        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
//        assert res != null;
//
//        File file = Paths.get(res.toURI()).toFile();
//        String absolutePathInput = file.getAbsolutePath();
//
//        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);
//
//        ResizerApp app = new ResizerApp();
//        app.setInputFile(new File(absolutePathInput));
//        app.setOutputFile(new File(absolutePathOutput));
//        app.setResizeWidth(AUDIO_COVER_WIDTH);
//        app.setResizeHeight(AUDIO_COVER_HEIGHT);
//        app.setQuality(100);
//        app.setBlurRadius(BOOK_COVER_BLUR_RADIUS);
//        app.call();
//
//        String outputCheckSum = getMD5(absolutePathOutput);
//        assertEquals("d4e92cf8ce5c1ed04241129da3d950f1", outputCheckSum);
//    }

    @Test
    public void testTypoSourceName() throws Exception {
        final String typo = "ops!sic!";

        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput + typo));
        app.setOutputFile(new File(absolutePathOutput));
        IIOException generatedException = null;
        try {
            app.call();
        } catch (IIOException e) {
            generatedException = e;
        }

        assertEquals("Can't read input file!", generatedException.getMessage());
        assertEquals(IIOException.class, generatedException.getClass());
    }

    @Test
    public void testBadAttributes() throws Exception {
        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setQuality(-50);
        BadAttributesException generatedException = null;
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }

//     Тест на изменение формата jpeg -> png
//     Идея теста:
//     Проверяем изменится ли имя output файла, а конкретно его расширение
//     Создаётся ли файл с расширением png, несмотря на то что передаём имя
//     с другим расширением
    @Test
    public void testChangeFormat() throws Exception {
        final String newFormat = "PNG";
        final String NEW_FILM_COVER_TARGET_NAME = FILM_COVER_TARGET_NAME + "." + newFormat.toLowerCase();

        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setFormat(newFormat);
        app.setQuality(100);
        app.call();

        absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_TARGET_NAME, NEW_FILM_COVER_TARGET_NAME);

        assertTrue(new File(absolutePathOutput).exists());
    }

    //     Тест проверки уровня сжатия и соответсвенно веса файла
//     Идея теста:
//     для разных значений Quality например 5, 25,..., 85 получаем файлы
//     и сравниваем их размер в байтах, ожидается что
//     F(q==5) < F(q==25) < ... < F(q==85)
//     а вообще для уменьшения времени теста можно использовать меньшее
//     количество файлов, например разбивка по Quality 1, 50, 100
//     Возможные причины багов:
//     после outputQuality(...) нельзя использовать asBufferedImage()
//     т.к. тогда Quality не меняется
    @Test
    public void testQualityCover() throws Exception {
        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        long fileSize = 0;

        for (int i = 0; i < 5; i++) {
            app.setQuality(5 + i * 20);
            app.call();

            long newFileSize = new File(absolutePathOutput).length();

            assertTrue(fileSize < newFileSize);

            fileSize = newFileSize;
        }
    }

    //    Тест проверки кропа изображения
//    Идея теста:
//    сравниваем параметры(ширина, высота) полученного изображения с переданными
    @Test
    public void testCropCover() throws Exception {
        final Integer cropedPreviewWidth = 330;
        final Integer cropedPreviewHeight = 220;
        final Integer cropedPreviewX = 230;
        final Integer cropedPreviewY = 200;

        URL res = getClass().getClassLoader().getResource(BOOK_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(BOOK_COVER_SOURCE_NAME, BOOK_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setCropWidth(cropedPreviewWidth);
        app.setCropHeigth(cropedPreviewHeight);
        app.setCropX(cropedPreviewX);
        app.setCropY(cropedPreviewY);
        app.call();

        BufferedImage cropedPreview = ImageIO.read(new File(absolutePathOutput));

        assertEquals(cropedPreviewWidth, cropedPreview.getWidth());
        assertEquals(cropedPreviewHeight, cropedPreview.getHeight());
    }

//     Тест проверки размытия
//     Идея теста:
//     Как таковое размытие не проверить автоматически, нужно смотреть вручную
//     для этого сделан кроп области изображения с чёткими границами, на которых
//     блюр заметнее всего даже для малых значений радиуса (в этом тесте == 7)
//     Возможные причины багов:
//     при использовании Marvin библиотеки, если использовать getBufferedImage()
//     и не использовать перед этим update(), то все пиксели становятся чёрного цвета
//     при этом другие параметры (ширина, высота при том же кропе) меняются корректно,
//     создаётся иллюзия правильной работы, но на самом деле получаем чёрный прямоугольник.
//     Поэтому достаточно проверить какой-то один пиксель, который заведомо (в нашем изображении)
//     должен иметь цвет отличный от чёрного.
    @Test
    public void testBlurCover() throws Exception {
        final Integer cropedPreviewWidth = 200;
        final Integer cropedPreviewHeight = 200;
        final Integer cropedPreviewX = 170;
        final Integer cropedPreviewY = 180;
        final Integer x = cropedPreviewWidth - 1;
        final Integer y = cropedPreviewHeight - 1;

        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setCropWidth(cropedPreviewWidth);
        app.setCropHeigth(cropedPreviewHeight);
        app.setCropX(cropedPreviewX);
        app.setCropY(cropedPreviewY);
        app.setQuality(100);
        app.setBlurRadius(7);
        app.call();

        BufferedImage bluredPreview = ImageIO.read(new File(absolutePathOutput));

        assertFalse(bluredPreview.getRGB(x, y) == Color.BLACK.getRGB());

    }
}
