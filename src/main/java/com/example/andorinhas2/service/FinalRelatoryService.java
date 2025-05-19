package com.example.andorinhas2.service;


import com.example.andorinhas2.model.MonthlyTable;
import com.example.andorinhas2.model.RegistrationTable;
import com.example.andorinhas2.model.SpentTable;
import com.example.andorinhas2.repository.MonthlyRepository;
import com.example.andorinhas2.repository.RegistrationRepository;
import com.example.andorinhas2.repository.SpentRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinalRelatoryService{

    @Autowired
    private RegistrationRepository registroRepository;
    private MonthlyRepository monthlyRepository;
    private SpentRepository spentRepository;

    public FinalRelatoryService (RegistrationRepository registrationRepository, MonthlyRepository monthlyRepository, SpentRepository spentRepository){
        this.monthlyRepository = monthlyRepository;
        this.registroRepository = registrationRepository;
        this.spentRepository = spentRepository;
    }

    public byte[] gerarRelatorioMensal(LocalDate mesReferencia) throws IOException {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph titulo = doc.createParagraph();
        XWPFRun run = titulo.createRun();
        run.setText("Relatório Mensal - " + mesReferencia.getMonth() + "/" + mesReferencia.getYear());
        run.setBold(true);
        run.setFontSize(16);

        List<MonthlyTable> mensalidades = monthlyRepository.findByMesEAnoAndPaga(mesReferencia.getMonthValue(), mesReferencia.getYear());
        System.out.println("Mensalidades encontradas: " + mensalidades.size());
        XWPFParagraph mensalidadePar = doc.createParagraph();
        XWPFRun mRun = mensalidadePar.createRun();
        mRun.addBreak();
        mRun.setText("Mensalidades:");
        for (MonthlyTable m : mensalidades) {
            mRun.addBreak();
            mRun.setText("- " + m.getCrianca().getNome() + ": " + (m.isPaga() ? "Paga" : "Pendente") + " - Valor: R$" + m.getValor());
        }

        List<SpentTable> despesas = spentRepository.findByMesEAno(mesReferencia.getMonthValue(),mesReferencia.getYear());
        XWPFParagraph despesaPar = doc.createParagraph();
        XWPFRun dRun = despesaPar.createRun();
        dRun.addBreak();
        dRun.setText("Despesas:");
        for (SpentTable d : despesas) {
            dRun.addBreak();
            dRun.setText("- " + d.getDescricao() + ": R$" + d.getValor());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();
        return out.toByteArray();
    }

}
