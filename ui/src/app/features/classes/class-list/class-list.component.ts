import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ClassService } from '../../../core/services/class.service';
import { ClassItem } from '../../../core/models/class.model';

@Component({
  selector: 'app-class-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css']
})
export class ClassListComponent implements OnInit {
  items = signal<ClassItem[]>([]);
  loading = signal(true);
  constructor(private service: ClassService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
