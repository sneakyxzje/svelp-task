import type { Task } from './task';

export interface ColumnDetail {
	id: number;
	name: string;
	position: number;
	tasks: Task[];
}
