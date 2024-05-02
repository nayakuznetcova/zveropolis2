package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.service.PetService;
import ru.skypro.zveropolis.service.ReportService;

import java.util.List;
import java.util.Optional;

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
    public void addReport(@RequestBody Report report) {
        reportService.addReport(report);
    }

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
    @GetMapping("/getReportByPet")
    public ResponseEntity<Optional <Report>> getReport(Pet pet) {
        Optional<Report> report = reportService.getReportByPet(pet);
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
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        Pet petToUpdate = petService.updatePet(pet);
        if (petToUpdate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(petToUpdate);

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

    @DeleteMapping()
    public ResponseEntity<Void> deleteReport(@PathVariable long id) {

        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }
}
