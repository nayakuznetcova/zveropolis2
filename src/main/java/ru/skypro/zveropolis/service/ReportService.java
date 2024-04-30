package ru.skypro.zveropolis.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.repository.ReportRepository;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class ReportService {
    @Autowired
    private final ReportRepository reportRepository;
    /**
     * Позволяет добавить отчет вручную волонтером
     */
    public void addReport( Report report) {
        reportRepository.save(report);
    }

    /**
     * Позволяет получить отчет по идентификатору
     * @return отчет
     */
    public Optional<Report> getReportById(long id) {
        return reportRepository.findById(id);
    }
    /**
     * Позволяет получить отчет по питомцу
     * @return отчет
     */
    public Optional<Report> getReportByPet(Pet pet) {
        return reportRepository.findByPet(pet);
    }
    /**
     * Позволяет обновить информацию в отчете
     *
     * @param report сущность питомца
     * @return обновленные данные отчета
     */

    public Report updateReport(Report report) {
        return reportRepository.save(report);
    }

    /**
     * Позволяет удалить отчет из базы данных
     *
     * @param id идентификатор отчета
     */

    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }

    /**
     * Позволяет получить список всех отчетов
     *
     * @return список всех отчетов
     */

    public List<Report> getAll() {
        return reportRepository.findAll();
    }
    /**
     * Позволяет изменить статус отчета после проверки волонтером
     *
     * @return отчет
     */
    public Report checkReportByVolunteer(Report report) {
        report.setChecked(true);
        return reportRepository.save(report);
    }
    /**
     * Позволяет получить отчеты для проверки волонтером
     *
     * @return отчет
     */
    public List<Report> getAlLReportsForCheck(boolean isChecked) {
        return reportRepository.findAllByIsChecked(isChecked);
    }

    }


