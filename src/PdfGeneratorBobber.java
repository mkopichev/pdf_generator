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
        String paperTitle = "РЕЗУЛЬТАТЫ";
        String generalInfo = "Общая информация";

        String exerciseDate = "Дата:";
        String exerciseTitle = "Упражнение:";
        String participantName = "Участник:";
        String participantScore = "Оценка:";
        String shotsOnTargetNumber = "Попаданий:";
        String exerciseTime = "Время:";

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

        textManager.addSingleLineTextCentered(appName, (int) (pageHeight - 81 + ((appLogo.getHeight() * 0.1) / 3)),
                boldFont, 20, Color.BLACK);
        pictureManager.addPictureRightAligned(appLogo, pageHeight - 81, (float) 0.1);

        textManager.addSingleLineTextCentered(paperTitle, (int) (pageHeight - 111), regularFont, 24, Color.BLACK);

        textManager.addSingleLineTextLeftAligned(generalInfo, (int) (pageHeight - 141), italicFont, 14, Color.BLACK);

        int[] cellWidth = { 120, 160, 245 };
        tableManager.setTable(cellWidth, 25, 35, (int) (pageHeight - 171));
        tableManager.setTableFont(regularFont, 10, Color.BLACK);

        tableManager.addCell(exerciseDate, Color.LIGHT_GRAY);
        tableManager.addCell(exerciseTitle, Color.LIGHT_GRAY);
        tableManager.addCell(participantName, Color.LIGHT_GRAY);

        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);
        tableManager.addCell("", Color.WHITE);

        tableManager.addCell(participantScore, Color.LIGHT_GRAY);
        tableManager.addCell(shotsOnTargetNumber, Color.LIGHT_GRAY);
        tableManager.addCell(exerciseTime, Color.LIGHT_GRAY);

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