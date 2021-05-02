package ru.ecom.report.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Milamesher on 21.09.2018.
 * Для работы с QR-кодами.
 */
public class QRCodeHelper {
    private final String QR_CODE_filename = "tmpFileForQRCode";

    //Метод возвращает qr-код с текстом, размерами QR_w и QR_h. default QR_TYPE PNG
    public String generateQRCodeImageBase64(String QR_text, int QR_w, int QR_h, String QR_TYPE) {
        if (QR_TYPE == null || QR_TYPE.equals("")) QR_TYPE = "PNG";
        String QR_CODE_IMAGE_PATH = QR_CODE_filename + "." + QR_TYPE;
        String base64 = null;
        try {
            MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(QR_text, BarcodeFormat.QR_CODE, QR_w, QR_h), QR_TYPE, new FileOutputStream(QR_CODE_IMAGE_PATH));
            base64 = encodeToString(ImageIO.read(new File(QR_CODE_IMAGE_PATH)), QR_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    //Milamesher #120 14092018  метод получения строки в base64 из файла с изображением
    private String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            imageString = new BASE64Encoder().encode(bos.toByteArray());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    //Milamesher #120 19092018 метод вставки qr-кода в файл
    public static Boolean createInsertQRCode(String qrText, int qrWeight, int qrHeight, String qrType, String template, String ext, String replaceSource) {
        boolean flag = false;
        if (qrText != null && !qrText.equals("") && qrWeight != 0 && qrHeight != 0 && template != null && !template.equals("") && ext != null && !ext.equals("")
                && replaceSource != null && !replaceSource.equals("")) {
            flag = true;
            if (qrType == null || qrType.equals("")) qrType = "PNG";
            String QR_CODE_IMAGE_PATH = template.replace(ext, "") + "." + qrType;
            try {
                MatrixToImageWriter.writeToStream(new QRCodeWriter().encode(qrText, BarcodeFormat.QR_CODE, qrWeight, qrHeight), qrType, new FileOutputStream(QR_CODE_IMAGE_PATH));
                File file = new File(QR_CODE_IMAGE_PATH);
                if (file.exists()) {
                    flag = putQRImage(file.toURI(), template, replaceSource);
                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    //Milamesher #120 130092018  метод замены кодового слова на QR-код
    private static Boolean putQRImage(URI uri, String template, String replaceSource) {
        try {
            Document textdoc;
            if (template.endsWith(".odt")) {
                textdoc = TextDocument.loadDocument(template);
                TextNavigation search = new TextNavigation(replaceSource, textdoc);
                if (uri != null) {
                    while (search.hasNext()) {
                        TextSelection item = (TextSelection) search.nextSelection();
                        try {
                            if (item != null) item.replaceWith(uri);
                        } catch (NullPointerException e) {
                        }
                    }
                }
            }
            //Milamesher #120 19112018 работа с .ods файлом
            else if (template.endsWith(".ods")) {
                textdoc = SpreadsheetDocument.loadDocument(template);
                if (!textdoc.getTableList().isEmpty()) {
                    Table t = textdoc.getTableList().get(0);
                    boolean flag = false;
                    for (int i = 0; i < t.getRowCount(); i++) {
                        for (int j = 0; i < t.getRowByIndex(i).getCellCount(); j++) {
                            Cell c = t.getCellByPosition(i, j);
                            if (c.getDisplayText().contains(replaceSource)) {
                                c.setImage(uri);
                                flag = true;
                                break;
                            }
                            if (flag) break;
                        }
                    }
                }
            } else {
                textdoc = null;
            }
            if (textdoc != null) textdoc.save(template);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}