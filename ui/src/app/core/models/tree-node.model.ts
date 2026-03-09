export interface TreeNode {
  id: number;
  label: string;
  type: 'department' | 'class' | 'student';
  children: TreeNode[];
  expandable: boolean;
}
