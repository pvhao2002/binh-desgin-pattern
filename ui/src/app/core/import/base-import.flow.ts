import { Observable } from 'rxjs';

export interface ImportResult {
  success: boolean;
  count: number;
  errors: string[];
}

export abstract class BaseImportFlow<T> {
  abstract readFile(file: File): Observable<string>;
  abstract validate(raw: string): string[];
  abstract mapToModel(raw: string): T[];
  abstract submit(items: T[]): Observable<ImportResult>;

  run(file: File): Observable<ImportResult> {
    return new Observable(observer => {
      this.readFile(file).subscribe({
        next: (raw) => {
          const errors = this.validate(raw);
          if (errors.length > 0) {
            observer.next({ success: false, count: 0, errors });
            observer.complete();
            return;
          }
          const items = this.mapToModel(raw);
          this.submit(items).subscribe({
            next: (res) => { observer.next(res); observer.complete(); },
            error: (e) => { observer.next({ success: false, count: 0, errors: [e?.message ?? 'Submit failed'] }); observer.complete(); }
          });
        },
        error: (e) => { observer.next({ success: false, count: 0, errors: [e?.message ?? 'Read failed'] }); observer.complete(); }
      });
    });
  }
}
