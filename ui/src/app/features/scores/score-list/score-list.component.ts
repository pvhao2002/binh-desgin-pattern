import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ScoreService } from '../../../core/services/score.service';
import { Score } from '../../../core/models/score.model';

@Component({
  selector: 'app-score-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './score-list.component.html',
  styleUrls: ['./score-list.component.css']
})
export class ScoreListComponent implements OnInit {
  items = signal<Score[]>([]);
  loading = signal(true);
  constructor(private service: ScoreService) {}
  ngOnInit(): void {
    this.service.getAll().subscribe({ next: (list) => { this.items.set(list); this.loading.set(false); }, error: () => this.loading.set(false) });
  }
}
