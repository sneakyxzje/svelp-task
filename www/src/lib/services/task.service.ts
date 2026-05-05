import { http } from '$lib/http/http';
import type { Task } from '$lib/interface/task';

export interface CreateTaskRequest {
	title: string;
	description?: string;
	columnId: number;
	priority?: string;
	assigneeId?: number | null;
	dueDate?: string | null;
}

export const taskService = {
	create: (data: CreateTaskRequest) => http.post<Task>('/tasks', data)
};
