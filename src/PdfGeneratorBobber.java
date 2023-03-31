import java.awt.Color;
import java.io.File;
import java.io.IOException;
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

        String generalInfo = "Общая информация";

        String exerciseDate = "Дата:";
        String exerciseTitle = "Упражнение:";
        String distanceToTarget = "Расстояние до цели:";
        String exerciseTimeMax = "Время / предельное:";
        String participantName = "Участник:";
        String participantScore = "Оценка:";
        String shotsOnTargetNumber = "Попаданий / ожидание:";
        String difficultyWeightfactor = "Сложность:";

        String externalFactors = "Внешние факторы";

        String airTemperature = "Температура:";
        String seaState = "Волнение моря:";
        String windSpeed = "Скорость ветра:";
        String windDirection = "Направление ветра:";
        String boatSpeed = "Скорость корабля:";

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
                boldFont, 20, Color.BLACK);
        pictureManager.addPictureRightAligned(appLogo, pageHeight - 81, (float) 0.1);

        textManager.addSingleLineTextCentered(paperTitle, (int) (pageHeight - 111), regularFont, 24, Color.BLACK);

        textManager.addSingleLineTextLeftAligned(generalInfo, (int) (pageHeight - 141), italicFont, 14, Color.BLACK);

        int[] cellWidthTab1 = { 100, 180, 245 };
        tableManager.setTable(cellWidthTab1, 25, 35, (int) (pageHeight - 181));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(exerciseDate, Color.LIGHT_GRAY);
        tableManager.addCell(exerciseTitle, Color.LIGHT_GRAY);
        tableManager.addCell(participantName, Color.LIGHT_GRAY);

        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        tableManager.addCell(participantScore, Color.LIGHT_GRAY);
        tableManager.addCell(exerciseTimeMax, Color.LIGHT_GRAY);
        tableManager.addCell(distanceToTarget, Color.LIGHT_GRAY);

        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        int[] cellWidthTab1_2 = { 280, 245 };
        tableManager.setTable(cellWidthTab1_2, 25, 35, (int) (pageHeight - 281));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(shotsOnTargetNumber, Color.LIGHT_GRAY);
        tableManager.addCell(difficultyWeightfactor, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        textManager.addSingleLineTextLeftAligned(externalFactors, (int) (pageHeight - 331), italicFont, 14, Color.BLACK);

        int[] cellWidthTab2 = { 175, 175, 175 };
        tableManager.setTable(cellWidthTab2, 25, 35, (int) (pageHeight - 371));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(airTemperature, Color.LIGHT_GRAY);
        tableManager.addCell(seaState, Color.LIGHT_GRAY);
        tableManager.addCell(windSpeed, Color.LIGHT_GRAY);

        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        tableManager.addCell(windDirection, Color.LIGHT_GRAY);
        tableManager.addCell(boatSpeed, Color.LIGHT_GRAY);
        tableManager.addCell("", Color.WHITE);

        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        pictureManager.addPictureCentered(targetImage, pageHeight - 781, (float) 0.4);

        pictureManager.addPictureCentered(devLogo, pageHeight - 821, (float) 0.04);

        contentStream.close();
        document.save("/Users/michael/Downloads/myPDF.pdf");
        document.close();
        System.out.println("PDF done");
    }
}