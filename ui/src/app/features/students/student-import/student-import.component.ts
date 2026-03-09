import { Component, signal, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StudentImportFlow } from '../../../core/import/student-import.flow';
import { ImportResult } from '../../../core/import/base-import.flow';

@Component({
  selector: 'app-student-import',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './student-import.component.html',
  styleUrls: ['./student-import.component.css']
})
export class StudentImportComponent {
  private importFlow = inject(StudentImportFlow);

  selectedFile = signal<File | null>(null);
  importing = signal(false);
  result = signal<ImportResult | null>(null);

  onFileSelected(e: Event): void {
    const input = e.target as HTMLInputElement;
    const file = input.files?.[0];
    this.selectedFile.set(file ?? null);
    this.result.set(null);
  }

  import(): void {
    const file = this.selectedFile();
    if (!file) return;
    this.importing.set(true);
    this.result.set(null);
    this.importFlow.run(file).subscribe({
      next: (r) => { this.result.set(r); this.importing.set(false); },
      error: () => { this.importing.set(false); }
    });
  }
}