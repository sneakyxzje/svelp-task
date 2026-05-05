export interface Task {
	id: number;
	title: string;
	description: string;
	columnId: number;
	position: number;
	priority: string;
	assigneeId: number | null;
	dueDate: string | null;
	createdAt: string;
}
