package com.coral.base.common.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author huss
 * @version 1.0
 * @className PdfWatermark
 * @description 水印实现
 * @date 2021/9/23 16:39
 */
public class PdfWatermark extends PdfPageEventHelper {
    Font FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new GrayColor(0.95f));
    /**
     * 水印内容
     */
    private String waterCont;

    public PdfWatermark() {

    }

    public PdfWatermark(String waterCont) {
        this.waterCont = waterCont;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_CENTER,
                        new Phrase(this.waterCont == null ? "HELLO WORLD" : this.waterCont, FONT),
                        (50.5f + i * 350),
                        (40.0f + j * 150),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }
    }
}
