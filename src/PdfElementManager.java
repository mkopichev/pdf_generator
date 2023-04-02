import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfElementManager {

    public static class TextManager {

        PDDocument document;
        PDPageContentStream contentStream;

        public TextManager(PDDocument document, PDPageContentStream contentStream) {

            this.document = document;
            this.contentStream = contentStream;
        }

        void addSingleLineText(String text, int xPos, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.newLineAtOffset(xPos, yPos);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.moveTo(0, 0);
        }

        void addSingleLineTextCentered(String text, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            int xPosCentered = (int) ((document.getPage(0).getTrimBox().getWidth()
                    - TextManager.getTextWidth(text, font, fontSize)) / 2);
            addSingleLineText(text, xPosCentered, yPos, font, fontSize, color);
        }

        void addSingleLineTextLeftCentered(String text, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            int xPosLeftCentered = (int) ((document.getPage(0).getTrimBox().getWidth() / 2)
                    - TextManager.getTextWidth(text, font, fontSize));
            addSingleLineText(text, xPosLeftCentered, yPos, font, fontSize, color);
        }

        void addSingleLineTextRightCentered(String text, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            int xPosRightCentered = (int) (document.getPage(0).getTrimBox().getWidth() / 2);
            addSingleLineText(text, xPosRightCentered, yPos, font, fontSize, color);
        }

        void addSingleLineTextLeftAligned(String text, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            int xPosLeftAligned = (int) ((document.getPage(0).getTrimBox().getWidth()) * 0.05);
            addSingleLineText(text, xPosLeftAligned, yPos, font, fontSize, color);
        }

        void addSingleLineTextRightAligned(String text, int yPos, PDFont font, float fontSize, Color color)
                throws IOException {

            int xPosRightAligned = (int) (document.getPage(0).getTrimBox().getWidth()
                    - TextManager.getTextWidth(text, font, fontSize) - 35);
            addSingleLineText(text, xPosRightAligned, yPos, font, fontSize, color);
        }

        void addMultipleLineText(String[] textArray, float leading, int xPos, int yPos, PDFont font, float fontSize,
                Color color) throws IOException {

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.setLeading(leading);
            contentStream.newLineAtOffset(xPos, yPos);
            for (String text : textArray) {
                contentStream.showText(text);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.moveTo(0, 0);
        }

        static float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
            return font.getStringWidth(text) / 1000 * fontSize;
        }
    }

    public static class TableManager {

        PDDocument document;
        PDPageContentStream contentStream;
        private int[] colWidth;
        private int cellHeight;
        private int xPos;
        private int yPos;
        private int colPos = 0;
        private int xInitPos;
        private float fontSize;
        private PDFont font;
        private Color fontColor;

        public TableManager(PDDocument document, PDPageContentStream contentStream) {

            this.document = document;
            this.contentStream = contentStream;
        }

        void setTable(int[] colWidth, int cellHeight, int xPos, int yPos) {

            this.colWidth = colWidth;
            this.cellHeight = cellHeight;
            this.xPos = xPos;
            this.yPos = yPos;
            xInitPos = xPos;
        }

        void setTableFont(PDFont font, float fontSize, Color fontColor) {

            this.font = font;
            this.fontSize = fontSize;
            this.fontColor = fontColor;
        }

        void addCell(String text, Color fillColor) throws IOException {

            contentStream.setStrokingColor(0f);
            if (fillColor != null)
                contentStream.setNonStrokingColor(fillColor);

            contentStream.addRect(xPos, yPos, colWidth[colPos], cellHeight);
            if (fillColor == null)
                contentStream.stroke();
            else
                contentStream.fillAndStroke();

            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(fontColor);

            contentStream.newLineAtOffset(
                    xPos + ((colWidth[colPos] - TextManager.getTextWidth(text, font, fontSize)) / 2),
                    yPos + (fontSize / 2) + 2);

            contentStream.showText(text);
            contentStream.endText();

            xPos = xPos + colWidth[colPos];
            colPos++;

            if (colPos == colWidth.length) {
                colPos = 0;
                xPos = xInitPos;
                yPos -= cellHeight;
            }
        }
    }

    public static class PictureManager {

        PDDocument document;
        PDPageContentStream contentStream;

        public PictureManager(PDDocument document, PDPageContentStream contentStrem) {

            this.document = document;
            this.contentStream = contentStrem;
        }

        void addPictureCentered(PDImageXObject picture, int yPos, float scale) throws IOException {

            int xPosCentered = (int) ((document.getPage(0).getTrimBox().getWidth()
                    - picture.getWidth() * (scale * 100) / 100) / 2);
            contentStream.drawImage(picture, xPosCentered, yPos, picture.getWidth() * (scale * 100) / 100,
                    picture.getHeight() * (scale * 100) / 100);
        }

        void addPictureRightAligned(PDImageXObject picture, int yPos, float scale) throws IOException {

            int xPosRightAligned = (int) ((document.getPage(0).getTrimBox().getWidth()
                    - (picture.getWidth() * (scale * 100) / 100) * 2));
            contentStream.drawImage(picture, xPosRightAligned, yPos, picture.getWidth() * (scale * 100) / 100,
                    picture.getHeight() * (scale * 100) / 100);
        }

        void addPictureLeftAligned(PDImageXObject picture, int yPos, float scale) throws IOException {

            int xPosLeftAligned = (int) ((picture.getWidth() * (scale * 100) / 100));
            contentStream.drawImage(picture, xPosLeftAligned, yPos, picture.getWidth() * (scale * 100) / 100,
                    picture.getHeight() * (scale * 100) / 100);
        }
    }

}
