import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfGeneratorBobber {

    public static void main(String[] args) throws IOException {

        PDDocument document = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        document.addPage(firstPage);

        String appName = "КМПА Поплавок";
        String paperTitle = "РЕЗУЛЬТАТЫ УПРАЖНЕНИЯ";

        String exerciseTitle = "Название упражнения: ";
        String exerciseDate = "Дата проведения: ";
        String participantName = "Имя участника: ";
        String participantScore = "Оценка: ";

        String generalInfo = "Общая информация";

        String exerciseTime = "Время выполнения";
        String exerciseTimeMax = "Время, задание";
        String shotsOnTargetNumber = "Попаданий в цель";
        String shotsOnTargetNumberExpect = "Попаданий, ожидание";
        String distanceToTarget = "Расстояние до цели, м";
        String distanceToTargetCorrectionFactor = "Расстояние, корректировка";
        String boatSpeed = "Скорость корабля, узлы";
        String boatSpeedCorrectionFactor = "Скорость, корректировка";
        String difficultyWeightFactor = "Сложность упражнения";
        String technicalIssues = "Число отказов";
        String course = "Курс судна";
        String courseCorrectionfactor = "Курс, корректировка";

        String externalFactors = "Внешние факторы";

        String airTemperature = "Температура, °C";
        String seaState = "Волнение моря, баллы";
        String windSpeed = "Скорость ветра, м/с";
        String windDirection = "Направление ветра";

        Format d_Format = new SimpleDateFormat("dd/MM/yyyy");
        Format t_Format = new SimpleDateFormat("HH:mm");

        int pageWidth = (int) firstPage.getTrimBox().getWidth(); // 595
        int pageHeight = (int) firstPage.getTrimBox().getHeight(); // 841

        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
        PdfElementManager.TextManager textManager = new PdfElementManager.TextManager(document, contentStream);
        PdfElementManager.TableManager tableManager = new PdfElementManager.TableManager(document, contentStream);
        PdfElementManager.PictureManager pictureManager = new PdfElementManager.PictureManager(document, contentStream);

        File myFontRegular = new File("font/verdana.ttf");
        File myFontItalic = new File("font/verdana_italic.ttf");
        File myFontBold = new File("font/verdana_bold.ttf");

        PDFont regularFont = PDType0Font.load(document, myFontRegular);
        PDFont italicFont = PDType0Font.load(document, myFontItalic);
        PDFont boldFont = PDType0Font.load(document, myFontBold);

        PDImageXObject appLogo = PDImageXObject.createFromFile("img/app_logo_new_v5_transparent.png", document);
        PDImageXObject targetImage = PDImageXObject.createFromFile("img/target_img_new_transparent_v2.png", document);
        PDImageXObject devLogo = PDImageXObject.createFromFile("img/dev_logo_transparent.png", document);

        textManager.addSingleLineTextCentered(appName,
                (int) (pageHeight - 81 + ((appLogo.getHeight() * 0.1) / 2)),
                boldFont, 18, Color.BLACK);
        pictureManager.addPictureRightAligned(appLogo, pageHeight - 81, (float) 0.1);

        textManager.addSingleLineTextCentered(paperTitle, (int) (pageHeight - 121), regularFont, 22, Color.BLACK);

        textManager.addSingleLineTextLeftCentered(exerciseTitle, (int) (pageHeight - 151), regularFont, 12,
                Color.BLACK);
        textManager.addSingleLineTextRightCentered("", (int) (pageHeight - 151), regularFont, 12, Color.BLACK);
        textManager.addSingleLineTextLeftCentered(exerciseDate, (int) (pageHeight - 171), regularFont, 12,
                Color.BLACK);
        textManager.addSingleLineTextRightCentered("", (int) (pageHeight - 171), regularFont, 12, Color.BLACK);
        textManager.addSingleLineTextLeftCentered(participantName, (int) (pageHeight - 191), regularFont, 12,
                Color.BLACK);
        textManager.addSingleLineTextRightCentered("", (int) (pageHeight - 191), regularFont, 12,
                Color.BLACK);
        textManager.addSingleLineTextLeftCentered(participantScore, (int) (pageHeight - 211),
                regularFont, 12, Color.BLACK);
        textManager.addSingleLineTextRightCentered("", (int) (pageHeight - 211), regularFont, 12,
                Color.BLACK);

        textManager.addSingleLineTextLeftAligned(generalInfo, (int) (pageHeight -
                241), italicFont, 14, Color.BLACK);

        int[] cellWidthTab1 = { 162, 100, 162, 100 };
        tableManager.setTable(cellWidthTab1, 20, 35, (int) (pageHeight - 271));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(exerciseTime, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(exerciseTimeMax, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(shotsOnTargetNumber, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(shotsOnTargetNumberExpect, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(distanceToTarget, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(distanceToTargetCorrectionFactor, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(boatSpeed, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(boatSpeedCorrectionFactor, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(course, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(courseCorrectionfactor, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(technicalIssues, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(difficultyWeightFactor, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);

        textManager.addSingleLineTextLeftAligned(externalFactors, (int) (pageHeight -
                401), italicFont, 14, Color.BLACK);

        int[] cellWidthTab2 = { 162, 100, 162, 100 };
        tableManager.setTable(cellWidthTab2, 20, 35, (int) (pageHeight - 431));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(airTemperature, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(seaState, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(windSpeed, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell(windDirection, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);

        pictureManager.addPictureCentered(targetImage, pageHeight - 781, (float) 0.5);

        pictureManager.addPictureCentered(devLogo, pageHeight - 821, (float) 0.04);

        textManager.addSingleLineTextRightAligned(
                "Отметка времени: " + d_Format.format(new Date()) + ", " + t_Format.format(new Date()),
                (int) pageHeight - 821, regularFont, 8, Color.BLACK);

        contentStream.close();
        document.save("F:/bobber_res.pdf");
        document.close();
        System.out.println("PDF done");
    }
}