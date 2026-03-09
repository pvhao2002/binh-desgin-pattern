import { Component, Input } from '@angular/core';
import { TreeNode } from '../../../core/models/tree-node.model';

@Component({
  selector: 'app-tree-node',
  standalone: true,
  imports: [TreeNodeComponent],
  templateUrl: './tree-node.component.html',
  styleUrls: ['./tree-node.component.css']
})
export class TreeNodeComponent {
  @Input({ required: true }) node!: TreeNode;
}
