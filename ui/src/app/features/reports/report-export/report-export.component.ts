import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ReportService } from '../../../core/services/report.service';
import { CsvExportVisitor } from '../../../core/visitor/export.visitor';

@Component({
  selector: 'app-report-export',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './report-export.component.html',
  styleUrls: ['./report-export.component.css']
})
export class ReportExportComponent implements OnInit {
  private reportService = inject(ReportService);

  lines = signal<string[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.reportService.getDepartmentTreeReport().subscribe({
      next: (list) => { this.lines.set(list ?? []); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  exportCsv(): void {
    const visitor = new CsvExportVisitor();
    this.lines().forEach(line => visitor.visitLine(line));
    const csv = visitor.getResult();
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'department-tree-report.csv';
    a.click();
    URL.revokeObjectURL(url);
  }
}