package com.report.service.impl;


import com.report.service.JasperService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JasperServiceImpl implements JasperService {

    /**
     * Method for exporting the report
     * @param reportPath Destination for created report file
     * @param params Params which will be passed to the report
     * @param collection Collection with data which will be passed to report
     * @return InputStream
     */
    @Override
    public InputStream exportReportToPDF(String reportPath, Map<String, Object> params, Collection<?> collection) {

        try {
            byte[] bytes = JasperExportManager.exportReportToPdf(preparePrint(getReportDesign(reportPath), params, collection));
            return new ByteArrayInputStream(bytes);
        } catch (JRException | IOException e) {
            throw new RuntimeException(e.getMessage()); // custom
        }
    }

    private InputStream getReportDesign(String reportPath) throws IOException {
        return new FileInputStream(new ClassPathResource(reportPath).getFile());
    }

    private JasperPrint preparePrint(InputStream reportXml, Map<String, Object> params, Collection<?> collection) throws JRException {
        JasperReport jasperReport = compileReport(reportXml);
        return collection != null ?
                JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(collection)) :
                JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    }

    private JasperReport compileReport(InputStream inputStream) throws JRException {
        return JasperCompileManager.compileReport(inputStream);
    }
}
