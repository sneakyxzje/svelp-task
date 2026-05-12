import { http } from '$lib/http/http';
import type { CreateTaskRequest, MoveTaskRequest, Task } from '$lib/interface/task';

export const taskService = {
	create: (data: CreateTaskRequest) => http.post<Task>('/tasks', data),
	update: (data: Partial<Task> & { id: number }) => http.patch<Task>(`/tasks/${data.id}`, data),
	move: (taskId: number, data: MoveTaskRequest) => http.patch<Task>(`/tasks/${taskId}/move`, data)
};
