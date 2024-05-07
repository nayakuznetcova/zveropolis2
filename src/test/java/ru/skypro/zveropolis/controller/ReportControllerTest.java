package ru.skypro.zveropolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import static org.mockito.Mockito.doNothing;
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
    void addReportCorrect() throws Exception {

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
    void getReportsByPetCorrect() throws Exception {

        long id = 20L;
        String description = "test";

        Pet petTest = new Pet();
        petTest.setId(10L);

        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);
        reportTest.setPet(petTest);

        List<Report> report = List.of(reportTest);
        List<Report> exp = List.of(reportTest);


        when(reportRepository.findByPetId(petTest.getId())).thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/getReportsByPetId/" + petTest.getId())
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(report.size(), exp.size());
        for (int i = 0; i < report.size(); i++) {
            assertEquals(exp.get(i).getDescription(), report.get(i).getDescription());

        }

    }

    @Test
    void getReportsForCheckCorrect() throws Exception {
        long id = 20L;
        String description = "test";
        boolean isChecked = false;

        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);
        reportTest.setChecked(isChecked);
        List<Report> reports = List.of(reportTest);
        List<Report> exp = List.of(reportTest);

        when(reportService.getAlLReportsForCheck(isChecked)).thenReturn(reports);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/getReportsForCheck")
                        .content(objectMapper.writeValueAsString(reports))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(reports.size(), exp.size());
        for (int i = 0; i < reports.size(); i++) {
            assertEquals(exp.get(i).getDescription(), reports.get(i).getDescription());


        }
    }

    @Test
    void updateReportCorrect() throws Exception {
        long id = 20L;
        String description = "test";
        String newDescription = "new";


        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);
        Report reportUpdate = new Report();
        reportUpdate.setId(id);
        reportUpdate.setDescription(newDescription);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", newDescription);

        when(reportRepository.save(any(Report.class))).thenReturn(reportUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/report?" + reportUpdate)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value(newDescription));
    }


    @Test
    void checkReportCorrect() throws Exception {
        long id = 20L;
        String description = "test";
        boolean isNotChecked = false;
        boolean isChecked = true;


        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);
        reportTest.setChecked(isNotChecked);
        Report reportUpdate = new Report();
        reportUpdate.setId(id);
        reportUpdate.setDescription(description);
        reportUpdate.setChecked(isChecked);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("description", description);
        jsonObject.put("checked", isChecked);

        when(reportRepository.save(any(Report.class))).thenReturn(reportUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/report/check?" + reportUpdate)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.checked").value(isChecked));
    }


    @Test
    void deleteReportCorrect() throws Exception {
        long id = 20L;
        String description = "test";


        Report reportTest = new Report();
        reportTest.setId(id);
        reportTest.setDescription(description);


        when(reportRepository.save(any(Report.class))).thenReturn(reportTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/report/" + reportTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}