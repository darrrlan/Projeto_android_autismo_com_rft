/*
    Copyright (C) 2024  Lucio A. Rocha

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package br.edu.utfpr.darlantcc_v1;

import android.util.Log;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public final class PageNumeration extends PdfPageEventHelper
{
   private static String TAG        = PageNumeration.class.getSimpleName();
   private static Font FONT_FOOTER  = new Font(Font.FontFamily.TIMES_ROMAN,  8, Font.NORMAL, BaseColor.DARK_GRAY);

   public PageNumeration()
   {

   }

   @Override
   public void onEndPage(PdfWriter writer, Document document)
   {
      try
      {
         PdfPCell cell;
         PdfPTable table = new PdfPTable(2);
         table.setWidthPercentage(100);
         table.setWidths(new float[]{3,1});

         //1st Column
         Anchor anchor = new Anchor(new Phrase("DARLANTCC", FONT_FOOTER));
         anchor.setReference( "http://darlantcc.com.br");
         cell = new PdfPCell(anchor);

         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         cell.setBorder(0);
         cell.setPadding(2f);
         table.addCell(cell);
         table.setTotalWidth(document.getPageSize().getWidth()-document.leftMargin()-document.rightMargin());
         table.writeSelectedRows(0,-1,document.leftMargin(),document.bottomMargin()-5,writer.getDirectContent());

         //2nd Column
         cell = new PdfPCell(new Phrase("Página - ".concat(String.valueOf(writer.getPageNumber())), FONT_FOOTER));
         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         cell.setBorder(0);
         cell.setPadding(2f);
         table.addCell(cell);
         table.setTotalWidth(document.getPageSize().getWidth()-document.leftMargin()-document.rightMargin());
         table.writeSelectedRows(0,-1,document.leftMargin(),document.bottomMargin()-5,writer.getDirectContent());
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         Log.e(TAG,ex.toString());
      }
   }
}