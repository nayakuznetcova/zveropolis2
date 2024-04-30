package ru.skypro.zveropolis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @InjectMocks
    ReportService out;

    @Mock
    ReportRepository repository;

    Report REPORT_1 = new Report();

    Pet PET_1 = new Pet();

    List <Report> REPORTS = List.of(REPORT_1);
    @BeforeEach
    void setUp() {
        REPORT_1.setId(1L);
        PET_1.setId(10L);
        REPORT_1.setPet(PET_1);
        REPORT_1.setChecked(false);
    }
    @Test
    void addReportCorrect() {
        Mockito.when(repository.save(REPORT_1)).thenReturn(REPORT_1);
        out.addReport(REPORT_1);
        Mockito.verify(repository, Mockito.times(1)).save(REPORT_1);
    }

    @Test
    void getReportByIdCorrect() {
        long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(REPORT_1));
        out.getReportById(id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        assertEquals(Optional.of(REPORT_1), out.getReportById(id));
    }

    @Test
    void getReportByPet() {

        Mockito.when(repository.findByPet(PET_1)).thenReturn(Optional.ofNullable(REPORT_1));
        out.getReportByPet(PET_1);
        Mockito.verify(repository, Mockito.times(1)).findByPet(PET_1);
        assertEquals(Optional.of(REPORT_1),   out.getReportByPet(PET_1));
    }

    @Test
    void updateReport() {
        Mockito.when(repository.save(REPORT_1)).thenReturn(REPORT_1);
        out.updateReport(REPORT_1);
        Mockito.verify(repository, Mockito.times(1)).save(REPORT_1);
        assertEquals(REPORT_1, out.updateReport(REPORT_1));
    }

    @Test
    void deleteReport() {
        long id = 1L;
        Mockito.doNothing().when(repository).deleteById(id);
        out.deleteReport(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(REPORTS);
        out.getAll();
        Mockito.verify(repository, Mockito.times(1)).findAll();
        assertEquals(REPORTS, out.getAll());
    }

    @Test
    void checkReportByVolunteer() {
        Mockito.when(repository.save(REPORT_1)).thenReturn(REPORT_1);
        out.checkReportByVolunteer(REPORT_1);
        Mockito.verify(repository, Mockito.times(1)).save(REPORT_1);
        assertEquals(REPORT_1, out.checkReportByVolunteer(REPORT_1));
    }

    @Test
    void getAlLReportsForCheck() {
        Mockito.when(repository.findAllByIsChecked(false)).thenReturn(REPORTS);
        out.getAlLReportsForCheck(false);
        Mockito.verify(repository, Mockito.times(1)).findAllByIsChecked(false);
        assertEquals(REPORTS, out.getAlLReportsForCheck(false));
    }

}