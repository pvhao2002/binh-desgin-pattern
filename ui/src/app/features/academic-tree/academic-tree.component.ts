import { Component, signal, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AcademicTreeService } from '../../core/services/academic-tree.service';
import { TreeNodeComponent } from './tree-node/tree-node.component';
import { TreeNode } from '../../core/models/tree-node.model';

@Component({
  selector: 'app-academic-tree',
  standalone: true,
  imports: [RouterLink, TreeNodeComponent],
  templateUrl: './academic-tree.component.html',
  styleUrls: ['./academic-tree.component.css']
})
export class AcademicTreeComponent implements OnInit {
  private treeService = inject(AcademicTreeService);

  roots = signal<TreeNode[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.treeService.buildTree().subscribe({
      next: (roots) => { this.roots.set(roots); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }
}
