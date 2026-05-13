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

export interface CreateTaskRequest {
	id?: number;
	title: string;
	description?: string;
	columnId: number;
	priority?: string;
	assigneeId?: number | null;
	dueDate?: string | null;
}

export interface UpdateTaskRequest {
	id: number;
	title: string;
	description?: string;
	columnId: number;
	priority?: string;
	assigneeId?: number | null;
	dueDate?: string | null;
}

export interface MoveTaskRequest {
	targetColumnId: number;
	prevTaskPosition: number | null;
	nextTaskPosition: number | null;
}
