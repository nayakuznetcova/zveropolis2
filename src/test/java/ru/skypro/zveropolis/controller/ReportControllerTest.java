package ru.skypro.zveropolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.repository.PetRepository;
import ru.skypro.zveropolis.repository.ReportRepository;
import ru.skypro.zveropolis.service.PetService;
import ru.skypro.zveropolis.service.ReportService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ReportController.class)
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportRepository reportRepository;

    @Mock
    private PetRepository petRepository;
    @SpyBean
    private ReportService reportService;

    @MockBean
    private PetService petService;
    @InjectMocks
    ReportController reportController;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addReportCorrect() throws Exception{

        long id = 20L;
        String description = "test";

        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);




        when(reportRepository.save(any(Report.class))).thenReturn(reportTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/report")
                        .content(objectMapper.writeValueAsString(reportTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getReportsCorrect() throws Exception {
        long id = 20L;
        String description = "test";

        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);
        List<Report> reports = List.of(reportTest);
        List<Report> exp = List.of(reportTest);


        when(reportService.getAll()).thenReturn(reports);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/getAllReports")
                        .content(objectMapper.writeValueAsString(reports))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(reports.size(), exp.size());
        for (int i = 0; i < reports.size(); i++) {
            assertEquals(exp.get(i).getDescription(), reports.get(i).getDescription());

        }
    }

    @Test
    void getReport() {
    }

    @Test
    void getReportsForCheck() {
    }

    @Test
    void updatePet() {
    }

    @Test
    void checkReport() {
    }

    @Test
    void deleteReport() {
    }
}