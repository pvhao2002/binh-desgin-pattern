export interface ReportExportVisitor {
  visitLine(line: string): void;
  getResult(): string;
}

export class CsvExportVisitor implements ReportExportVisitor {
  private readonly lines: string[] = [];

  visitLine(line: string): void {
    this.lines.push(line.replace(/"/g, '""'));
  }

  getResult(): string {
    return this.lines.map(l => `"${l}"`).join('\n');
  }
}
