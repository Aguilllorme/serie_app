package src.main.java.dad.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Jasper {

    JasperReport reporte;
    {
        try {
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(getResource("Series_report.jasper").toString()));
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    JRDataSource jrDataSource = new JREmptyDataSource();

    JasperPrint jasperPrint = new JasperPrint();
    {
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, null,jrDataSource);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    JRPdfExporter  exporter = new JRPdfExporter();

}
