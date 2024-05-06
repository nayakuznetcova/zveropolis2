package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.service.PetService;
import ru.skypro.zveropolis.service.ReportService;

import java.util.List;

@RestController
@RequestMapping ("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PetService petService;
    @Operation(
            summary = "Добавление отчета вручную волонтером"

    )
    @PostMapping()
    public void addReport(@RequestBody Report report) {reportService.addReport(report);}

    @Operation(
            summary = "Получить все отчеты"

    )

    @GetMapping("/getAllReports")
    public ResponseEntity<List<Report>> getReports() {
        List<Report> reports = reportService.getAll();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }
    @Operation(
            summary = "Получить отчет по питомцу"

    )
    @GetMapping("/getReportsByPetId/{id}")
    public ResponseEntity<List <Report>> getReports( @PathVariable long id) {
        List<Report> report = reportService.getReportsByPet(id);
        if (report.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
    @Operation(
            summary = "Получить отчеты на проверку"

    )
    @GetMapping("/getReportsForCheck")
    public ResponseEntity<List <Report>> getReportsForCheck(boolean isChecked) {
       List<Report> report = reportService.getAlLReportsForCheck(isChecked);
        if (report.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
    @Operation (
            summary = "Коррекция информации в отчете волонтером"
    )

    @PutMapping()
    public ResponseEntity<Report> updateReport(@RequestBody Report report) {
        Report reportToUpdate = reportService.updateReport(report);
        if (reportToUpdate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reportToUpdate);

    }
    @Operation (
            summary = "Проверка отчета волонтером"
    )

    @PutMapping("/check")
    public ResponseEntity<Report> checkReport(@RequestBody Report report) {
        Report reportToCheck = reportService.checkReportByVolunteer(report);
        return ResponseEntity.ok(report);

    }

    @Operation(
            summary = "Удаление отчета"
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable long id) {

        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }
}
