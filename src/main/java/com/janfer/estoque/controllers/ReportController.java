package com.janfer.estoque.controllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/generate-report")
    public ResponseEntity<Resource> generateReport(@RequestParam("ID_ORDEM_COMPRA") Long idOrdemCompra) throws JRException, IOException, SQLException {
        // Caminho para o arquivo do relatório
        File file = ResourceUtils.getFile("classpath:reports/ordemCompra/OrdemCompraItem.jrxml");

        // Carregar o relatório
//        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(String.valueOf(file));
        JasperReport jasperReport = JasperCompileManager.compileReport(String.valueOf(file));

        // Parâmetros para o relatório
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID_ORDEM_COMPRA", idOrdemCompra);

        // Obter a conexão do banco de dados
        Connection connection = dataSource.getConnection();

        // Preencher o relatório com os parâmetros e a conexão
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

        // Gerar o PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Criar um ByteArrayInputStream a partir do ByteArrayOutputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // Criar um ByteArrayResource a partir do ByteArrayInputStream
        ByteArrayResource resource = new ByteArrayResource(inputStream.readAllBytes());

        // Definir o tipo de conteúdo da resposta como PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OrdemCompra.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

