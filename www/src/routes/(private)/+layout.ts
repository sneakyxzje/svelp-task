import { http } from '$lib/http/http';
import type { Workspace } from '$lib/interface/workspace.js';

export const load = async ({ fetch }) => {
	const response = await http(fetch).get<Workspace[]>('/workspaces');
	console.log('API Response Data:', response.data);
	console.log('API Response Error:', response.error);
	if (response.data) {
		return {
			workspaces: response.data
		};
	} else {
		console.error('Failed to fetch workspaces:', response.error);
		return {
			workspaces: []
		};
	}
};
