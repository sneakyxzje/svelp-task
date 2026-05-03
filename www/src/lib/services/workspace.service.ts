import { http } from '$lib/http/http';
import type { Workspace } from '$lib/interface/workspace';

export const workspaceService = {
	getAll: () => http.get<Workspace[]>('/workspaces'),
	create: (data: Partial<Workspace>) => http.post<Workspace>('/workspaces', data)
};
